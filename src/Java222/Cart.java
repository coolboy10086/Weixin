package Java222;

public class Cart extends Product  {

	private int  id;
	private int customerid;
	private int productid;
	private int amount;
	private int status;
	private String addtime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	
	public Cart(int id, int customerid, int productid, int amount, int status, String addtime) {
		super();
		this.id = id;
		this.customerid = customerid;
		this.productid = productid;
		this.amount = amount;
		this.status = status;
		this.addtime = addtime;
	}
	
	public Cart() {
		super();
	}

}
