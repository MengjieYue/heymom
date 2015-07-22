package com.heymom.backend.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
public class SMSRequest {
	private String account;
	private String content;
	private String msgid;
	private String password;
	private String phones;
	private String sendtime;
	private String sign;
	private String subcode;

	public String getAccount() {
		return account;
	}

	public String getContent() {
		return content;
	}

	public String getMsgid() {
		return msgid;
	}

	public String getPassword() {
		return password;
	}

	public String getPhones() {
		return phones;
	}

	public String getSendtime() {
		return sendtime;
	}

	public String getSign() {
		return sign;
	}

	public String getSubcode() {
		return subcode;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}

}
