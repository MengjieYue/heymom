package com.heymom.backend.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class SMSResponse {
	private String blacklist;
	private String desc;
	private String msgid;
	private String phones;
	private int result;

	public String getBlacklist() {
		return blacklist;
	}

	public String getDesc() {
		return desc;
	}

	public String getMsgid() {
		return msgid;
	}

	public String getPhones() {
		return phones;
	}

	public int getResult() {
		return result;
	}

	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
