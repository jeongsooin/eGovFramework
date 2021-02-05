package gymManager.admin.trainer.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import gymManager.admin.trainer.TrainerVO;
import gymManager.com.dao.ComOslaglAbstractDAO;

/**
 * @Class Name : TrainerDAO.java
 * @Description : TrainerDAO DAO Class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Repository("trainerDAO")
public class TrainerDAO extends ComOslaglAbstractDAO {

	/**
	 * SelectTrainer 트레이너 목록 조회
	 * @param 
	 * @return List
	 * @exception Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectTrainerList() throws Exception {
		return list("trainerDAO.selectTrainerList");
	}
	
	/**
	 * TRAINER 트레이너 아이디로 정보 조회
	 * @param String
	 * @return TrainerVO
	 * @exception Exception
	 */
	public TrainerVO selectTrainerInfo(String tid) throws Exception {
		return (TrainerVO)select("trainerDAO.selectTrainerInfo", tid);
	}
	
	/**
	 * InsertTrainer 트레이너 정보 등록
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void insertTrainerInfo(TrainerVO trainerVO) throws Exception {
		insert("trainerDAO.insertTrainerInfo", trainerVO);
	}
	
	/**
	 * UpdateTrainer 트레이너 정보 수정
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void updateTrainerInfo(TrainerVO trainerVO) throws Exception {
		update("trainerDAO.updateTrainerInfo", trainerVO);
	}
	
	/**
	 * DeleteTrainer 트레이너 정보 삭제
	 * @param TrainerVO
	 * @return void
	 * @exception Exception
	 */
	public void deleteTrainerInfo(TrainerVO trainerVO) throws Exception {
		delete("trainerDAO.deleteTrainerInfo", trainerVO);
	}
	
	/**
	 * UpdateTrainer 트레이너 개인 수업 횟수 갱신
	 * @param String
	 * @return void
	 * @exception Exception
	 */
	public void updateTrainerPTCount(String tid) throws Exception {
		update("trainerDAO.updateTrainerPTCount", tid);
	}
}
