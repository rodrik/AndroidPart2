package br.com.rodrigo.ijk;

import java.io.File;
import java.util.Date;

public class Selfie implements Comparable<Selfie> {
	
	public Selfie() {
		super();
	}
	
	public Selfie(File file) {
		super();
		this.file = file;
	}
	
	private File file;
	
	public File getFile() {
		return file;
	}
	public Date getTimestamp() {
		return new Date(this.file.lastModified());
	}
	public String getFilename() {
		return this.file.getName();
	}

	@Override
	public int compareTo(Selfie another) {
		return getTimestamp().compareTo(another.getTimestamp()) * -1;
	}
	
}
