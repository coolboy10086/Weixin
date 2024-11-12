package Java222;

public class Customer {//ʵ����
	private Integer id;
	private String username;
	private String password;
	private String realname;
	private int age;
	private String address;
	private String gender;
	private String mobile;
	private String wxopenid;
	private String mpopenid;
	public Customer() {
		super();
	}
	
	public Customer(Integer id, String username, String password, String realname, int age, String address,
			String gender, String mobile) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.realname = realname;
		this.age = age;
		this.address = address;
		this.gender = gender;
		this.mobile = mobile;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}

	public String getMpopenid() {
		return mpopenid;
	}

	public void setMpopenid(String mpopenid) {
		this.mpopenid = mpopenid;
	}

	public Customer(String wxopenid, String mpopenid) {
		super();
		this.wxopenid = wxopenid;
		this.mpopenid = mpopenid;
	}
	
	
	
	
}
