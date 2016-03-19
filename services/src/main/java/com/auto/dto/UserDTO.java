package com.auto.dto;

public class UserDTO {
	private long uid;
	private String fName;
	private String lName;
	private String email;
	private long contact;
	private int uType;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	public int getuType() {
		return uType;
	}

	public void setuType(int uType) {
		this.uType = uType;
	}

	@Override
	public String toString() {
		return "UserDTO [uid=" + uid + ", fName=" + fName + ", lName=" + lName
				+ ", email=" + email + ", contact=" + contact + ", uType="
				+ uType + "]";
	}
}
