package hawkDisplay;

public class HawkBean {
	String name;
	int mobile;
	String address,areas;
	float salary;
	String pic;
	public HawkBean(String name, int mobile, String address, String areas, float salary, String pic) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.areas = areas;
		this.salary = salary;
		this.pic = pic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	
}
