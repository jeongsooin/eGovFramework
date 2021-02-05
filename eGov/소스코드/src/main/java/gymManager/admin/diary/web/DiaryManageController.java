package gymManager.admin.diary.web;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;
import gymManager.admin.diary.DiaryVO;
import gymManager.admin.diary.service.DiaryManageService;
import gymManager.admin.locker.web.LockerManageController;
import gymManager.admin.member.service.MemberManageService;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.admin.trainer.service.TrainerManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : DiaryManageController.java
 * @Description : DiaryManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see

 */
@Controller
public class DiaryManageController {
	
	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(LockerManageController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** DiaryManageService DI */
	@Resource(name = "diaryManageService")
	private DiaryManageService diaryManageService;

	/** ScheduleManageService DI */
	@Resource(name = "scheduleManageService")
	private ScheduleManageService scheduleManageService;

	/** MemberManageService DI */
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;

	/** TrainerManageService DI */
	@Resource(name = "trainerManageService")
	private TrainerManageService trainerManageService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;
	
	/**
	 *
	 * @Description 수업일지 정보 조회하고 조회한 결과 값을 담아 리턴한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/selectDiaryAjax.do")
	public ModelAndView selectDiaryAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
			
		try {
			// request로부터 parameter의 value를 받아온다
			int cid = Integer.parseInt(request.getParameter("cid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			
			// value 값을 VO에 세팅한다
			DiaryVO diaryVO = new DiaryVO();
			diaryVO.setCid(cid);
			diaryVO.setMid(mid);
			diaryVO.setTid(tid);
			
			if (diaryManageService.selectDiaryInfo(diaryVO) == null) {
				// 조회 결과 없음 메시지 세팅
				model.addAttribute("message", "no");
				model.addAttribute("contents", "");
				return new ModelAndView("jsonView");
			} else {
				String contents = diaryManageService.selectDiaryInfo(diaryVO).getContents();
				// 조회 결과 있음 메시지 세팅
				model.addAttribute("message", "yes");
				model.addAttribute("contents", contents);
				return new ModelAndView("jsonView");
			}					
		} catch (Exception e) {
			Log.error(e);
			
			// 조회 실패 메시지 세팅
			model.addAttribute("message", "fail");
			return new ModelAndView("jsonView");
		}		
	}
	
	/**
	 *
	 * @Description 수업일지 정보를 등록하고 등록한 결과 값을 담아 리턴한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/insertDiary.do")
	public void insertDiaryInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int cid = Integer.parseInt(request.getParameter("cid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			String contents = request.getParameter("contents");
			
			// value 값을 VO에 세팅한다
			DiaryVO diaryVO = new DiaryVO();
			diaryVO.setCid(cid);
			diaryVO.setMid(mid);
			diaryVO.setTid(tid);
			diaryVO.setContents(contents);
			diaryVO.setReg_usr_id(loginID);
			diaryVO.setModify_usr_id(loginID);
			
			// 수업일지 정보를 VO에 정보를 담아 DB에 삽입한다 
			diaryManageService.insertDiaryInfo(diaryVO);
			
			//해당 수업에 배정된 트레이너와 회원의 개인수업 횟수를 갱신한다
			memberManageService.updateMemberPTCount(mid);
			trainerManageService.updateTrainerPTCount(tid);
			
			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록 되었습니다'); location.href='/scheduleView.do';</script>");
			out.flush();
		} catch (Exception e) {
			Log.error(e);
			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록 실패하였습니다'); location.href='/scheduleView.do';</script>");
			out.flush();
		}
	}
	
	/**
	 *
	 * @Description 수업일지 정보를 수정하고 수정한 결과 값을 담아 리턴한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/updateDiaryAjax.do")
	public ModelAndView updateDiaryInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int cid = Integer.parseInt(request.getParameter("cid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			String contents = request.getParameter("contents");
			
			// value 값을 VO에 세팅한다
			DiaryVO diaryVO = new DiaryVO();
			diaryVO.setCid(cid);
			diaryVO.setMid(mid);
			diaryVO.setTid(tid);
			diaryVO.setContents(contents);
			diaryVO.setModify_usr_id(loginID);
			
			// 수업일지 정보를 VO에 담아 DB에 update한다
			diaryManageService.updateDiaryInfo(diaryVO);
			
			// 수정 성공 메시지 세팅
			model.addAttribute("message", "yes");
			return new ModelAndView("jsonView");
						
		} catch (Exception e) {
			Log.error(e);
			// 수정 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}
	}
	
	/**
	 *
	 * @Description 수업일지 정보를 삭제하고 삭제한 결과 값을 담아 리턴한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteDiaryAjax.do")
	public ModelAndView deleteDiaryAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			// request로부터 parameter의 value를 받아온다
			int cid = Integer.parseInt(request.getParameter("cid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			
			// value 값을 VO에 세팅한다
			DiaryVO diaryVO = new DiaryVO();
			diaryVO.setCid(cid);
			diaryVO.setMid(mid);
			diaryVO.setTid(tid);
			
			// DB에서 해당 수업일지 정보를 삭제한다
			diaryManageService.deleteDiaryInfo(diaryVO);
			
			// 삭제 성공 메시지 세팅
			model.addAttribute("message", "yes");
			return new ModelAndView("jsonView");
		} catch (Exception e) {
			Log.error(e);
			// 삭제 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}
	}
}
