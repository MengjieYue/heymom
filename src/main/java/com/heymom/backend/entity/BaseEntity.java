package com.heymom.backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity {
	protected Date createTime;
	protected Integer status = 0;
	protected Date updateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT 0")
	public Date getCreateTime() {
		return createTime;
	}

	@Column(name = "status", nullable = false, precision = 2, scale = 0, columnDefinition = "INT DEFAULT 0")
	public Integer getStatus() {
		return status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
