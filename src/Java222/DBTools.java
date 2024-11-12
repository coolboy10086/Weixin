package Java222;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DBTools {

	private static Connection getCon() {
		Connection con = null;
		String user = "root";
		String pwd = "123456";
		String url = "jdbc:mysql://localhost:3306/db2022a";
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			// 1.��������
			Class.forName(driver);

			// 2.�������
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动程序加载失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("sql连接失败");

			e.printStackTrace();
		}

		return con;
	}

	public static Customer login(String openid) {
		Customer c = null;
		if (openid == null)return c;
		try {
			Connection con = getCon();
			Statement st = con.createStatement();
			String sql = "select * from customer where wxopenid='" + openid + "'or mpopenid ='" + openid + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				c = new Customer();
				c.setRealname(rs.getString("realname"));c.setAddress(rs.getString("address"));
				c.setAge(rs.getInt("age"));c.setGender(rs.getString("gender"));
				c.setMobile(rs.getString("mobile"));c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));c.setPassword(rs.getString("password"));
				c.setWxopenid(rs.getString("wxopenid"));c.setMpopenid(rs.getString("mpopenid"));
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("sql连接失败");
			c = null;
			e.printStackTrace();
		}
		return c;
	}

	public static Customer login(String username, String password) {
		Customer c = null;
		if (username == null || password == null)
			return c;
		try {
			Connection con = getCon();
			// 3.�������
			Statement st = con.createStatement();
			// 4.ִ�����õ����
			String sql = "select * from customer where username='" + username + "'and password ='" + password + "';";
			ResultSet rs = st.executeQuery(sql);
			// 5.������
			if (rs.next()) {
				c = new Customer();// �½�һ�����������汻��ѯ��ֵ

				c.setRealname(rs.getString("realname"));
				c.setAddress(rs.getString("address"));
				c.setAge(rs.getInt("age"));
				c.setGender(rs.getString("gender"));
				c.setMobile(rs.getString("mobile"));
				c.setId(rs.getInt("id"));
				c.setUsername(username);
				c.setPassword(password);
			}
			// 6.�ر����ݿ�
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("sql连接失败");
			c = null;
			e.printStackTrace();// �����Σ�û��Ҫ
		}
		return c;
	}

	public static boolean saveProduct(Product p) {
		boolean r = true;
		try {
			Connection con = getCon();
			String sql = "insert into product(name,description,price,amount,comment,picture) values(?,?,?,?,?,?)";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setString(1, p.getName());
			pstm.setString(2, p.getDescription());
			pstm.setDouble(3, p.getPrice());
			pstm.setInt(4, p.getAmount());
			pstm.setInt(5, p.getComment());
			pstm.setString(6, p.getPicture());
			r = pstm.executeUpdate() > 0;
			pstm.close();
			con.close();
		} catch (SQLException e) {
			r = false;
			e.printStackTrace();
		}
		return r;
	}

	public static ArrayList<Product> getProducts() {
		ArrayList<Product> c = new ArrayList<Product>();
		try {
			Connection con = getCon();
			Statement st = con.createStatement();
			String sql = "select * from product";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Product p = new Product();
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setAmount(rs.getInt("amount"));
				p.setComment(rs.getInt("comment"));
				p.setPicture(rs.getString("picture"));
				p.setId(rs.getInt("id"));
				c.add(p);
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("sql连接失败");
			e.printStackTrace();
		}
		return c;
	}

	public static int addCart(int customerid, int productid, int amount) {
		int r = -1;
		try {Connection con = getCon();
			String sql = "select * from cart where customerid=? and productid=? and status=0";
			PreparedStatement pstm = con.prepareStatement(sql); 
			pstm.setInt(1, customerid);
			pstm.setInt(2, productid);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {sql = "update cart set amount=amount+" + amount + " where id=" + rs.getInt("id");
				con.createStatement().executeUpdate(sql);} 
			else {sql = "insert into cart(productid,customerid,amount,addtime)values(?,?,?,?);";
				pstm.close();
				pstm = con.prepareStatement(sql);pstm.setInt(1, productid);pstm.setInt(2, customerid);
				pstm.setInt(3, amount);pstm.setString(4, new Date().toLocaleString());pstm.executeUpdate();
			}
			sql = "select sum(amount) as cnt from cart where status = 0 and customerid =" + customerid;
			rs.close();
			rs = con.createStatement().executeQuery(sql);
			if (rs.next())r = rs.getInt("cnt");rs.close();pstm.close();con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	// ��ȡָ���û�id�Ĺ��ﳵ�б�
	public static ArrayList<Cart> getCart(int customerid) {
		ArrayList<Cart> list = new ArrayList<Cart>();
		String sql = "select status,cart.id,name,cart.amount,picture,price from cart left join product on cart.productid=product.id "
				+ "where customerid=" + customerid;
		Connection con = getCon();
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while (rs.next()) {
				Cart c = new Cart();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setAmount(rs.getInt("amount"));
				c.setPicture(rs.getString("picture"));
				c.setPrice(rs.getDouble("price"));
				c.setStatus(rs.getInt("status"));

				list.add(c);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static int modityCart(int cartid, int value, int customerid) {
		int r = 0;
		String sql = "update cart set amount=amount+" + value + " where id=" + cartid + " and customerid=" + customerid;
		Connection con = getCon();
		try {
			con.createStatement().executeUpdate(sql);
			sql = "select sum(amount) as cnt from cart where status=0 and customerid=" + customerid;
			ResultSet rs = con.createStatement().executeQuery(sql);
			if (rs.next())
				r = rs.getInt("cnt");
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static int pay(String[] cartids, Integer id) {
		int r = 0;

		Connection con = getCon();
		try {
			for (String cartid : cartids) {
				String sql = "update cart set status=1 where id=" + cartid + " and customerid=" + id;
				con.createStatement().executeUpdate(sql);

				sql = "update product,cart set product.amount=product.amount-cart.amount where product.id=cart.productid "
						+ "and cart.id=" + cartid + " and customerid=" + id + ";";
				con.createStatement().executeUpdate(sql);
				r++;
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static int del(int id) {
		int r = 0;
		Connection con = getCon();
		try {

			String sql = "DELETE FROM `db2022a`.`cart` WHERE id =" + id + ";";

			con.createStatement().executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	public static Customer bindU(String username, String password, String realname, String address, String mobile,
			String gender, int age, String mpopenid, String wxopenid) {
		Customer c = null;
		Connection con = getCon();
		try {
			String sql = "select id from customer where (username='' and password='') or mobile='' or wxopenid='' or mpopenid=''";
			if (!mpopenid.equals(""))
				sql = sql + "or mpopenid='" + mpopenid + "'";
			if (!wxopenid.equals(""))
				sql = sql + "or wxopenid='" + wxopenid + "'";
			ResultSet rs = con.createStatement().executeQuery(sql);
			int id = -1;
			if (rs.next()) {
				id = rs.getInt("id");
				sql = "update customer set  where id = " + id;
				sql = "update customer set ";
				if (!username.equals(""))
					sql = sql + ",username='" + username + "'";
				if (!password.equals(""))
					sql = sql + ",password='" + password + "'";
				if (!realname.equals(""))
					sql = sql + ",realname='" + realname + "'";
				if (!address.equals(""))
					sql = sql + ",address='" + address + "'";
				if (!mobile.equals(""))
					sql = sql + ",mobile='" + mobile + "'";
				if (!gender.equals(""))
					sql = sql + ",gender='" + gender + "'";
				if (!mpopenid.equals(""))
					sql = sql + ",mpopenid='" + mpopenid + "'";
				if (!wxopenid.equals(""))
					sql = sql + ",wxopenid='" + wxopenid + "'";
				if (age != 0)
					sql = sql + ",age='" + age + "'";
				sql = sql + " where id = " + id;
				sql = sql.replace("set ,", "set ");
				con.createStatement().execute(sql);

			} else {
				sql = "insert into customer(username,password,realname,address,mobile,gender,age,mpopenid,wxopenid) value(?,?,?,?,?,?,?,?,?)";
				PreparedStatement pstm = con.prepareStatement(sql);
				pstm.setString(1, username);
				pstm.setString(2, password);
				pstm.setString(3, realname);
				pstm.setString(4, address);
				pstm.setString(5, mobile);
				pstm.setString(6, gender);
				pstm.setInt(7, age);
				pstm.setString(8, mpopenid);
				pstm.setString(9, wxopenid);
				pstm.execute();
				pstm.close();
				rs.close();
				rs = con.createStatement().executeQuery("select max(id) as id from customer;");
				if (rs.next()) {
					id = rs.getInt("id");
				}
			}
			if (rs.next()) {
				c = new Customer();
				c.setAddress(rs.getString("address"));
				c.setAge(rs.getInt("age"));
				c.setId(id);
				c.setGender(rs.getString("gender"));
				c.setMobile(rs.getString("mobile"));
				c.setMpopenid(rs.getString("mpopenid"));
				c.setWxopenid(rs.getString("wxopenid"));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}
}
