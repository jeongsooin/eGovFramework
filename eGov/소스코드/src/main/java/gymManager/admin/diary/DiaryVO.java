package gymManager.admin.diary;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @Class Name : DiaryVO.java
 * @Description : Diary 테이블의 정보를 저장하기 위한 VO 클래스
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.01.
 * @version 1.0
 * @see
 */
public class DiaryVO {
	
	/* DIARY 테이블의 정보를 저장하기 위한 변수 선언 */
	private int cid;
	private int mid;
	private String tid;
	private String contents;
	private String use_yn;
	private Date reg_dtm;
	private String reg_usr_id;
	private String reg_usr_ip;
	private Date modify_dtm;
	private String modify_usr_id;
	private String modify_usr_ip;
	
	/* Getters and Setters */
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
	
	public DiaryVO() {
		this.reg_dtm = getToday();
		this.modify_dtm = getToday();
		this.reg_usr_ip = getIp();
		this.modify_usr_ip = getIp();
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
