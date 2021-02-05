package gymManager.admin.locker.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.locker.LockerVO;
import gymManager.admin.locker.service.LockerManageService;

/**
 * @Class Name : LockerManageServiceImpl.java
 * @Description : LockerManageServiceImpl 비즈니스 로직 구현체 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see

 */
@Service("lockerManageService")
public class LockerManageServiceImpl extends EgovAbstractServiceImpl implements LockerManageService {

	/** MemberDAO DI */
	@Resource(name="lockerDAO")
	private LockerDAO lockerDAO;
	
	/**
	 * LOCKER 테이블 사물함 정보 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectLockerList() throws Exception {
		return lockerDAO.selectLockerList();
	}
	
	/**
	 * LOCKER 테이블 회원에게 배정된 사물함 정보 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectLockerInfo(int mid) throws Exception {
		return lockerDAO.selectLockerInfo(mid);
	}
	
	/**
	 * LOCKER 테이블 사물함 정보 수정
	 * @param LockerVO
	 * @return void
	 * @exception Exception
	 */
	public void updateLockerInfo(LockerVO lockerVO) throws Exception {
		lockerDAO.updateLockerInfo(lockerVO);
	}
	
	/**
	 * LOCKER 테이블 사물함 배정 정보 삭제(기본값으로 초기화)
	 * @param LockerVO
	 * @return void
	 * @exception Exception
	 */
	public void resetLockerInfo(LockerVO lockerVO) throws Exception {
		lockerDAO.resetLockerInfo(lockerVO);
	}
}
