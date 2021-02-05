package gymManager.admin.shcedule;

/**
 * @Class Name : EventVO.java
 * @Description : fullCalendar event(일정) 세팅에 필요한 정보를 담은 Event Object를 만들어 Event Source를 제공하기 위한 VO 클래스 
 * @Modification Information
 *
 * @author 정수인
 * @since 2019.11.14.
 * @version 1.0
 * @see
 */
public class EventVO {

	/* fullCalendar Event Object의 정보 */
	private int id;
	private String title;
	private String start;
	private String end;
	private String backgroundColor;
	private String borderColor;
	private String textColor;
	
	/* Getters 및 Setters */
	
	public String getTitle() {
		return title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

}
