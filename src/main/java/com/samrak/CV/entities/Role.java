package com.samrak.CV.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "role")
	public class Role {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "role_id")
		private Long id;

		@Column(name = "role_name")
		private String rolename;

		@Column(name = "role_desc")
		private String desc;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRolename() {
			return rolename;
		}

		public void setRolename(String rolename) {
			this.rolename = rolename;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		
		
}
