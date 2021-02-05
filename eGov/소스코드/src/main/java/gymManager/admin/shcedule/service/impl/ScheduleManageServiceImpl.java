package gymManager.admin.shcedule.service.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.shcedule.ScheduleVO;
import gymManager.admin.shcedule.service.ScheduleManageService;

/**
 * @Class Name : ScheduleManageServiceImpl.java
 * @Description : ScheduleManageServiceImpl 비즈니스 로직 구현체 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Service("scheduleManageService")
public class ScheduleManageServiceImpl extends EgovAbstractServiceImpl implements ScheduleManageService {

	/** ScheduleDAO DI */
	@Resource(name="scheduleDAO")
	private ScheduleDAO scheduleDAO;
	
	/**
	 * Schedule 테이블 수업 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheuldeList() throws Exception {
		return scheduleDAO.selectScheduleList();
	}

	/**
	 * Schedule 테이블 수업 정보 삭제
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		scheduleDAO.deleteScheduleInfo(scheduleVO);
		
	}
	
	/**
	 * Schedule 테이블 수업 정보 등록
	 * @param ScheduleVO
	 * @return int
	 * @exception Exception
	 */
	public void insertScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		scheduleDAO.insertScheduleInfo(scheduleVO);
	}
	
	/**
	 * Schedule 테이블 회원 수업 목록 조회
	 * @param int
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleMember(int mid) throws Exception {
		return scheduleDAO.selectScheduleMember(mid);
	}
	
	/**
	 * Schedule 테이블 트레이너 수업 목록 조회
	 * @param String
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleTrainer(String tid) throws Exception {
		return scheduleDAO.selectScheduleTrainer(tid);
	}
	
	/**
	 * Schedule 테이블 수업 목록 수정
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	@Override
	public void updateScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		scheduleDAO.updateScheduleInfo(scheduleVO);
	}
	
	/**
	 * 두 날짜간의 차이를 구함
	 * @param Date
	 * @return long
	 * @exception Exception
	 */
	public int getDateDiff(Date toDate, String toTime) throws Exception {

		// diffDays > 0  : 예정		diffDays < 0  : 만료	diffDays == 0 : 진행
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayStr = dayTime.format(new Date(time));
		Date day1 = dayTime.parse(dayStr);
		
		String day = new SimpleDateFormat("yyyy-MM-dd").format(toDate);
		String dayAndTime = day + " " + toTime + ":00";
		Date day2 = dayTime.parse(dayAndTime);
		
		long diff = day2.getTime() - day1.getTime();
		long diffMinitues = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60* 60* 1000);

		if(diffDays < 0) {
			return 2;  // 완료
		} else if(diffMinitues <= 0 && diffHours <= 0 && diffDays == 0){
			return 1; // 진행
		} else {
			return 0; // 예정
		}
	}
}
