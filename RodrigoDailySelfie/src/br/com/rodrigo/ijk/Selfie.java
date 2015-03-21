package br.com.rodrigo.ijk;

import java.util.Date;

public class Selfie {
	
	public Selfie() {
		super();
	}
	
	public Selfie(Long id, Date timestamp, String filename) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.filename = filename;
	}
	private Long id;
	private Date timestamp;
	private String filename;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
