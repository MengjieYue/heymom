package com.heymom.backend.entity.user;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;

@Entity
@Table(name = "kid")
public class Kid extends BaseEntity {
	private List<ActivityAttendeeRecord> attendRecords;
	private Date birthday;
	private Integer gender;
	private Long id;
	private String image;
	private String name;
	private String nickName;
	private User parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kid")
	@Where(clause = "status>=0")
	public List<ActivityAttendeeRecord> getAttendRecords() {
		return attendRecords;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}

	@Column(name = "gender", precision = 1, scale = 0)
	public Integer getGender() {
		return gender;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@Column(name = "image", length = 150)
	public String getImage() {
		return image;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	@Column(name = "nick_name", length = 50)
	public String getNickName() {
		return nickName;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public User getParent() {
		return parent;
	}

	public void setAttendRecords(List<ActivityAttendeeRecord> attendRecords) {
		this.attendRecords = attendRecords;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

}
