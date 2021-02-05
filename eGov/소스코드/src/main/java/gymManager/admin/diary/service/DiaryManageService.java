package gymManager.admin.diary.service;

import gymManager.admin.diary.DiaryVO;

public interface DiaryManageService {

	/**
	 * selectDiaryInfo 수업일지 정보 조회
	 * @param DiaryVO
	 * @return DiaryVO
	 * @exception Exception
	 */
	public DiaryVO selectDiaryInfo(DiaryVO diaryVO) throws Exception;
	
	/**
	 * insertDiaryInfo 수업일지 정보 등록
	 * @param DiaryVO
	 * @return void
	 * @exception Exception
	 */
	public void insertDiaryInfo(DiaryVO diaryVO) throws Exception;
	
	/**
	 * updateDiaryInfo 수업일지 정보 수정
	 * @param DiaryVO
	 * @return void
	 * @exception Exception
	 */
	public void updateDiaryInfo(DiaryVO diaryVO) throws Exception;
	
	/**
	 * deleteDiaryInfo 수업일지 정보 삭제
	 * @param DiaryVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteDiaryInfo(DiaryVO diaryVO) throws Exception;
	
	/**
	 * DIARY 수업번호에 수업일지가 존재하는지 조회
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public DiaryVO selecDiaryExist(int cid) throws Exception;
}
