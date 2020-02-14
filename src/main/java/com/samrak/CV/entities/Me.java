package com.samrak.CV.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Me {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name="firstname")
	private String firstName;
	
	@Column(name="familyname")
	private String familyName;
	
	@Column(name="availability")
	private String availability;
	
	@Column(name="currentlocation")
	private String currentLocation;
	
	@Column(name="jobtitle")
	private String jobTitle;
	
	
	//@OneToMany(fetch=FetchType.LAZY, mappedBy="me")
	@OneToMany(mappedBy="me")
	private List<Skills>skills;
	
	@OneToMany(mappedBy="me")
	private List<Offer>offer;
	
	@OneToMany(mappedBy="me")
	private List<File>file;
	
	

	
	
	


	public Me() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getFamilyName() {
		return familyName;
	}


	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}


	public String getAvailability() {
		return availability;
	}


	public void setAvailability(String availability) {
		this.availability = availability;
	}


	public String getCurrentLocation() {
		return currentLocation;
	}


	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}


	public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	
	

	public List<Skills> getSkills() {
		return skills;
	}




	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}




	public List<Offer> getOffer() {
		return offer;
	}


	public void setOffer(List<Offer> offer) {
		this.offer = offer;
	}
	
	
	public List<File> getFile() {
		return file;
	}


	public void setFile(List<File> file) {
		this.file = file;
	}

		
	
}
