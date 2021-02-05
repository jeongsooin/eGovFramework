package gymManager.admin.statistics.service;

import java.util.List;

import gymManager.admin.member.MemberVO;

public interface StatisticsManageService {
	
	/**
	 * @Description MEMBER 테이블에 존재하는 모든 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectAllGenCount() throws Exception;
	
	/**
	 * @Description MEMBER 테이블에 존재하는 남성 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectMaleGenCount() throws Exception;
	
	/**
	 * @Description LOCKER 테이블에서 배정된 회원이 존재하는 모든 사물함의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectUsedLockerCount() throws Exception;
	
	/**
	 * @Description MemberVO에 저장된 회원의 이용권이 몇 개월동안 이용가능한지 조회한다
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	 public int selectMembershipDuration(MemberVO memberVO) throws Exception;
	
	/**
	 * @Description 월 별 진행한 개인수업 횟수와 정보를 조회한다
	 * @param int
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectPTCountOfMonth(int month) throws Exception;
	
	/**
	 * @Description 회원 목록을 성별 순서로 정렬하여 조회한다
	 * @param 
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectAllGenList() throws Exception;
	
	/**
	  * @Description 인자로 입력한 달(정수, month)에 등록한 회원의 정보를 조회한다
	  * @param int
	  * @return List
	  * @exception Exception
	  */ 
	@SuppressWarnings("rawtypes")
	public List selectMembershipMonth(int month) throws Exception;
}
