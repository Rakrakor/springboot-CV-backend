package com.samrak.CV.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="contracttype")
	private String contractType;
	
	@Column(name="startdate")
	private LocalDate startDate;
	
	@Column(name="wages")
	private int wages;
	
	//https://stackoverflow.com/questions/11938253/whats-the-difference-between-joincolumn-and-mappedby-when-using-a-jpa-onetoma
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "me_id")
	@JsonIgnore
	private Me me;
	
	//@JsonManagedReference //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private Users userPersonalOffers;
	
	@OneToMany(mappedBy="answer")
	private List<Response>response;
	
	

	public Offer() {
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



	public String getContractType() {
		return contractType;
	}



	public void setContractType(String contractType) {
		this.contractType = contractType;
	}



	public LocalDate getStartDate() {
		return startDate;
	}



	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	public int getWages() {
		return wages;
	}



	public void setWages(int wages) {
		this.wages = wages;
	}



	public Me getMe() {
		return me;
	}



	public void setMe(Me me) {
		this.me = me;
	}



	public Users getuserPersonalOffers() {
		return userPersonalOffers;
	}



	public void setUsers(Users userPersonalOffers) {
		this.userPersonalOffers = userPersonalOffers;
	}



	public List<Response> getResponse() {
		return response;
	}



	public void setResponse(List<Response> response) {
		this.response = response;
	}

		
}
