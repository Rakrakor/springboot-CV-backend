package com.samrak.CV.entities;

import java.time.LocalDate;
import java.util.List;

public class DTOOffers {

	
	private Long id;

	private String title;
	
	private String description;
	
	private String contractType;
	
	private LocalDate startDate;

	private int wages;
	
	private DTOUsers userDTO;
	
	private List<Response>response;

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

	public DTOUsers getUserPersonalOffers() {
		return userDTO;
	}

	public void setUserPersonalOffers(DTOUsers userDTO) {
		this.userDTO = userDTO;
	}

	public List<Response> getResponse() {
		return response;
	}

	public void setResponse(List<Response> response) {
		this.response = response;
	}
	
	
}
