package gymManager.admin.member.web;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import gymManager.admin.locker.service.LockerManageService;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : MemberManageController.java
 * @Description : MemberManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Controller
public class MemberManageController {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(MemberManageController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** MemberManageService DI */
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;
	
	/** LockerManageService DI */
	@Resource(name = "lockerManageService")
	private LockerManageService lockerManageService;
	
	/** ScheduleManageService DI */
	@Resource(name = "scheduleManageService")
	private ScheduleManageService scheduleManageService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;

	/**
	 *
	 * @Description 회원 목록 조회 후 회원관리 메뉴 화면을 세팅한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/memberView.do")
	public ModelAndView memeberView(HttpServletRequest requset, HttpServletResponse response) throws Exception {
		try {

			// 회원 정보 목록을 DB에서 조회한다
			List<MemberVO> list = memberManageService.selectMemberList();

			// 회원 정보 리스트를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			mv.setViewName("admin/member/member");
			mv.addObject("list", list);

			return mv;
		} catch (Exception e) {
			Log.debug(e);
		}
		return new ModelAndView("admin/member/member");
	}

	/**
	 *
	 * @Description 회원 등록 정보를 받아오고 DB에 삽입한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberInsert.do")
	public void memberInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온다
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			String name = request.getParameter("name");
			String gen = request.getParameter("gen");
			String telNo = request.getParameter("tel0") + request.getParameter("tel1") + request.getParameter("tel2");
			String zip_code = request.getParameter("zip_cd");
			String load_adr = request.getParameter("load_adr");
			String det_adr = request.getParameter("det_adr");
			int pt_count = Integer.parseInt(request.getParameter("pt_count"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date mbsr_start = (Date) (dateFormat.parse(request.getParameter("mbrs_start")));
			Date mbsr_end = (Date) (dateFormat.parse(request.getParameter("mbrs_end")));

			// value 값을 VO에 세팅한다
			MemberVO memberVO = new MemberVO();
			memberVO.setName(name);
			memberVO.setGen(gen);
			memberVO.setTelNo(telNo);
			memberVO.setZip_cd(zip_code);
			memberVO.setLoad_adr(load_adr);
			memberVO.setDet_adr(det_adr);
			memberVO.setPt_count(pt_count);
			memberVO.setMbrs_start(new java.sql.Date(mbsr_start.getTime()));
			memberVO.setMbrs_end(new java.sql.Date(mbsr_end.getTime()));
			memberVO.setModify_usr_id(loginID);
			memberVO.setReg_usr_id(loginID);

			// VO에 정보를 담아 DB에 삽입한다
			memberManageService.insertMemberInfo(memberVO);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록 되었습니다'); location.href='/memberView.do';</script>");
			out.flush();
		} catch (Exception e) {
			Log.debug(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록에 실패하였습니다.'); location.href='/memberView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 회원 수정 정보를 받아와 DB에 update한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberUpdate.do")
	public void memberUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int mid = Integer.parseInt(request.getParameter("mid"));
			String name = request.getParameter("name");
			String gen = request.getParameter("gen");
			String telNo = request.getParameter("tel0") + request.getParameter("tel1") + request.getParameter("tel2");
			String zip_code = request.getParameter("zip_cd");
			String load_adr = request.getParameter("load_adr");
			String det_adr = request.getParameter("det_adr");
			int pt_count = Integer.parseInt(request.getParameter("pt_count"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date mbsr_start = (Date) (dateFormat.parse(request.getParameter("mbrs_start")));
			Date mbsr_end = (Date) (dateFormat.parse(request.getParameter("mbrs_end")));

			// value 값을 VO에 세팅한다
			MemberVO memberVO = new MemberVO();
			memberVO.setMid(mid);
			memberVO.setName(name);
			memberVO.setGen(gen);
			memberVO.setTelNo(telNo);
			memberVO.setZip_cd(zip_code);
			memberVO.setLoad_adr(load_adr);
			memberVO.setDet_adr(det_adr);
			memberVO.setPt_count(pt_count);
			memberVO.setMbrs_start(new java.sql.Date(mbsr_start.getTime()));
			memberVO.setMbrs_end(new java.sql.Date(mbsr_end.getTime()));
			memberVO.setModify_usr_id(loginID);

			// VO에 정보를 담아 DB에 수정한다
			memberManageService.updateMemberInfo(memberVO);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정 되었습니다'); location.href='/memberView.do';</script>");
			out.flush();
		} catch (Exception e) {
			Log.debug(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정에 실패하였습니다.'); location.href='/memberView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 삭제할 회원정보의 회원번호를 받아와 DB에서 삭제한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMemberAjax.do")
	public ModelAndView deleteMemberAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온 후 삭제할 레코드 값을 세팅한다
			int mid = Integer.parseInt(request.getParameter("mid"));
			MemberVO memberVO = new MemberVO();
			memberVO.setMid(mid);
			int lockers = lockerManageService.selectLockerInfo(mid).size();
			int schedules = scheduleManageService.selectScheduleMember(mid).size();
			int pt = memberManageService.selectMemberInfo(mid).getPt_count();
			
			// 사물함과 수업에 배정되어있는지 검사한다
			if(lockers == 0 && schedules == 0 && pt == 0) {
				// DB에서 삭제한다
				memberManageService.deleteMemberInfo(memberVO);

				// 삭제 성공 메시지 세팅
				model.addAttribute("message", "yes");
				return new ModelAndView("jsonView");
			} else if(schedules > 0) {
				// 삭제 실패 메시지 세팅
				model.addAttribute("message", "schedule");
				return new ModelAndView("jsonView");
			} else if (pt > 0) {
				// 삭제 실패 메시지 세팅
				model.addAttribute("message", "count");
				return new ModelAndView("jsonView");
			} else if(lockers > 0) {
				// 삭제 실패 메시지 세팅
				model.addAttribute("message", "locker");
				return new ModelAndView("jsonView");
			} else {
				// 삭제 성공 메시지 세팅
				model.addAttribute("message", "yes");
				return new ModelAndView("jsonView");
			}
		} catch (Exception e) {
			Log.debug(e);
			
			// 삭제 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}		
	}
}
