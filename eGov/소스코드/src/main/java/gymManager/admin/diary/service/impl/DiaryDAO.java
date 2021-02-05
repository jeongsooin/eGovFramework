package gymManager.admin.diary.service.impl;

import org.springframework.stereotype.Repository;

import gymManager.admin.diary.DiaryVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : DiaryDAO.java
 * @Description : DiaryDAO DAO Class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Repository("diaryDAO")
public class DiaryDAO extends ComOslaglAbstractDAO {

	/**
	 * DIARY 수업일지 정보 조회
	 * @param DiaryVO 
	 * @return DiaryVO 
	 * @exception Exception
	 */
	public DiaryVO selectDiaryInfo(DiaryVO diaryVO) throws Exception {
		return (DiaryVO)select("diaryDAO.selectDiaryInfo", diaryVO);
	}
	
	/**
	 * DIARY 수업일지 정보 등록
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void insertDiaryInfo(DiaryVO diaryVO) throws Exception {
		insert("diaryDAO.insertDiaryInfo", diaryVO);
	}
	
	/**
	 * DIARY 수업일지 정보 수정
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void updateDiaryInfo(DiaryVO diaryVO) throws Exception {
		update("diaryDAO.updateDiaryInfo", diaryVO);
	}
	
	/**
	 * DIARY 수업일지 정보 삭제
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void deleteDiaryInfo(DiaryVO diaryVO) throws Exception {
		delete("diaryDAO.deleteDiaryInfo", diaryVO);
	}
	
	/**
	 * DIARY 수업번호에 수업일지가 존재하는지 조회
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public DiaryVO selecDiaryExist(int cid) throws Exception {
		return (DiaryVO) select("diaryDAO.selectDiaryExist", cid);
	}
}
