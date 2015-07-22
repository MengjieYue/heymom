package com.heymom.backend.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;

@Entity
@Table(name = "mobile_verification_record")
public class MobileVerificationRecord extends BaseEntity {
	private String code;
	private Long id;
	private String mobile;

	public MobileVerificationRecord() {
		super();
	}

	public MobileVerificationRecord(String code, String mobile) {
		super();
		this.code = code;
		this.mobile = mobile;
	}

	@Column(name = "code", length = 6, nullable = false)
	public String getCode() {
		return code;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@Column(name = "mobile", length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
