package gymManager.admin.diary.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.diary.DiaryVO;
import gymManager.admin.diary.service.DiaryManageService;

/**
 * @Class Name : DiaryManageServiceImpl.java
 * @Description : DiaryManageServiceImpl 비즈니스 로직 구현체 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 */
@Service("diaryManageService")
public class DiaryManageServiceImpl extends EgovAbstractServiceImpl implements DiaryManageService {

	/** DiaryDAO DI */
	@Resource(name="diaryDAO")
	private DiaryDAO diaryDAO;
	
	/**
	 * Diary 테이블 수업일지 정보 조회
	 * @param DiaryVO
	 * @return DiaryVO
	 * @exception Exception
	 */
	public DiaryVO selectDiaryInfo(DiaryVO diaryVO) throws Exception {
		return diaryDAO.selectDiaryInfo(diaryVO);
	}
	
	/**
	 * Diary 테이블 수업일지 정보 등록
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void insertDiaryInfo(DiaryVO diaryVO) throws Exception {
		diaryDAO.insertDiaryInfo(diaryVO);
	}
	
	/**
	 * Diary 테이블 수업일지 정보 수정
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void updateDiaryInfo(DiaryVO diaryVO) throws Exception {
		diaryDAO.updateDiaryInfo(diaryVO);		
	}
	
	/**
	 * Diary 테이블 수업일지 정보 삭제
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public void deleteDiaryInfo(DiaryVO diaryVO) throws Exception {
		diaryDAO.deleteDiaryInfo(diaryVO);
	}
	
	/**
	 * DIARY 수업번호에 수업일지가 존재하는지 조회
	 * @param DiaryVO
	 * @return 
	 * @exception Exception
	 */
	public DiaryVO selecDiaryExist(int cid) throws Exception {
		return diaryDAO.selecDiaryExist(cid);
	}
}
