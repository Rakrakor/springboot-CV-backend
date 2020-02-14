package com.samrak.CV.entities;

public class UploadFileResponse {

	
	private String fileName;
	private String uri;
	private String content;
	private Long size;
	
	public UploadFileResponse(String fileName, String uri, String content, Long size) {
		super();
		this.fileName = fileName;
		this.uri = uri;
		this.content = content;
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	
	
}
