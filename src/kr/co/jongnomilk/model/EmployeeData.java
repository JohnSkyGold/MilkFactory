package kr.co.jongnomilk.model;

public class EmployeeData {
	String employeeID;
	int password;
	int age;
	String sex;
	String position;
	String departure;
	String name;
	String authority;
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "EmployeeData [employeeID=" + employeeID + ", password=" + password + ", age=" + age + ", sex=" + sex
				+ ", position=" + position + ", departure=" + departure + ", name=" + name + ", authority=" + authority
				+ "]";
	}	
}
