package gymManager.admin.shcedule.service;

import java.util.Date;
import java.util.List;

import gymManager.admin.shcedule.ScheduleVO;

public interface ScheduleManageService {

	/**
	 * SelectScheduleList 모든 수업일정 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheuldeList() throws Exception;
	
	/**
	 * SelectScheduleMember 특정 아이디를 가진 회원의 수업일정 목록 조회
	 * @param int
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleMember(int mid) throws Exception;
	
	/**
	 * SelectScheduleTrainer 특정 아이디를 가진 트레이너의 수업일정 목록 조회
	 * @param String
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleTrainer(String tid) throws Exception;
	
	/**
	 * InsertScheduleInfo 신규 수업일정 정보 등록
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void insertScheduleInfo(ScheduleVO scheduleVO) throws Exception;
	
	/**
	 * UpdateScheduleInfo 선택한 수업일정의 정보 수정
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void updateScheduleInfo(ScheduleVO scheduleVO) throws Exception;
	
	/**
	 * DeleteScheduleInfo 선택한 수업일정의 정보 삭제
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteScheduleInfo(ScheduleVO scheduleVO) throws Exception;
	
	/**
	 * 두 날짜간의 차이를 구함
	 * @param Date
	 * @return long
	 * @exception Exception
	 */
	public int getDateDiff(Date toDate, String toTime) throws Exception; 

}

