package org.lazyluke.spring.jdbc.examples;

public class Person {
	private Integer id;
	private String name;
	private String phoneNumber;  // note that this is called phone_number in the query plsql
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", phoneNumber="
				+ phoneNumber + "]";
	}
	
}