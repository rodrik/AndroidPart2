package br.com.rodrigo.ijk;

import java.io.File;
import java.util.Date;

public class Selfie implements Comparable<Selfie> {
	
	public Selfie() {
		super();
	}
	
	public Selfie(File file, Date timestamp, String filename) {
		super();
		this.file = file;
		this.timestamp = timestamp;
		this.filename = filename;
	}
	
	private File file;
	private Date timestamp;
	private String filename;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
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

	@Override
	public int compareTo(Selfie another) {
		return timestamp.compareTo(another.getTimestamp()) * -1;
	}
	
}
