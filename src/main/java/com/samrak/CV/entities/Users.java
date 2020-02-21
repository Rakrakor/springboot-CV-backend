package com.samrak.CV.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "auth_user")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(name="user_name")
	//@JsonProperty("data")   //https://stackoverflow.com/questions/19389723/can-not-deserialize-instance-of-java-lang-string-out-of-start-object-token
//	@NotEmpty(message="the email section is mandatory")
//	@Email(message="Email not valid")
	private String username;
	
	@Column(name="user_password")
//	@Length(min=3,message="Password must be at least 3 characters")
	private String userpassword;
	
	@Column(name = "user_email")
	@Email(message="Email not valid")
	private String email;


	@Column(name="user_phonenumber")
	//@Pattern(reexp="(^$|[0-9]{10}),message="Mobile phone number must be 10 digits
	private String phonenumber;
	
	@Column(name="user_company")
	private String usercompany;
	
	@Column(name = "user_status")
	private String status;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "auth_user_role", joinColumns = @JoinColumn(name = "auth_user_id"), inverseJoinColumns = @JoinColumn(name = "auth_role_id"))
	private Set<Role> roles;
	
	//mmappedBy must have the same name as the @JoinColumn private variable returned in Offer
	//@JsonBackReference //https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
	@JsonIgnore
	@OneToMany(mappedBy="userPersonalOffers")
	private List<Offer>offer;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	

	public String getUsercompany() {
		return usercompany;
	}

	public void setUsercompany(String usercompany) {
		this.usercompany = usercompany;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Offer> getOffer() {
		return offer;
	}

	public void setOffer(List<Offer> offer) {
		this.offer = offer;
	}
	
	
	
	
}
