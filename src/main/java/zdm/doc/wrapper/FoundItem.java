package zdm.doc.wrapper;

import java.io.Serializable;

/**
 * 搜索结果
 * @author user
 *
 */
public class FoundItem implements Serializable{
	
	private static final long serialVersionUID = -9003191735429151468L;
	
	private String title;
	private String detail;
	private String href;
	private String date;
	private String time;
	
	private static final String divider = "###";
	
	public FoundItem(String title, String detail, String href, String date, String time){
		this.time = time;
		this.date = date;
		this.title = title;
		this.detail = detail;
		this.href = href;
	}

	public FoundItem(String title, String detail, String href, String time){
		this.time = time;
		this.title = title;
		this.date="_";
		this.detail = detail;
		this.href = href;
	}

	public FoundItem(String line){
		String strs[] = line.split(divider);
		this.title = strs[0];
		this.detail = strs[1];
		this.date = strs[2];
		this.time = strs[3];
		this.href = strs[4];
	}
	
	@Override
	public String toString(){
		return this.title + divider + this.detail + divider + this.date + divider + this.time + divider + this.href;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
