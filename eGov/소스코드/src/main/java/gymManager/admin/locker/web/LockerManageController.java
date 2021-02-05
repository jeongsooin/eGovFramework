package gymManager.admin.locker.web;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import gymManager.admin.locker.LockerVO;
import gymManager.admin.locker.service.LockerManageService;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : LockerManageController.java
 * @Description : LockerManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 * 
 */
@Controller
public class LockerManageController {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(LockerManageController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** LockerManageService DI */
	@Resource(name = "lockerManageService")
	private LockerManageService lockerManageService;

	/** MemberManageService DI */
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;

	/**
	 *
	 * @Description 사물함 목록 조회 후 사물함관리 메뉴 화면을 세팅한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lockerView.do")
	public ModelAndView lockerList(HttpServletRequest requset, HttpServletResponse response) throws Exception {
		try {

			// 사물함 정보 목록을 DB에서 조회한다
			List<LockerVO> list = lockerManageService.selectLockerList();

			// 사물함 정보에 추가할 회원이름을 저장할 리스트 변수를 선언한다
			List<String> names = new ArrayList<String>();
			List<MemberVO> members = memberManageService.selectMemberList();

			// 각 VO의 MID로 회원이름을 검색해서 리스트에 추가한다
			for (int i = 0; i < list.size(); i++) {

				int mid = list.get(i).getMid();

				if (mid != 0) {
					String name = memberManageService.selectMemberInfo(mid).getName();
					names.add(name);
				} else {
					names.add("");
				}
			}

			// 사물함정보, 회원이름 리스트를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			mv.setViewName("admin/locker/locker");
			mv.addObject("list", list);
			mv.addObject("names", names);
			mv.addObject("members", members);

			return mv;
		} catch (Exception e) {
			Log.debug(e);
			return new ModelAndView("admin/locker/locker");
		}
	}

	/**
	 *
	 * @Description 사물함 수정 정보를 받아와 DB에 update한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateLocker.do")
	public void updateLocker(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int lid = Integer.parseInt(request.getParameter("lid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date start_dtm = (Date) (dateFormat.parse(request.getParameter("start_dtm")));
			Date end_dtm = (Date) (dateFormat.parse(request.getParameter("end_dtm")));
			
			if(memberManageService.selectMemberInfo(mid) == null) {
				// 결과 메시지 출력
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('존재하지 않는 회원번호입니다.'); location.href='/lockerView.do';</script>");
				out.flush();
			} else {
				// value 값을 VO에 세팅한다
				LockerVO lockerVO = new LockerVO();
				lockerVO.setLid(lid);
				lockerVO.setMid(mid);
				lockerVO.setStart_dtm(new java.sql.Date(start_dtm.getTime()));
				lockerVO.setEnd_dtm(new java.sql.Date(end_dtm.getTime()));
				lockerVO.setModify_usr_id(loginID);

				lockerManageService.updateLockerInfo(lockerVO);

				// 결과 메시지 출력
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('배정 되었습니다'); location.href='/lockerView.do';</script>");
				out.flush();
			}
		} catch (Exception e) {
			Log.debug(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('배정에 실패하였습니다'); location.href='/lockerView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 삭제할 사물함정보의 번호를 받아와 DB에서 삭제한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLockerAjax.do")
	public ModelAndView deleteLockerAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온 후 삭제할 레코드 값을 세팅한다
			int lid = Integer.parseInt(request.getParameter("lid"));
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();

			LockerVO lockerVO = new LockerVO();
			lockerVO.setLid(lid);
			lockerVO.setModify_usr_id(loginID);

			// DB에서 삭제한다(사물함 정보는 초기데이터로 리셋)
			lockerManageService.resetLockerInfo(lockerVO);

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
