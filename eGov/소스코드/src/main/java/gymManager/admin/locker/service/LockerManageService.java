package gymManager.admin.locker.service;

import java.util.List;

import gymManager.admin.locker.LockerVO;

public interface LockerManageService {

	/**
	 * SelectLockerList 모든 사물함 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	List selectLockerList() throws Exception;
	
	/**
	 * SelectLockerInfo 회원에게 배정된 사물함 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	List selectLockerInfo(int mid) throws Exception;
	
	/**
	 * UpdateLockerInfo 사물함 정보 수정
	 * @param LockerVO
	 * @return 
	 * @exception Exception
	 */
	void updateLockerInfo(LockerVO lockerVO) throws Exception;
	
	/**
	 * ResetLockerInfo 사물함 정보 수정
	 * @param LockerVO
	 * @return void
	 * @exception Exception
	 */
	void resetLockerInfo(LockerVO lockerVO) throws Exception;
	
}
