package zdm.utils.weixin;

import java.io.Serializable;

public class PostParam implements Serializable{
	
	private static final long serialVersionUID = 7388465482894467526L;
	
	private String touser;
	private String totag;
	private String msgtype;
	private Integer agentid;
	private String safe = "0";
	private text text;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	
	public text getText() {
		return text;
	}
	public void setText(String text) {
		this.text = new text();
		this.text.content = text;
	}
	public String getTotag() {
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public Integer getAgentid() {
		return agentid;
	}
	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}
	
}

class text {
	
	public String content;
	
}
