package Java222;

import java.io.Serializable;

public class Product implements Serializable {
	private String name;
	private String description;
	private double price;
	private int amount;
	private int comment;
	private String picture;
	private int id;
	public boolean save(){
		return DBTools.saveProduct(this);
	}
	public static void main(String[] args) {
		Product p = new Product("��ΪMate60","��Ϊңң���ȣ���������ǿ����ֵ��ӵ��",9000,100,5,"",0);
		p.save();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product(String name, String description, double price, int amount, int comment, String picture, int id) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.amount = amount;
		this.comment = comment;
		this.picture = picture;
		this.id = id;
	}
	public Product() {
		super();
	}
	
	
	
}
