package gymManager.admin.trainer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Class Name : TrainerVO.java
 * @Description : TRAINER 테이블의 정보를 저장하기 위한 VO 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
public class TrainerVO {
	
	/* TRAINER 테이블의 데이터를 저장하는 변수 */
	private String tid;
	private String name;
	private String gen;
	private String pw;
	private String telNo;
	private int pt_count;
	private String work_start;
	private String work_end;
	private String use_yn;
	private Date reg_dtm;
	private String reg_usr_id;
	private String reg_usr_ip;
	private Date modify_dtm;
	private String modify_usr_id;
	private String modify_usr_ip;
	
	/* Getters and Setters */
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGen() {
		return gen;
	}
	
	public void setGen(String gen) {
		this.gen = gen;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getTelNo() {
		return telNo;
	}
	
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	
	public int getPt_count() {
		return pt_count;
	}
	
	public void setPt_count(int pt_count) {
		this.pt_count = pt_count;
	}
	
	public String getWork_start() {
		return work_start;
	}
	
	public void setWork_start(String work_start) {
		this.work_start = work_start;
	}
	
	public String getWork_end() {
		return work_end;
	}
	
	public void setWork_end(String work_end) {
		this.work_end = work_end;
	}
	
	public String getUse_yn() {
		return use_yn;
	}
	
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	
	public Date getReg_dtm() {
		return reg_dtm;
	}
	
	public void setReg_dtm(Date reg_dtm) {
		this.reg_dtm = reg_dtm;
	}
	
	public String getReg_usr_id() {
		return reg_usr_id;
	}
	
	public void setReg_usr_id(String reg_usr_id) {
		this.reg_usr_id = reg_usr_id;
	}
	
	public String getReg_usr_ip() {
		return reg_usr_ip;
	}
	
	public void setReg_usr_ip(String reg_usr_ip) {
		this.reg_usr_ip = reg_usr_ip;
	}
	
	public Date getModify_dtm() {
		return modify_dtm;
	}
	
	public void setModify_dtm(Date modify_dtm) {
		this.modify_dtm = modify_dtm;
	}
	
	public String getModify_usr_id() {
		return modify_usr_id;
	}
	
	public void setModify_usr_id(String modify_usr_id) {
		this.modify_usr_id = modify_usr_id;
	}
	
	public String getModify_usr_ip() {
		return modify_usr_ip;
	}
	
	public void setModify_usr_ip(String modify_usr_ip) {
		this.modify_usr_ip = modify_usr_ip;
	}
	
	public TrainerVO() {
		this.reg_dtm = getToday();
		this.modify_dtm = getToday();
		this.reg_usr_ip = getIp();
		this.modify_usr_ip = getIp();
		this.pw ="asdf";
	}
	
	private Date getToday() {
		Calendar cal = new GregorianCalendar();
		Date today = new Date(cal.getTimeInMillis());
		return today;
	}
	
	private String getIp() {
		InetAddress local;
		String ip = "127.0.0.0";
		try {
			
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return ip;
	}
}
