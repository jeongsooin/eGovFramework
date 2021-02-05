package gymManager.admin.trainer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import gymManager.admin.trainer.TrainerVO;
import gymManager.admin.trainer.service.TrainerManageService;

/**
 * @Class Name : TrainerManageServiceImpl.java
 * @Description : TrainerManageServiceImpl 비즈니스 로직 구현체 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Service("trainerManageService")
public class TrainerManageServiceImpl extends EgovAbstractServiceImpl implements TrainerManageService {

	/** TrainerDAO DI */
	@Resource(name="trainerDAO")
	private TrainerDAO trainerDAO;
	
	/**
	 * SelectTrainer 트레이너 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectTrainerList() throws Exception {
		return trainerDAO.selectTrainerList();
	}

	/**
	 * SelectTrainer 트레이너 정보 조회
	 * @param String
	 * @return TrainerVO
	 * @exception Exception
	 */
	public TrainerVO selectTrainerInfo(String tid) throws Exception {
		return (TrainerVO)trainerDAO.selectTrainerInfo(tid);
	}
	
	/**
	 * InsertTrainer 트레이너 정보 등록
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void insertTrainerInfo(TrainerVO trainerVO) throws Exception {
		trainerDAO.insertTrainerInfo(trainerVO);		
	}
	
	/**
	 * UpdateTrainer 트레이너 정보 수정
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void updateTrainerInfo(TrainerVO trainerVO) throws Exception {
		trainerDAO.updateTrainerInfo(trainerVO);
	}
	
	/**
	 * DeleteTrainer 트레이너 정보 삭제
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteTrainerInfo(TrainerVO trainerVO) throws Exception {
		trainerDAO.deleteTrainerInfo(trainerVO);
	}
	
	/**
	 * UpdateTrainer 트레이너 개인 수업 횟수 갱신
	 * @param String
	 * @return void
	 * @exception Exception
	 */
	public void updateTrainerPTCount(String tid) throws Exception {
		trainerDAO.updateTrainerPTCount(tid);
	}
}
