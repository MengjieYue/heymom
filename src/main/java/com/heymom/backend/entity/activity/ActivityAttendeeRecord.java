package com.heymom.backend.entity.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.user.Kid;
import com.heymom.backend.entity.user.User;

@Entity
@Table(name = "activity_attendee_record")
public class ActivityAttendeeRecord extends BaseEntity {
	private Activity activity;
	private String comments;
	private Long id;
	private Kid kid;
	private Integer score;
	private User user;

	@ManyToOne
	@JoinColumn(name = "activity_id")
	public Activity getActivity() {
		return activity;
	}

	@Column(name = "comments", length = 200)
	public String getComments() {
		return comments;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "kid_id")
	public Kid getKid() {
		return kid;
	}

	@Column(name = "score", precision = 2, scale = 0)
	public Integer getScore() {
		return score;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKid(Kid kid) {
		this.kid = kid;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
