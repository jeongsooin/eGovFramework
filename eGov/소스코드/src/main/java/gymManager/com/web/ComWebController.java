package gymManager.com.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;
import gymManager.admin.diary.service.DiaryManageService;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;
import gymManager.admin.shcedule.ScheduleVO;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.admin.trainer.TrainerVO;
import gymManager.admin.trainer.service.TrainerManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : ComWebController.java
 * @Description : ComWebController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01
 * @version 1.0
 * @see
 * @desc 공통 컨트롤러
 */

@Controller
public class ComWebController {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(ComWebController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** ScheduleManageService DI */
	@Resource(name = "scheduleManageService")
	private ScheduleManageService scheduleManageService;

	/** MemberManageService DI */
	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;

	/** TrainerManageService DI */
	@Resource(name = "trainerManageService")
	private TrainerManageService trainerManageService;

	/** DiaryManageService DI */
	@Resource(name = "diaryManageService")
	private DiaryManageService diaryManageService;
	
	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;

	/**
	 *
	 * @Description 트레이너 메인 화면 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/mainView.do")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 수업일정 정보 목록을 DB에서 조회한다
			List<ScheduleVO> scheduleList = scheduleManageService.selectScheuldeList();
			List<MemberVO> memberList = memberManageService.selectMemberList();
			List<TrainerVO> trainerList = trainerManageService.selectTrainerList();

			// 수업일정 정보에 추가할 회원이름, 트레이너 이름을 저장할 리스트 변수를 선언한다
			List<String> memberNames = new ArrayList<String>();
			List<String> trainerNames = new ArrayList<String>();
			List<String> classStatus = new ArrayList<String>();
			List<String> diaryStatus = new ArrayList<String>();
			
			// 각 ScheduleVO의 MID와 TID로 회원이름, 트레이너 이름을 검색해서 리스트에 추가한다
			for (int i = 0; i < scheduleList.size(); i++) {
				int cid = scheduleList.get(i).getCid();
				int mid = scheduleList.get(i).getMid();
				String tid = scheduleList.get(i).getTid();
				String mname = memberManageService.selectMemberInfo(mid).getName();
				String tname = trainerManageService.selectTrainerInfo(tid).getName();
				memberNames.add(mname);
				trainerNames.add(tname);

				// 오늘 날짜와 수업날짜의 차이를 구해 상태 값을 추가한다.
				// 진행이거나 완료이면 일지정보를 조회해서 값을 구분 값을 추가한다.
				if (scheduleManageService.getDateDiff(scheduleList.get(i).getClass_date(),
						scheduleList.get(i).getClass_time()) == 1) {
					classStatus.add("진행"); // 진행
					
					if(diaryManageService.selecDiaryExist(cid) != null) {
						diaryStatus.add("Y");
					} else {
						diaryStatus.add("N");
					}
				} else if (scheduleManageService.getDateDiff(scheduleList.get(i).getClass_date(),
						scheduleList.get(i).getClass_time()) == 2) {
					classStatus.add("완료"); // 완료
					if(diaryManageService.selecDiaryExist(cid) != null) {
						diaryStatus.add("Y");
					} else {
						diaryStatus.add("N");
					}
				} else {
					classStatus.add("예정"); // 예정
					diaryStatus.add("N");
				}
			}

			// 수업일정, 회원이름, 트레이너이름 리스트 및 사용자 정보를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			HttpSession session = request.getSession();
			LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
			mv.setViewName("main");
			mv.addObject("list", scheduleList);
			mv.addObject("members", memberNames);
			mv.addObject("trainers", trainerNames);
			mv.addObject("tid", loginVO.getUsrId());
			mv.addObject("wstart", loginVO.getWork_start());
			mv.addObject("wend", loginVO.getWork_end());
			mv.addObject("pc", loginVO.getPt_count());
			mv.addObject("stat", classStatus);
			mv.addObject("diary", diaryStatus);
			mv.addObject("memberList", memberList);
			mv.addObject("trainerList", trainerList);

			return mv;
		} catch (Exception e) {
			Log.debug(e);
			return new ModelAndView("redirect:/coverView.do");
		}
	}

	/**
	 *
	 * @Description 회원 메인 화면 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userMainView.do")
	public ModelAndView userMain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// 수업일정 정보 목록을 DB에서 조회한다
			List<ScheduleVO> scheduleList = scheduleManageService.selectScheuldeList();
			List<MemberVO> memberList = memberManageService.selectMemberList();
			List<TrainerVO> trainerList = trainerManageService.selectTrainerList();

			// 수업일정 정보에 추가할 회원이름, 트레이너 이름을 저장할 리스트 변수를 선언한다
			List<String> memberNames = new ArrayList<String>();
			List<String> trainerNames = new ArrayList<String>();
			List<String> classStatus = new ArrayList<String>();

			// 각 ScheduleVO의 MID와 TID로 회원이름, 트레이너 이름을 검색해서 리스트에 추가한다
			for (int i = 0; i < scheduleList.size(); i++) {

				int mid = scheduleList.get(i).getMid();
				String tid = scheduleList.get(i).getTid();
				String mname = memberManageService.selectMemberInfo(mid).getName();
				String tname = trainerManageService.selectTrainerInfo(tid).getName();
				memberNames.add(mname);
				trainerNames.add(tname);

				// 오늘 날짜와 수업날짜의 차이를 구해 상태 값을 추가한다.
				if (scheduleManageService.getDateDiff(scheduleList.get(i).getClass_date(),
						scheduleList.get(i).getClass_time()) == 1) {
					classStatus.add("진행"); // 진행
				} else if (scheduleManageService.getDateDiff(scheduleList.get(i).getClass_date(),
						scheduleList.get(i).getClass_time()) == 2) {
					classStatus.add("완료"); // 완료
				} else {
					classStatus.add("예정"); // 예정
				}
			}

			// 수업일정, 회원이름, 트레이너이름 리스트 및 사용자 정보를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			HttpSession session = request.getSession();
			LoginVO loginVO = (LoginVO) session.getAttribute("loginVO");
			mv.setViewName("userMain");
			mv.addObject("list", scheduleList);
			mv.addObject("members", memberNames);
			mv.addObject("trainers", trainerNames);
			mv.addObject("mid", loginVO.getUsrId());
			mv.addObject("start", loginVO.getMbrs_start());
			mv.addObject("end", loginVO.getMbrs_end());
			mv.addObject("pc", loginVO.getPt_count());
			mv.addObject("stat", classStatus);
			mv.addObject("memberList", memberList);
			mv.addObject("trainerList", trainerList);

			return mv;
		} catch (Exception e) {
			Log.debug(e);
			return new ModelAndView("redirect:/coverView.do");
		}
	}

	/**
	 *
	 * @Description 공통 커버 페이지 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/coverView.do")
	public ModelAndView coverView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("cover");
	}

	/**
	 *
	 * @Description 트레이너 로그인 화면 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/trainerLoginView.do")
	public ModelAndView trainerLoginView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("/cmm/tlogin");
	}

	/**
	 *
	 * @Description 회원 로그인 화면 이동
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberLoginView.do")
	public ModelAndView memberLoginView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("/cmm/mlogin");
	}

	/**
	 *
	 * @Description 트레이너 로그인 처리
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trainerLogin.do")
	public void trainerLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String tid = request.getParameter("tid");
		String password = request.getParameter("pw");

		if (password.equals("asdf") && trainerManageService.selectTrainerInfo(tid) != null) {

			// 기존에 로그인한 세션 값이 존재하면 기존 값을 제거한다
			if (session.getAttribute("loginVO") != null) {
				session.removeAttribute("loginVO");
			}

			// 로그인이 성공하면 세션에 로그인 정보를 담는 loginVO 객체를 저장한다
			LoginVO loginVO = new LoginVO();
			TrainerVO trainerVO = trainerManageService.selectTrainerInfo(tid);

			loginVO.setUsrId(trainerVO.getTid());
			loginVO.setUsrType("admin");
			loginVO.setWork_start(trainerVO.getWork_start());
			loginVO.setWork_end(trainerVO.getWork_end());
			loginVO.setPt_count(trainerVO.getPt_count());

			session.setAttribute("loginVO", loginVO);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>location.href='/mainView.do';</script>");
			out.flush();
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('아이디 혹은 비밀번호가 틀립니다'); location.href='/coverView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 회원 로그인 처리
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/memberLogin.do")
	public void memberLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {

		try {
			int mid = Integer.parseInt(request.getParameter("mid"));

			if (memberManageService.selectMemberInfo(mid).getMid() == mid) {

				// 기존에 로그인한 세션 값이 존재하면 기존 값을 제거한다
				if (session.getAttribute("loginVO") != null) {
					session.removeAttribute("loginVO");
				}

				// 로그인이 성공하면 세션에 로그인 정보를 담는 loginVO 객체를 저장한다
				LoginVO loginVO = new LoginVO();
				MemberVO memberVO = memberManageService.selectMemberInfo(mid);

				loginVO.setUsrId("M" + mid);
				loginVO.setUsrType("user");
				loginVO.setMbrs_start(memberVO.getMbrs_start());
				loginVO.setMbrs_end(memberVO.getMbrs_end());
				loginVO.setPt_count(memberVO.getPt_count());

				session.setAttribute("loginVO", loginVO);

				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>location.href='/userMainView.do';</script>");
				out.flush();
			}
		} catch (Exception e) {
			Log.debug(e);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인에 실패하였습니다.'); location.href='/coverView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 세션 만료시키고 로그아웃 처리
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/logOut.do")
	public ModelAndView logout(HttpSession session) throws Exception {
		session.invalidate();
		return new ModelAndView("redirect:/coverView.do");
	}
}
