package gymManager.admin.member.service.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import gymManager.admin.member.MemberVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : MemberDAO.java
 * @Description : MEMBER 테이블의 데이터에 접근하고 관리하는 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 *  
 */
@Repository("memberDAO")
public class MemberDAO extends ComOslaglAbstractDAO {

	/**
	 * MEMBER 모든 회원 정보 조회(데이터 테이블 조회용)
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectMemberList() throws Exception {
		return list("memberDAO.selectMemberList");
	}
	
	/**
	 * MEMBER 회원 아이디로 회원 정보 조회
	 * @param MemberVO
	 * @return MemberVO 
	 * @exception Exception
	 */
	public MemberVO selectMemberInfo(MemberVO memberVO) throws Exception {
		return (MemberVO)select("memberDAO.selecMemberInfo", memberVO);
	}
	
	/**
	 * MEMBER 회원 정보 수정
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void updateMemberInfo(MemberVO memberVO) throws Exception {
		update("memberDAO.updateMemberInfo", memberVO);
	}
	
	/**
	 * MEMBER 회원 정보 입력
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void insertMemberInfo(MemberVO memberVO) throws Exception {
		insert("memberDAO.insertMemberInfo", memberVO);
	}
	
	/**
	 * MEMBER 회원 정보 삭제
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteMemberInfo(MemberVO memberVO) throws Exception {
		delete("memberDAO.deleteMemberInfo", memberVO);
	}
	
	/**
	 * MEMBER 회원 개인수업 횟수 갱신
	 * @param int
	 * @return void
	 * @exception Exception
	 */
	public void updateMemberPTCount(int mid) throws Exception {
		update("memberDAO.updateMemberPTCount", mid);
	}
}
