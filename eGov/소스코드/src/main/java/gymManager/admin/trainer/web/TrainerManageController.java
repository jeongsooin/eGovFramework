package gymManager.admin.trainer.web;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;
import gymManager.admin.shcedule.service.ScheduleManageService;
import gymManager.admin.trainer.TrainerVO;
import gymManager.admin.trainer.service.TrainerManageService;
import gymManager.com.vo.LoginVO;

/**
 * @Class Name : TrainerManageController.java
 * @Description : TrainerManageController Controller class
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
@Controller
public class TrainerManageController {

	/** 로그4j 로거 로딩 */
	private static final Logger Log = Logger.getLogger(TrainerManageController.class);

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** MemberManageService DI */
	@Resource(name = "trainerManageService")
	private TrainerManageService trainerManageService;
	
	/** ScheduleManageService DI */
	@Resource(name = "scheduleManageService")
	private ScheduleManageService scheduleManageService;

	/** TRACE */
	@Resource(name = "leaveaTrace")
	LeaveaTrace leaveaTrace;

	/**
	 *
	 * @Description 트레이너 목록 조회 후 트레이너관리 메뉴 화면을 세팅한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/trainerView.do")
	public ModelAndView trainerView(HttpServletRequest requset, HttpServletResponse response) throws Exception {

		try {

			// 트레이너 정보 목록을 DB에서 조회한다
			List<TrainerVO> list = trainerManageService.selectTrainerList();

			// 트레이너 정보 리스트를 attribute에 추가한다
			ModelAndView mv = new ModelAndView();
			mv.setViewName("admin/trainer/trainer");
			mv.addObject("list", list);

			return mv;
		} catch (Exception e) {
			Log.debug("=====ERROR : MemberManageContoller.memberList()=====");
			Log.debug(e);
		}
		return new ModelAndView("admin/trainer/trainer");
	}

	/**
	 *
	 * @Description 트레이너 등록 정보를 받아오고 DB에 삽입한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertTrainer.do")
	public void insertTrainer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String[] times = {"00:00 08:00", "00:30 08:30", "01:00 09:00", "01:30 09:30", "02:00 10:00", "02:30 10:30", "03:00 11:00", "03:30 11:30", 
		 		 		  "04:00 12:00", "04:30 12:30", "05:00 13:00", "05:30 13:30", "06:00 14:00", "06:30 14:30", "07:00 15:00", "07:30 15:30",
		 		 		  "08:00 16:00", "08:30 16:30", "09:00 17:00", "09:30 17:30", "10:00 18:00", "10:30 18:30", "11:00 19:00", "11:30 19:30",
		 		 		  "12:00 20:00", "12:30 20:30", "13:00 21:00", "13:30 21:30", "14:00 22:00", "14:30 22:30", "15:00 23:00", "15:30 23:30",
		 		 		  "16:00 00:00", "16:30 00:30", "17:00 01:00", "17:30 01:30", "18:00 02:00", "18:30 02:30", "19:00 03:00", "19:30 03:30",
		 		 		  "20:00 04:00", "20:30 04:30", "21:00 05:00", "21:30 05:30", "22:00 06:00", "22:30 06:30", "23:00 07:00", "23:30 07:30"};
		
		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			String tid = request.getParameter("tid");
			String name = request.getParameter("name");
			String gen = request.getParameter("gen");
			String telNo = request.getParameter("tel0") + request.getParameter("tel1") + request.getParameter("tel2");
			int work_time = Integer.parseInt(request.getParameter("work_time"));
			String work_start = times[work_time].split(" ")[0];
			String work_end = times[work_time].split(" ")[1];
			
			// value 값을 VO에 세팅한다
			TrainerVO trainerVO = new TrainerVO();
			trainerVO.setTid(tid);
			trainerVO.setName(name);
			trainerVO.setGen(gen);
			trainerVO.setTelNo(telNo);
			trainerVO.setWork_start(work_start);
			trainerVO.setWork_end(work_end);
			trainerVO.setReg_usr_id(loginID);
			trainerVO.setModify_usr_id(loginID);

			// VO에 정보를 담아 DB에 삽입한다
			trainerManageService.insertTrainerInfo(trainerVO);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록 되었습니다'); location.href='/trainerView.do';</script>");
			out.flush();
		} catch (Exception e) {
			Log.debug(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('등록에 실패하였습니다'); location.href='/trainerView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 트레이너 수정 정보를 받아와 DB에 update한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTrainer.do")
	public void updateTrainer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String[] times = {"00:00 08:00", "00:30 08:30", "01:00 09:00", "01:30 09:30", "02:00 10:00", "02:30 10:30", "03:00 11:00", "03:30 11:30", 
						  "04:00 12:00", "04:30 12:30", "05:00 13:00", "05:30 13:30", "06:00 14:00", "06:30 14:30", "07:00 15:00", "07:30 15:30",
						  "08:00 16:00", "08:30 16:30", "09:00 17:00", "09:30 17:30", "10:00 18:00", "10:30 18:30", "11:00 19:00", "11:30 19:30",
						  "12:00 20:00", "12:30 20:30", "13:00 21:00", "13:30 21:30", "14:00 22:00", "14:30 22:30", "15:00 23:00", "15:30 23:30",
						  "16:00 00:00", "16:30 00:30", "17:00 01:00", "17:30 01:30", "18:00 02:00", "18:30 02:30", "19:00 03:00", "19:30 03:30",
						  "20:00 04:00", "20:30 04:30", "21:00 05:00", "21:30 05:30", "22:00 06:00", "22:30 06:30", "23:00 07:00", "23:30 07:30"};
		
		try {

			// request로부터 parameter의 value를 받아온다
			HttpSession session = request.getSession();
			LoginVO vo = (LoginVO) session.getAttribute("loginVO");
			String loginID = vo.getUsrId();
			String tid = request.getParameter("tid");
			String name = request.getParameter("name");
			String gen = request.getParameter("gen");
			String telNo = request.getParameter("tel0") + request.getParameter("tel1") + request.getParameter("tel2");
			int work_time = Integer.parseInt(request.getParameter("work_time"));
			String work_start = times[work_time].split(" ")[0];
			String work_end = times[work_time].split(" ")[1];
			

			// value 값을 VO에 세팅한다
			TrainerVO trainerVO = new TrainerVO();
			trainerVO.setTid(tid);
			trainerVO.setName(name);
			trainerVO.setGen(gen);
			trainerVO.setTelNo(telNo);
			trainerVO.setWork_start(work_start);
			trainerVO.setWork_end(work_end);
			trainerVO.setModify_usr_id(loginID);

			// VO에 정보를 담아 DB를 수정한다
			trainerManageService.updateTrainerInfo(trainerVO);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정 되었습니다'); location.href='/trainerView.do';</script>");
			out.flush();
		} catch (Exception e) {
			Log.debug(e);

			// 결과 메시지 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('수정에 실패하였습니다'); location.href='/trainerView.do';</script>");
			out.flush();
		}
	}

	/**
	 *
	 * @Description 삭제할 트레이너정보의 아이디를 받아와 DB에서 삭제한다
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return void
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTrainerAjax.do")
	public ModelAndView deleteTrainerAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		try {

			// request로부터 parameter의 value를 받아온 후 삭제할 레코드 값을 세팅한다
			String tid = request.getParameter("tid");
			TrainerVO trainerVO = new TrainerVO();
			trainerVO.setTid(tid);
			
			// 배정된 수업이 있는지 체크해서 수업이 존재하면 삭제하지 않는다
			if(scheduleManageService.selectScheduleTrainer(tid).size() == 0) {
				// DB에서 삭제한다
				trainerManageService.deleteTrainerInfo(trainerVO);

				// 삭제 성공 메시지 세팅
				model.addAttribute("message", "yes");
				return new ModelAndView("jsonView");
			} else {
				// 삭제 실패 메시지 세팅
				model.addAttribute("message", "schedule");
				return new ModelAndView("jsonView");
			}
						
		} catch (Exception e) {
			Log.debug(e);

			// 삭제 실패 메시지 세팅
			model.addAttribute("message", "schedule");
			return new ModelAndView("jsonView");
		}
	}
	
	@RequestMapping(value="/selectTrainerIdAjax.do")
	public ModelAndView selectTrainerIdAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		try {
			
			String tid = request.getParameter("tid");

			if(trainerManageService.selectTrainerInfo(tid) == null){
				//사용가능 메시지 세팅
	    		model.addAttribute("message", "yes");
	    		return new ModelAndView("jsonView");
			} else {
				//사용 불가 메시지 세팅
	        	model.addAttribute("message", "no");
	        	return new ModelAndView("jsonView");
			}
	              	
		} catch (Exception e) {
			Log.error(e);
			//조회실패 메시지 세팅
    		model.addAttribute("message", "fail");
    		return new ModelAndView("jsonView");

		}
	}
}
