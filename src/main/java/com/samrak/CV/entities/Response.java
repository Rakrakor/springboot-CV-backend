package com.samrak.CV.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Response {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="response")
	private String response;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
	@JsonIgnore
	private Offer offer;

	
	
	//constructor
	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getResponse() {
		return response;
	}



	public void setResponse(String response) {
		this.response = response;
	}



	public Offer getOffer() {
		return offer;
	}



	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	
	
	
	

}
