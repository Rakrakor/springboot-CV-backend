package com.samrak.CV.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

	@Entity
	@Table(name = "me_attached_files")
	public class File {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name="file_name")
	    private String fileName;
	    
	    @Column(name="file_type")
	    private String fileType;

		@Column(name="file_data")
	    @Lob
	    private byte[] data;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "me_id")
		@JsonIgnore
		private Me me;
	    
	    
	    public File() {

	    }

	    public File(String fileName, String fileType, byte[] data) {
	        this.fileName = fileName;
	        this.fileType = fileType;
	        this.data = data;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileType() {
			return fileType;
		}

		public void setFileType(String fileType) {
			this.fileType = fileType;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}
		
		public Me getMe() {
			return me;
		}

		public void setMe(Me me) {
			this.me = me;
		}

	
	    
}
