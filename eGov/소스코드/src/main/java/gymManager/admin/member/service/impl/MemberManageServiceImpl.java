package gymManager.admin.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.member.MemberVO;
import gymManager.admin.member.service.MemberManageService;

/**
 * @Class Name : MemberServiceImpl.java
 * @Description : MemberServiceImpl Business Implement class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 *  
 */
@Service("memberManageService")
public class MemberManageServiceImpl extends EgovAbstractServiceImpl implements MemberManageService {

	/** MemberDAO DI */
	@Resource(name="memberDAO")
	private MemberDAO memberDAO;
	
	/**
	 * Member 테이블 회원 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectMemberList() throws Exception {
		return memberDAO.selectMemberList();
	}
	
	/**
	 * Member 테이블 회원 정보 조회
	 * @param int
	 * @return MemberVO
	 * @exception Exception
	 */
	public MemberVO selectMemberInfo(int mid) throws Exception {
		return (MemberVO)memberDAO.select("memberDAO.selectMemberInfo", mid);
	}

	/**
	 * Member 테이블 회원 정보 수정
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void updateMemberInfo(MemberVO memberVO) throws Exception {
		memberDAO.updateMemberInfo(memberVO);
	}
	
	/**
	 * Member 테이블 회원 정보 입력
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void insertMemberInfo(MemberVO memberVO) throws Exception {
		memberDAO.insertMemberInfo(memberVO);
	}
	
	/**
	 * Member 테이블 회원 정보 삭제
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteMemberInfo(MemberVO memberVO) throws Exception {
		memberDAO.deleteMemberInfo(memberVO);
	}
	
	/**
	 * Member 테이블 회원 개인 수업 횟수 갱신
	 * @param int
	 * @return void
	 * @exception Exception
	 */
	public void updateMemberPTCount(int mid) throws Exception {
		memberDAO.updateMemberPTCount(mid);
	}
}
