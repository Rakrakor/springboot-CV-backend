package com.samrak.CV;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column
	protected String identity;
	
	@Column
	protected String postalAddress;
	
	@Column
	protected String email;
	
	@Column
	protected String phoneNumber;
	
	@Column
	protected String location;
	
	
	
}

