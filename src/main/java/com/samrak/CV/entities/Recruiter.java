//package com.samrak.CV.entities;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//
//@Entity
//public class Recruiter {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	
//	@Column(name="username")
//	private String userName;
//	
//	@Column(name="companyname")
//	private String CompanyName;
//	
//	@Column(name="email")
//	private String email;
//	
//	@Column(name="phonenumber")
//	private String phoneNumber;
//	
//	@Column(name="offer")
//	@OneToMany(fetch = FetchType.EAGER, mappedBy="recruiter")
//	private List<Offer> offerList;
//
//	public Recruiter() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getCompanyName() {
//		return CompanyName;
//	}
//
//	public void setCompanyName(String companyName) {
//		CompanyName = companyName;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public List<Offer> getOfferList() {
//		return offerList;
//	}
//
//	public void setOfferList(List<Offer> offerList) {
//		this.offerList = offerList;
//	}
//
//
//	
//	
//	
//}
