package gymManager.admin.statistics.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gymManager.admin.member.MemberVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : StatisticsDAO.java
 * @Description : 통계 메뉴의 데이터를 처리하는 DAO Class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.20.
 * @version 1.0
 * @see
 */
@Repository("statisticsDAO")
public class StatisticsDAO extends ComOslaglAbstractDAO {

	/**
	 * @Description MEMBER 테이블에 존재하는 모든 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectAllGenCount() throws Exception {
		return (int) select("statisticsDAO.selectAllGenCount");
	}
	
	/**
	 * @Description MEMBER 테이블에 존재하는 남성 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectMaleGenCount() throws Exception {
		return (int) select("statisticsDAO.selectMaleGenCount");
	}
	
	/**
	 * @Description LOCKER 테이블에서 배정된 회원이 존재하는 모든 사물함의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectUsedLockerCount() throws Exception {
		return list("statisticsDAO.selectUsedLockerCount");
	}
	
	/**
	 * @Description 월 별 진행한 개인수업 횟수와 정보를 조회한다
	 * @param int
	 * @return List 
	 * @exception Exception
	 */
	 @SuppressWarnings("rawtypes")
	public List selectPTCountOfMonth(int month) throws Exception {
		 return list("statisticsDAO.selectPTCountOfMonth", month);
	 }
	 
	 /**
	  * @Description MemberVO에 저장된 회원의 이용권이 몇 개월동안 이용가능한지 조회한다
	  * @param 
	  * @return int 
	  * @exception Exception
	  */
	 public int selectMembershipDuration(MemberVO memberVO) throws Exception {
		 return (int) select("statistics.selectMembershipDuration", memberVO);
	 }
	 
	/**
	 * @Description 성별 별로 정렬한 회원 목록을 조회한다
	 * @param 
     * @return 
     * @exception Exception
	 */
	 @SuppressWarnings("rawtypes")
	public List selectAllGenList() throws Exception {
		 return list("statisticsDAO.selectAllGenList");
	 }
	
	 /**
	  * @Description 인자로 입력한 달(정수, month)에 등록한 회원의 정보를 조회한다
	  * @param int
	  * @return List
	  * @exception Exception
	  */ 
	@SuppressWarnings("rawtypes")
	public List selectMembershipMonth(int month) throws Exception {
		 return list("statistics.selectMembershipMonth", month);
	 }
}	
