package gymManager.admin.statistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.member.MemberVO;
import gymManager.admin.statistics.service.StatisticsManageService;

/**
 * @Class Name : StatisticsManageServiceImpl.java
 * @Description : 통계메뉴 처리에 필요한 비즈니스 로직 구현체 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.20.
 * @version 1.0
 * @see
 */
@Service("statisticsManageService")
public class StatisticsManageServiceImpl extends EgovAbstractServiceImpl implements StatisticsManageService {

	/** StatisticsDAO DI */
	@Resource(name="statisticsDAO")
	private StatisticsDAO statisticsDAO;
	
	/**
	 * @Description MEMBER 테이블에 존재하는 모든 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectAllGenCount() throws Exception {
		return statisticsDAO.selectAllGenCount();
	}
	
	/**
	 * @Description MEMBER 테이블에 존재하는 남성 회원의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectMaleGenCount() throws Exception {
		return statisticsDAO.selectMaleGenCount();
	}
	
	/**
	 * @Description LOCKER 테이블에서 배정된 회원이 존재하는 모든 사물함의 수 조회
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectUsedLockerCount() throws Exception {
		return statisticsDAO.selectUsedLockerCount();
	}
		
	/**
	 * @Description MemberVO에 저장된 회원의 이용권이 몇 개월동안 이용가능한지 조회한다
	 * @param 
	 * @return int 
	 * @exception Exception
	 */
	public int selectMembershipDuration(MemberVO memberVO) throws Exception {
		return statisticsDAO.selectMembershipDuration(memberVO);
	}
	
	/**
	 * @Description 월 별 진행한 개인수업 횟수와 정보를 조회한다
	 * @param int
	 * @return List 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectPTCountOfMonth(int month) throws Exception {
		return statisticsDAO.selectPTCountOfMonth(month);
	}
	
	/**
	 * @Description 성별 별로 정렬한 회원 목록을 조회한다
	 * @param 
	 * @return 
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectAllGenList() throws Exception {
		return statisticsDAO.selectAllGenList();
	}
	
   /**
	* @Description 인자로 입력한 달(정수, month)에 등록한 회원의 정보를 조회한다
	* @param int
	* @return List
	* @exception Exception
	*/ 
	@SuppressWarnings("rawtypes")
	public List selectMembershipMonth(int month) throws Exception {
		return statisticsDAO.selectMembershipMonth(month);
	}
}
