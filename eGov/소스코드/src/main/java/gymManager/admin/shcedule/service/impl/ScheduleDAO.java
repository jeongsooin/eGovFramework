package gymManager.admin.shcedule.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gymManager.admin.shcedule.ScheduleVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : ScheduleDAO.java
 * @Description : ScheduleDAO DAO Class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Repository("scheduleDAO")
public class ScheduleDAO extends ComOslaglAbstractDAO {

	/**
	 * SCHEDULE 모든 수업일정 정보 조회(데이터 테이블 조회용)
	 * @param 
	 * @return List 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleList() throws Exception {
		return list("scheduleDAO.selectScheduleList");
	}
	
	/**
	 * SCHEDULE 특정 회원의 수업일정 정보 조회
	 * @param int
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleMember(int mid) throws Exception {
		return list("scheduleDAO.selectScheduleMember", mid);
	}
	
	/**
	 * SCHEDULE 특정 트레이너의 수업일정 정보 조회
	 * @param String
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectScheduleTrainer(String tid) throws Exception {
		return list("scheduleDAO.selectScheduleTrainer", tid);
	}
	
	/**
	 * SCHEDULE 수업일정 정보 등록
	 * @param ScheduleVO
	 * @return void 
	 * @exception Exception
	 */
	public void insertScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		insert("scheduleDAO.insertScheduleInfo", scheduleVO);
	}
	
	/**
	 * SCHEDULE 수업일정 정보 수정
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void updateScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		update("scheduleDAO.updateScheduleInfo", scheduleVO);
	}
	
	/**
	 * SCHEDULE 수업일정 정보 삭제
	 * @param ScheduleVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteScheduleInfo(ScheduleVO scheduleVO) throws Exception {
		delete("scheduleDAO.deleteScheduleInfo", scheduleVO);
	}
}
