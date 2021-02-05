package gymManager.admin.trainer.service;

import java.util.List;

import gymManager.admin.trainer.TrainerVO;

public interface TrainerManageService {
	/**
	 * SelectTrainer 트레이너 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	List selectTrainerList() throws Exception;
	
	/**
	 * SelectTrainerInfo 트레이너 정보 조회
	 * @param String
	 * @return TrainerVO
	 * @exception Exception
	 */
	public TrainerVO selectTrainerInfo(String tid) throws Exception;
	
	/**
	 * InsertTrainer 트레이너 정보 등록
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	void insertTrainerInfo(TrainerVO trainerVO) throws Exception;
	
	/**
	 * UpdateTrainer 트레이너 정보 수정
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	void updateTrainerInfo(TrainerVO trainerVO) throws Exception;
	
	/**
	 * DeleteTrainer 트레이너 정보 삭제
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	void deleteTrainerInfo(TrainerVO trainerVO) throws Exception;
	
	/**
	 * UpdateTrainer 트레이너 개인 수업 횟수 갱신
	 * @param String
	 * @return void
	 * @exception Exception
	 */
	void updateTrainerPTCount(String tid) throws Exception;
}
