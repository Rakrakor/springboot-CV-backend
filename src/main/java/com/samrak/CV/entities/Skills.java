package com.samrak.CV.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Skills {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="duration")
	private String duration;
	
	@Column(name="expertiselevel")
	private Long expertiseLevel;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "me_id")
	@JsonIgnore
	private Me me;
	
	
	public Skills() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getDuration() {
		return duration;
	}



	public void setDuration(String duration) {
		this.duration = duration;
	}



	public Long getExpertiseLevel() {
		return expertiseLevel;
	}



	public void setExpertiseLevel(Long expertiseLevel) {
		this.expertiseLevel = expertiseLevel;
	}



	public Me getMe() {
		return me;
	}



	public void setMe(Me me) {
		this.me = me;
	}


	

}
