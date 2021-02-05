package gymManager.admin.statistics.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;
import gymManager.admin.diary.service.DiaryManageService;
import gymManager.admin.locker.LockerVO;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.admin.shcedule.web.ScheduleManageController;
import gymManager.admin.statistics.service.StatisticsManageService;
import gymManager.admin.trainer.service.TrainerManageService;

/**
 * @Class Name : StatisticsManageController.java
 * @Description : StatisticsManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.18.
 * @version 1.0
 * @see
 */
@Controller
public class StatisticsManageController {

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
	
	/** StatisticsManageService DI */
	@Resource(name= "statisticsManageService")
	private StatisticsManageService statisticsManageService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;
	
	/**
	 *
	 * @Description 통계메뉴 화면으로 이동한다
	 *              1. 전체 등록 회원 수와 각 성별의 수를 조회하여 뷰에 통계 데이터로 넘겨준다.
	 *              2. 전체 사물함 갯수(100개) 중 사용되고 있는 사물함(MID가 0이 아닌 사물함)의 갯수를 조회하여 뷰에 통계 데이터로 넘겨준다
	 *              3. 단위 기간 별로 이용권을 등록한 회원의 수를 조회하여 뷰에 통계 데이터로 넘겨준다.
	 *              4. 매 월별 등록을 시작한 회원의 수를 배열로 저장하여 뷰에 통계 데이터로 넘겨준다.
	 *              5. 매 월별 진행된 개인 수업 횟수를 배열로 저장하여 뷰에 통계 데이터로 넘겨준다.
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="statisticsView.do")
	public ModelAndView statisticsView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			
			/*
			 * 1. 현재 사용되고 있는 사물함의 VO를 저장할 리스트를 선언하고 DB에서 조회하여 데이터를 저장한다
			 * 2. 1번의 리스트를 사용하고 있는 회원의 이름을 저장할 리스트를 선언한다
			 * 3. 각 사물함VO에 저장된 MID를 이용하여 회원 이름을 조회하여 2번의 리스트에 저장한다
			 * */
			List<LockerVO> usedLockerList = new ArrayList<LockerVO>();
			usedLockerList = statisticsManageService.selectUsedLockerCount();
			List<String> namesOfUsingLocker = new ArrayList<String>();
			List<MemberVO> memberGenList = statisticsManageService.selectAllGenList();
			
			/*
			 * 1. usedLockerCount : 현재 사용자가 배정되어 있는 사물함의 갯수를 조회하여 변수에 저장
			 * 2. allMemberCount : 등록되어 있는 모든 회원의 수를 조회하여 변수에 저장
			 * 3. maleMemberCount : 등록되어 있는 모든 회원 중 성별이 남성인 회원의 수
			 * 4. femaleMemberCount : 등록되어 있는 모든 회원 중 성별이 여성인 회원의 수 ( 2번 - 3번 = 4번)
			 * 5. duration : 단위 기간 별로 이용권을 등록한 회원의 수를 저장
			 * 6. membersOfMonth : 매 월별 등록을 시작한 회원의 수를 배열로 저장
			 * 7. ptClassOfMonth : 매 월별 진행된 개인 수업 횟수를 배열로 저장
			 */
			int usedLockerCount = usedLockerList.size();
			int allMemberCount = statisticsManageService.selectAllGenCount();
			int maleMemberCount = statisticsManageService.selectMaleGenCount();
			int femaleMemberCount = allMemberCount - maleMemberCount;
			int[] duration = {0, 0, 0, 0};
			int[] membersOfMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] ptClassOfMonth = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
			// 사용중인 사물함의 리스트에서 mid를 이용해 사물함을 사용중인 회원의 이름 목록을 생성한다
			for(int i = 0 ; i < usedLockerList.size(); i++ ) {
				int mid = usedLockerList.get(i).getMid();
				String mname = memberManageService.selectMemberInfo(mid).getName();
				namesOfUsingLocker.add(mname);
			}
			
			// 모든 회원 목록의 정보 리스트에서 각 회원의 이용권 기간을 조회하여 단위 기간 별로 배열에 저장한다
			for(int i = 0; i < memberGenList.size(); i++) {
				int _duration = statisticsManageService.selectMembershipDuration(memberGenList.get(i));
				if(_duration > 12) {									// 이용 기간이 12개월 초과인 경우
					duration[3] = duration[3] + 1;
				} else if(_duration <= 12 && _duration > 6 ) {			// 이용 기간이 6개월 초과 12개월 이하인 경우
					duration[2] = duration[2] + 1;
				} else if(_duration <= 6 && _duration > 3) {			// 이용 기간이 6개월 이하 3개월 초과인 경우
					duration[1] = duration[1] + 1;
				} else if(_duration <= 3) {								// 이용 기간이 3개월 이하인 경우
					duration[0] = duration[0] + 1;
				}
			}
			
			// 월 별 이용을 시작한 회원의 수와, 월 별 진행된 개인 수업 횟수를 조회하여 배열에 저장한
			// 배열의 index + 1을 월로 사용한다(ex. 1월 = [0], 2월 = [1])
			for(int i = 0; i < 12; i++) {
				int memberCnt = statisticsManageService.selectMembershipMonth(i + 1).size();
				int lessonCnt = statisticsManageService.selectPTCountOfMonth(i + 1).size(); 
				membersOfMonth[i] = membersOfMonth[i] + memberCnt;
				ptClassOfMonth[i] = ptClassOfMonth[i] + lessonCnt;
			}
			
			// 통계 화면에 전달할 데이터를 세팅한다
			ModelAndView mv = new ModelAndView("admin/statistics/statistics");			
			mv.addObject("usedLockerList", usedLockerList);
			mv.addObject("memberGenList", memberGenList);
			mv.addObject("namesList", namesOfUsingLocker);
			mv.addObject("maleCnt", maleMemberCount);
			mv.addObject("femaleCnt", femaleMemberCount);
			mv.addObject("lockerCnt", usedLockerCount);
			mv.addObject("membersOfMonth", membersOfMonth);
			mv.addObject("ptClassOfMonth", ptClassOfMonth);
			mv.addObject("duration", duration);
			
			return mv;
		} catch (Exception e) {
			Log.error(e);
			// 오류 발생 시 메인 화면으로 돌아간다
			return new ModelAndView("redirect:/mainView.do");
		}		
	}
	
	/**
	 *
	 * @Description 성별 별로 정렬된 회원 목록 데이터를 조회하는 Ajax 요청을 수행한 후 결과를 리턴한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @param ModelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/selectAllGenListAjax.do")
	public ModelAndView selectAllGenListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			
			// GEN 컬럼으로 정렬된 회원 목록 데이터를 조회하여 선언한 변수에 저장한다
			List<MemberVO> memberGenList = statisticsManageService.selectAllGenList();
			
			// 조회 성공 메시지와 회원 정보 리스트를 뷰에 담아 jsonView로 리턴한다
			model.addAttribute("memberGenList", memberGenList);
			model.addAttribute("message", "yes");
			
			return new ModelAndView("jsonView");
		} catch (Exception e) {
			Log.error(e);
			// 조회 실패 메시지 세팅
			model.addAttribute("message", "fail");
			return new ModelAndView("jsonView");
		}
	}
}
