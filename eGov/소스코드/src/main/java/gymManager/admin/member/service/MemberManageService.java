package gymManager.admin.member.service;

import java.util.List;

import gymManager.admin.member.MemberVO;

public interface MemberManageService {

	/**
	 * SelectMemberList 회원 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	List selectMemberList() throws Exception;
	
	/**
	 * SelectMemberInfo 회원 정보 조회
	 * @param int
	 * @return MemberVO
	 * @exception Exception
	 */
	public MemberVO selectMemberInfo(int mid) throws Exception;
	
	/**
	 * UpdateMemberInfo 회원 정보 수정
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	void updateMemberInfo(MemberVO memberVO) throws Exception;
	
	/**
	 * InsertMemberInfo 회원 정보 입력
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	void insertMemberInfo(MemberVO memberVO) throws Exception;
	
	/**
	 * DeleteMemberInfo 회원 정보 삭제
	 * @param MemberVO
	 * @return void
	 * @exception Exception
	 */
	void deleteMemberInfo(MemberVO memberVO) throws Exception;
	
	/**
	 * UpdateMemberPTCount 회원 개인수업 횟수 갱신
	 * @param int
	 * @return void
	 * @exception Exception
	 */
	void updateMemberPTCount(int mid) throws Exception;
}
