package gymManager.admin.locker.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gymManager.admin.locker.LockerVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : LockerDAO.java
 * @Description : LockerDAO DAO Class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 *  
 */
@Repository("lockerDAO")
public class LockerDAO extends ComOslaglAbstractDAO {

	/**
	 * LOCKER 모든 사물함 정보 조회(데이터 테이블 조회용)
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectLockerList() throws Exception {
		return list("lockerDAO.selectLockerList");
	}
	
	/**
	 * LOCKER 회원 한명에게 배정된 사물함 정보 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectLockerInfo(int mid) throws Exception {
		return list("lockerDAO.selectLockerInfo", mid);
	}
	
	/**
	 * LOCKER 사물함 정보 수정(사물함 배정 및 수정)
	 * @param LockerVO
	 * @return void
	 * @exception Exception
	 */
	public void updateLockerInfo(LockerVO lockerVO) throws Exception {
		update("lockerDAO.updateLockerInfo", lockerVO);
	}
	
	/**
	 * LOCKER 사물함 배정 정보 삭제(기본값으로 초기화)
	 * @param LockerVO
	 * @return void
	 * @exception Exception
	 */
	public void resetLockerInfo(LockerVO lockerVO) throws Exception {
		update("lockerDAO.resetLockerInfo", lockerVO);
;	}
}
