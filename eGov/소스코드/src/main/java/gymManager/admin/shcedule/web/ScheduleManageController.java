package gymManager.admin.shcedule.web;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*;

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
import gymManager.admin.diary.service.DiaryManageService;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;
import gymManager.admin.shcedule.EventVO;
import gymManager.admin.shcedule.ScheduleVO;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.admin.trainer.TrainerVO;
import gymManager.admin.trainer.service.TrainerManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : ScheduleManageController.java
 * @Description : ScheduleManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Controller
public class ScheduleManageController {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(ScheduleManageController.class);

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
	 * @Description 수업일정 목록 조회 후 수업일정관리 메뉴 화면을 세팅한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/scheduleView.do")
	public ModelAndView scheduleList(HttpServletRequest requset, HttpServletResponse response) throws Exception {

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

			for (int i = 0; i < scheduleList.size(); i++) {
				// 각 ScheduleVO의 MID와 TID로 회원이름, 트레이너 이름을 검색해서 리스트에 추가한다
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

			// 수업일정, 회원이름, 트레이너이름 리스트를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			mv.setViewName("admin/schedule/schedule");
			mv.addObject("list", scheduleList);
			mv.addObject("members", memberNames);
			mv.addObject("trainers", trainerNames);
			mv.addObject("stat", classStatus);
			mv.addObject("diary", diaryStatus);
			mv.addObject("memberList", memberList);
			mv.addObject("trainerList", trainerList);

			return mv;
		} catch (Exception e) {
			Log.debug(e);
			return new ModelAndView("admin/schedule/schedule");
		}
	}
	
	/**
	 *
	 * @Description 수업일정 정보를 조회 후, 일정 정보를 fullCalendar Event 객체로 표현할 수 있도록 EventVO에 저장하여 리턴한다
	 * 				1. EventVO : fullCalendar에서 event(일정) 렌더 시 사용되는 여러가지 옵션을 저장하는 용도의 클래스
	 * 				2. 수업일정 정보를 Event 형식으로 나타낼 수 있도록 알맞은 속성값을 매핑한다
	 *                 ex. 날짜, 시간, 일정 제목, 일정 색상 등
	 *              3. 수업일정 목록 테이블과 마찬가지로 각 수업 일정에서 수업 일지를 확인하고, 상태에 따라 구분할 수 있도록 옵션을 지정한다
	 *                 ex. 수업일정이 "진행" 및 "완료"일 때만 수업 일지가 활성화 된다. 수업 일지가 활성화 되면 조회, 작성 및 수정이 가능하고,
	 *                     색상을 통해 구분한다. 파란색 배경은 수업일지가 등록되어 완료된 상태의 일정, 파란 테두리 일정은 진행 중이나 일지가 
	 *                     작성되지 않은 일정, 회색 바탕의 일정은 아직 수업일지를 등록할 수 없는 "예정" 상태의 일정으로 세팅한다 
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/selectScheduleListAjax.do")
	public ModelAndView selectScheduleListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			
			// 이벤트 정보를 담을 리스트 선언
			List<EventVO> eventList = new ArrayList<EventVO>();
			
			// 일정 목록을 DB에서 조회 후 리스트에 저장
			List<ScheduleVO> scheduleList = scheduleManageService.selectScheuldeList();
			
			// 일정 정보를 이벤트 정보에 맞게 파싱해서 EventVO에 세팅한 후, eventList에 추가
			for (int i = 0; i < scheduleList.size(); i++) {
				// 각 ScheduleVO의 MID와 TID로 회원이름, 트레이너 이름을 검색해서 리스트에 추가한다
				int cid = scheduleList.get(i).getCid();
				int mid = scheduleList.get(i).getMid();
				int type = scheduleList.get(i).getClass_type();
				String tid = scheduleList.get(i).getTid();
				String date = scheduleList.get(i).getClass_date().toString();
				String stime = scheduleList.get(i).getClass_time();
				String mname = memberManageService.selectMemberInfo(mid).getName();
				String tname = trainerManageService.selectTrainerInfo(tid).getName();
				String sHour = stime.split(":")[0];
				String sMin  = stime.split(":")[1];

				// EventVO에 세팅할 데이터
				String title = mname + "(" + mid + ")" + " 회원 - " + tname + "(" + tid + ") 트레이너 개인수업";
				LocalTime time = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMin));
				LocalTime etime = time.plusMinutes(type);
				String start = date + "T" + stime + ":00";
				String end = date + "T" +etime.toString() + ":00";
				String backgroundColor = "";
				String borderColor = "";
				String textColor = "";
				
				// 수업 일지 존재 여부에 따라 일정의 색을 다르게 표시한다
				if(diaryManageService.selecDiaryExist(cid) != null) {		// 수업일지가 등록되고 완료된 상태의 일정
					backgroundColor = "#198595";
					borderColor = "#FFFFFF";
					textColor = "#FFFFFF";
				} else if(diaryManageService.selecDiaryExist(cid) == null && // 수업일지는 등록되지 않은 진행 상태의 일정
						scheduleManageService.getDateDiff(scheduleList.get(i).getClass_date(), scheduleList.get(i).getClass_time()) != 0){
					backgroundColor = "#FFFFFF";
					borderColor = "#198595";
					textColor = "#198595";
				} else {													// 수업일지는 아직 조회, 작성, 수정할 수 없는 예정 상태의 일정
					backgroundColor = "#A0A6AB";
					borderColor = "#FFFFFF";
					textColor = "#FFFFFF";
				}
				
				// EventVO에 데이터 세팅
				EventVO eventVO = new EventVO();
				eventVO.setId(cid);
				eventVO.setTitle(title);
				eventVO.setStart(start);
				eventVO.setEnd(end);
				eventVO.setBackgroundColor(backgroundColor);
				eventVO.setBorderColor(borderColor);
				eventVO.setTextColor(textColor);
				
				// 생성된 EventVO를 리스트에 추가
				eventList.add(eventVO);
			}
			
			// 만들어진 이벤트 목록을 뷰에 담아서 반환
			model.addAttribute("message", "yes");
			model.addAttribute("eventList", eventList);
			return new ModelAndView("jsonView");
		} catch (Exception e) {
			Log.error(e);
			// 조회 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}
	}

	/**
	 *
	 * @Description 수업일정 등록 정보를 받아오고 DB에 삽입한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertSchedule.do")
	public void insertSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date class_date = (Date) (dateFormat.parse(request.getParameter("class_date")));
			String class_time = request.getParameter("class_time");
			int class_type = Integer.parseInt(request.getParameter("type"));

			// 배정할 회원의 잔여 수업 횟수가 존재하는지 체크
			if (memberManageService.selectMemberInfo(mid).getPt_count() == 0) {
				// 결과 메시지 출력
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('해당 회원의 잔여수업 횟수가 0 입니다.'); location.href='/scheduleView.do';</script>");
				out.flush();
			} else {
				// 등록하려는 시간이 시간 목록에서 겹치지 않는지 확인
				if(timeCheck(mid, tid, class_date, class_time, class_type) == 0) {
					// value 값을 VO에 세팅한다
					ScheduleVO scheduleVO = new ScheduleVO();
					scheduleVO.setMid(mid);
					scheduleVO.setTid(tid);
					scheduleVO.setClass_date(new java.sql.Date(class_date.getTime()));
					scheduleVO.setClass_time(class_time);
					scheduleVO.setClass_type(class_type);
					scheduleVO.setReg_usr_id(loginID);
					scheduleVO.setModify_usr_id(loginID);

					// 회원과 트레이너 모두 배정된 수업이 없으므로 VO에 정보를 담아 DB에 삽입한다
					scheduleManageService.insertScheduleInfo(scheduleVO);

					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('등록 되었습니다'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else if(timeCheck(mid, tid, class_date, class_time, class_type) == 1) {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('등록하려는 시간에 이미 수업이 존재합니다.'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else if(timeCheck(mid, tid, class_date, class_time, class_type) == 2) {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('해당 트레이너의 근무 시간이 아닙니다.'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('등록에 실패하였습니다'); location.href='/scheduleView.do';</script>");
					out.flush();
				}
				
			}
		} catch (Exception e) {
			Log.error(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록에 실패하였습니다'); location.href='/scheduleView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 수업일정 수정 정보를 받아와 DB에 update한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSchedule.do")
	public void updateSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			int cid = Integer.parseInt(request.getParameter("cid"));
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date class_date = (Date) (dateFormat.parse(request.getParameter("class_date")));
			String class_time = request.getParameter("class_time");
			int class_type = Integer.parseInt(request.getParameter("type"));

			// 배정할 회원의 잔여 수업 횟수가 존재하는지 체크
			if (memberManageService.selectMemberInfo(mid).getPt_count() == 0) {
				// 결과 메시지 출력
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('해당 회원의 잔여수업 횟수가 0 입니다.'); location.href='/scheduleView.do';</script>");
				out.flush();
			} else if(diaryManageService.selecDiaryExist(cid) !=null) {
				// 결과 메시지 출력
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>alert('완료된 수업은 수정할 수 없습니다.'); location.href='/scheduleView.do';</script>");
				out.flush();
			} else {
				
				if(timeCheck(mid, tid, class_date, class_time, class_type) == 0) {
					// value 값을 VO에 세팅한다
					ScheduleVO scheduleVO = new ScheduleVO();
					scheduleVO.setCid(cid);
					scheduleVO.setMid(mid);
					scheduleVO.setTid(tid);
					scheduleVO.setClass_date(new java.sql.Date(class_date.getTime()));
					scheduleVO.setClass_time(class_time);
					scheduleVO.setClass_type(class_type);
					scheduleVO.setModify_usr_id(loginID);

					// VO에 정보를 담아 DB를 수정한다
					scheduleManageService.updateScheduleInfo(scheduleVO);

					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('수정 되었습니다'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else if(timeCheck(mid, tid, class_date, class_time, class_type) == 1) {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('등록하려는 시간에 이미 수업이 존재합니다.'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else if(timeCheck(mid, tid, class_date, class_time, class_type) == 2) {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('해당 트레이너의 근무 시간이 아닙니다.'); location.href='/scheduleView.do';</script>");
					out.flush();
				} else {
					// 결과 메시지 출력
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>alert('수정에 실패하였습니다'); location.href='/scheduleView.do';</script>");
					out.flush();
				}
				
			}
		} catch (Exception e) {
			Log.error(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정에 실패하였습니다'); location.href='/scheduleView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 삭제할 수업정보의 수업번호를 받아와 DB에서 삭제한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteScheduleAjax.do")
	public ModelAndView deleteScheduleAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model)
			throws Exception {

		try {

			// request로부터 parameter의 value를 받아온 후 삭제할 레코드 값을 세팅한다
			int cid = Integer.parseInt(request.getParameter("cid"));
			ScheduleVO scheduleVO = new ScheduleVO();
			scheduleVO.setCid(cid);
			
			if(diaryManageService.selecDiaryExist(cid) == null) {
				// 수업일지가 등록된 수업이 아니면 DB에서 삭제한다
				scheduleManageService.deleteScheduleInfo(scheduleVO);
				// 삭제 성공 메시지 세팅
				model.addAttribute("message", "yes");
				return new ModelAndView("jsonView");
			} else {
				// 삭제 실패 메시지 세팅
				model.addAttribute("message", "end");
				return new ModelAndView("jsonView");
			}
		} catch (Exception e) {
			Log.error(e);

			// 삭제 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}
	}

	/**
	 * @Description 수업일정 등록 가능 시간 중 겹치는 시간을 제거한 시간 목록을 DB에서 조회하고 계산한 뒤 시간 목록 반환
	 * 				1. 조회할 시간의 조건을 받아 세팅한다(날짜, 회원번호, 트레이너 번호, 수업 진행 시간)
	 *              2. 해당 날짜와 시간에 이미 수업이 있는 회원이나 트레이너가 있는지 체크한다
	 *              3. 배정된 수업이 하나라도 있으면 회원과 트레이너 각각 겹치는 시간이 있는지 체크하여 해당 시간을 제외
	 *              4. 수업일정의 진행 시간에따라 해당 시간과 해당 시간의 n분 전후의 시간을 제외
	 *              5. 제외된 시간을 빼고 나머지 회원과 트레이너의 수업일정과 겹치지 않는 시간만 리턴할 시간 목록 리스트에 추가 
	 *              6. 그 중 해당 트레이너의 근무시간 범위 내가 아닌 시간은 제거하고 최종적으로 가능한 시간 목록 리스트를 뷰에 리턴
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/selectScheduleTimeListAjax.do")
	public ModelAndView selectScheduleTimeListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		// 기본 시간 리스트를 선언하고 초기화
		List<String> defaultTimes = new ArrayList<String>();
		String[] times = { "00:00", "00:20", "00:40", "01:00", "01:20", "01:40", "02:00", "02:20", "02:40", 
						   "03:00", "03:20", "03:40", "04:00", "04:20", "04:40", "05:00", "05:20", "05:40", 
						   "06:00", "06:20", "06:40", "07:00", "07:20", "07:40", "08:00", "08:20", "08:40", 
						   "09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20", "11:40", 
						   "12:00", "12:20", "12:40", "13:00", "13:20", "13:40", "14:00", "14:20", "14:40", 
						   "15:00", "15:20", "15:40", "16:00", "16:20", "16:40", "17:00", "17:20", "17:40",
				           "18:00", "18:20", "18:40", "19:00", "19:20", "19:40", "20:00", "20:20", "20:40", 
				           "21:00", "21:20", "21:40", "22:00", "22:20", "22:40", "23:00", "23:20", "23:40" };

		for (int i = 0; i < times.length; i++) {
			defaultTimes.add(times[i]);
		}

		try {

			// request로부터 겹치는 시간을 조회할 조건을 받아서 선언
			int mid = Integer.parseInt(request.getParameter("mid"));
			String tid = request.getParameter("tid");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date class_date = (Date) (dateFormat.parse(request.getParameter("class_date")));
			int class_type = Integer.parseInt(request.getParameter("class_type"));

			// 제거할 시간을 담을 리스트를 선언
			List<String> whiteList = new ArrayList<String>();

			// 해당 날짜와 시간에 이미 수업이 있는 회원이나 트레이너가 있는지 체크
			List<ScheduleVO> mClassList = scheduleManageService.selectScheduleMember(mid);
			List<ScheduleVO> tClassList = scheduleManageService.selectScheduleTrainer(tid);
			int mclassNum = mClassList.size();
			int tclassNum = tClassList.size();

			// 배정된 수업이 하나라도 있으면
			if (mclassNum != 0) {
				// 회원이 배정된 수업에 대해서 날짜와 시간이 겹치는지 체크
				for (int i = 0; i < mclassNum; i++) {
					if ((class_date.compareTo(mClassList.get(i).getClass_date()) == 0)) {

						String strTime = mClassList.get(i).getClass_time();
						String strHour = strTime.split(":")[0];
						String strMinu = strTime.split(":")[1];

						LocalTime time = LocalTime.of(Integer.parseInt(strHour), Integer.parseInt(strMinu));

						// 수업일정의 진행 시간이 30분이면 해당 시간과 해당 시간의 40분 후의 시간을 제거할 시간 리스트에 추가
						if (mClassList.get(i).getClass_type() == 30) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 4; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());	
								Log.info("MEMBER PLUS 1 ::: " + whiteList.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("MEMBER MINUS 1 ::: " + whiteList.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("MEMBER MINUS 2 ::: " + whiteList.toString());
								}
							}
							whiteList.add(time.toString());

							// 수업일정의 진행 시간이 50분이면 해당 시간과 해당 시간의 60분 후의 시간을 제거할 시간 리스트에 추가
						} else if (mClassList.get(i).getClass_type() == 50) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 7; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
								Log.info("MEMBER PLUS 2 ::: " + whiteList.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("MEMBER MINUS 3 ::: " + whiteList.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("MEMBER MINUS 4 ::: " + whiteList.toString());
								}
							}
							whiteList.add(time.toString());
						}
					}
				}
				
			} else if(tclassNum != 0) {
				// 트레이너가 배정된 수업에 대해서 날짜와 시간이 겹치는지 체크
				for (int i = 0; i < tclassNum; i++) {
					if ((class_date.compareTo(tClassList.get(i).getClass_date()) == 0)) {

						String strTime = tClassList.get(i).getClass_time();
						String strHour = strTime.split(":")[0];
						String strMinu = strTime.split(":")[1];

						LocalTime time = LocalTime.of(Integer.parseInt(strHour), Integer.parseInt(strMinu));

						// 수업일정의 진행 시간이 30분이면 해당 시간과 해당 시간의 40분 전후의 시간을 제거할 시간 리스트에 추가
						if (tClassList.get(i).getClass_type() == 30) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 4; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
								Log.info("TRAINER PLUS 1 ::: " + whiteList.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("TRAINER MINUS 1 ::: " + whiteList.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("TRAINER MINUS 2 ::: " + whiteList.toString());
								}
							}
							whiteList.add(time.toString());

							// 수업일정의 진행 시간이 50분이면 해당 시간과 해당 시간의 60분 전후의 시간을 제거할 시간 리스트에 추가
						} else if (tClassList.get(i).getClass_type() == 50) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 7; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
								Log.info("TRAINER PLUS 2 ::: " + whiteList.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("TRAINER MINUS 3 ::: " + whiteList.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
									Log.info("TRAINER MINUS 4 ::: " + whiteList.toString());
								}
							}
							whiteList.add(time.toString());
						}
					}
				}
			}
			
			List<String> preTimeList = new ArrayList<String>();
			// 회원과 트레이너의 수업일정과 겹치지 않는 시간만 리스트에 추가
			for (int i = 0; i < defaultTimes.size(); i++) {
				int count = 0;
				for (int j = 0; j < whiteList.size(); j++) {
					if (defaultTimes.get(i).equals(whiteList.get(j))) {
						count++;
					} 			
				}
				if(count == 0) {
					preTimeList.add(defaultTimes.get(i));
				}
			}
			
			List<String> timeList = new ArrayList<String>();
			
			// 트레이너의 근무 시간 범위 안에 포함 되는 시간만 남기고 제거
			TrainerVO trainerVO = trainerManageService.selectTrainerInfo(tid);
			String start = trainerVO.getWork_start();
			String end = trainerVO.getWork_end();
			String sHour = start.split(":")[0];
			String sMin = start.split(":")[1];
			String eHour = end.split(":")[0];
			String eMin = end.split(":")[1];
			LocalTime startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMin));
			LocalTime endTime = LocalTime.of(Integer.parseInt(eHour), Integer.parseInt(eMin));

			for(int i = 0; i < preTimeList.size(); i++) {
				String hour = preTimeList.get(i).split(":")[0];
				String min = preTimeList.get(i).split(":")[1];
				LocalTime time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(min));
				// 리스트 안의 시간이 근무시작 시간 이후이고 근무종료 시간 이전이면 timeList에 더한다
				if(time.isAfter(startTime) && time.isBefore(endTime)) {
					timeList.add(preTimeList.get(i));
				}
			}

			// 만들어진 시간 목록을 뷰에 담아서 반환
			model.addAttribute("message", "yes");
			model.addAttribute("timeList", timeList);
			return new ModelAndView("jsonView");
		} catch (Exception e) {
			Log.error(e);

			// 조회 실패 메시지 세팅
			model.addAttribute("message", "no");
			return new ModelAndView("jsonView");
		}
	}
	
	/**
	 * @Description 등록 및 수정 시, 등록할 시간이 겹치지 않는지 서버단에서 한번 더 체크하는 메소드
	 * @param mid
	 * @param tid
	 * @param class_date
	 * @param class_time
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private int timeCheck(int mid, String tid, Date class_date, String class_time, int class_type) throws Exception {
		
		// 기본 시간 리스트를 선언하고 초기화
		List<String> defaultTimes = new ArrayList<String>();
		String[] times = { "00:00", "00:20", "00:40", "01:00", "01:20", "01:40", "02:00", "02:20", "02:40", "03:00",
					       "03:20", "03:40", "04:00", "04:20", "04:40", "05:00", "05:20", "05:40", "06:00", "06:20", "06:40",
					       "07:00", "07:20", "07:40", "08:00", "08:20", "08:40", "09:00", "09:20", "09:40", "10:00", "10:20",
					       "10:40", "11:00", "11:20", "11:40", "12:00", "12:20", "12:40", "13:00", "13:20", "13:40", "14:00",
					       "14:20", "14:40", "15:00", "15:20", "15:40", "16:00", "16:20", "16:40", "17:00", "17:20", "17:40",
					       "18:00", "18:20", "18:40", "19:00", "19:20", "19:40", "20:00", "20:20", "20:40", "21:00", "21:20",
					       "21:40", "22:00", "22:20", "22:40", "23:00", "23:20", "23:40" };
		
		for (int i = 0; i < times.length; i++) {
			defaultTimes.add(times[i]);
		}
				
		// 제거할 시간을 담을 리스트를 선언
		List<String> whiteList = new ArrayList<String>();
		
		try {
					
			// 해당 날짜와 시간에 이미 수업이 있는 회원이나 트레이너가 있는지 체크
			List<ScheduleVO> mClassList = scheduleManageService.selectScheduleMember(mid);
			List<ScheduleVO> tClassList = scheduleManageService.selectScheduleTrainer(tid);
			int mclassNum = mClassList.size();
			int tclassNum = tClassList.size();

			// 배정된 수업이 하나라도 있으면
			if (mclassNum != 0) {
				// 회원이 배정된 수업에 대해서 날짜와 시간이 겹치는지 체크
				for (int i = 0; i < mclassNum; i++) {
					if ((class_date.compareTo(mClassList.get(i).getClass_date()) == 0)) {
									
						String strTime = mClassList.get(i).getClass_time();
						String strHour = strTime.split(":")[0];
						String strMinu = strTime.split(":")[1];
							
						LocalTime time = LocalTime.of(Integer.parseInt(strHour), Integer.parseInt(strMinu));
								
						// 수업일정의 진행 시간이 30분이면 해당 시간과 해당 시간의 40분 전후 까지의 시간을 제거할 시간 리스트에 추가
						if(mClassList.get(i).getClass_type() == 30) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 4; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							}
							whiteList.add(time.toString());
								
						// 수업일정의 진행 시간이 50분이면 해당 시간과 해당 시간의 60분 전후 까지의 시간을 제거할 시간 리스트에 추가
						} else if(mClassList.get(i).getClass_type()== 50) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 7; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							}
							whiteList.add(time.toString());
						}
					}
				}				
			} else if(tclassNum != 0) {
				// 트레이너가 배정된 수업에 대해서 날짜와 시간이 겹치는지 체크
				for (int i = 0; i < tclassNum; i++) {
					if ((class_date.compareTo(tClassList.get(i).getClass_date()) == 0)) {
									
						String strTime = tClassList.get(i).getClass_time();
						String strHour = strTime.split(":")[0];
						String strMinu = strTime.split(":")[1];
									
						LocalTime time = LocalTime.of(Integer.parseInt(strHour), Integer.parseInt(strMinu));
								
						// 수업일정의 진행 시간이 30분이면 해당 시간과 해당 시간의 40분 전후 까지의 시간을 제거할 시간 리스트에 추가
						if(tClassList.get(i).getClass_type() == 30) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 4; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							}
							whiteList.add(time.toString());
								
						// 수업일정의 진행 시간이 50분이면 해당 시간과 해당 시간의 60분 후 까지의 시간을 제거할 시간 리스트에 추가
						} else if(tClassList.get(i).getClass_type()== 50) {
							LocalTime whiteTime = time;
							for(int j= 1; j < 7; j++) {
								whiteTime = time.plusMinutes(j * 10);
								whiteList.add(whiteTime.toString());
							}
							if(class_type == 30) {
								for(int j= 1; j < 4; j++) {							
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							} else {
								for(int j= 1; j < 7; j++) {
									whiteTime = time.minusMinutes(j * 10);
									whiteList.add(whiteTime.toString());
								}
							}
							whiteList.add(time.toString());
						}
					}
				}
			}
			
			List<String> preTimeList = new ArrayList<String>();
			// 회원과 트레이너의 수업일정과 겹치지 않는 시간만 리스트에 추가
			for (int i = 0; i < defaultTimes.size(); i++) {
				int count = 0;
				for (int j = 0; j < whiteList.size(); j++) {
					if (defaultTimes.get(i).equals(whiteList.get(j))) {
						count++;
					} 			
				}
				if(count == 0) {
					preTimeList.add(defaultTimes.get(i));
				}
			}

			// 제외할 시간 리스트에서 등록하려는 시간이 포함되어있는지 한번 더 체크
			for(int i = 0; i < whiteList.size(); i++) {
				if(whiteList.get(i).equals(class_time)) {
					return 1;	// 시간 겹침
				} 
			}
			
			if(workTimeCheck(tid, preTimeList, class_time)) {
				return 0; // 등록 가능
			} else {
				return 2; // 트레이너 근무 시간 아님
			}
		} catch (Exception e) {
			Log.error(e);
			return 3;	// 에러 발생
		}
	}
	
	/**
	 * @Description 수업일정 등록 가능 시간 중 겹치는 시간을 제거한 시간 목록을 DB에서 조회하고 계산한 뒤 시간 목록 반환
	 * @param String tid
	 * @param List<String> defaultTimes
	 * @param String class_time
	 * @throws Exception
	 * @return
	 */
	private boolean workTimeCheck(String tid, List<String> defaultTimes, String class_time) throws Exception {
		
		List<String> timeList = new ArrayList<String>();
		
		// 트레이너의 근무 시간 범위 안에 포함 되는 시간만 남기고 제거
		TrainerVO trainerVO = trainerManageService.selectTrainerInfo(tid);
		String start = trainerVO.getWork_start();
		String end = trainerVO.getWork_end();
		String sHour = start.split(":")[0];
		String sMin = start.split(":")[1];
		String eHour = end.split(":")[0];
		String eMin = end.split(":")[1];
		
		LocalTime startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMin));
		LocalTime endTime = LocalTime.of(Integer.parseInt(eHour), Integer.parseInt(eMin));
		
		for(int i = 0; i < defaultTimes.size(); i++) {
			String hour = defaultTimes.get(i).split(":")[0];
			String min = defaultTimes.get(i).split(":")[1];
			LocalTime time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(min));
			
			// 리스트 안의 시간이 근무시작 시간 이후이고 근무종료 시간 이전이면 timeList에 더한다
			if(time.isAfter(startTime) && time.isBefore(endTime)) {
				timeList.add(defaultTimes.get(i));
			}
		}
		
		// 근무 시간 내에 해당 시간이 존재하는지 체크		
		int count = 0;
		
		for(int i = 0; i < timeList.size(); i++) {
			if(timeList.get(i).equals(class_time)) {
					count++;
			} 
		}
		
		// 근무 시간 내에 시간이 존재하면 true 반환, 존재하지 않으면 false 반환
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
}
