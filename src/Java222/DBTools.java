package Java222;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * DBTools类提供了数据库连接和操作的方法
 */
public class DBTools {

	/**
	 * 获取数据库连接
	 * 
	 * @return 数据库连接对象
	 */
	private static Connection getCon() {
		Connection con = null;
		String user = "root";
		String pwd = "123456";
		String url = "jdbc:mysql://localhost:3306/db2022a";
		String driver = "com.mysql.cj.jdbc.Driver";
		try {
			// 1.加载数据库驱动
			Class.forName(driver);

			// 2.建立数据库连接
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

	/**
	 * 用户登录方法，通过openid查询用户信息
	 * 
	 * @param openid 用户的微信或小程序openid
	 * @return 如果查询成功则返回Customer对象，否则返回null
	 */
	public static Customer login(String openid) {
		Customer c = null;
		if (openid == null)
			return c;
		try {
			Connection con = getCon();
			Statement st = con.createStatement();
			// 根据openid查询customer表中的用户信息
			String sql = "select * from customer where wxopenid='" + openid + "'or mpopenid ='" + openid + "';";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				c = new Customer();
				// 从结果集中获取用户信息并设置到Customer对象中
				c.setRealname(rs.getString("realname"));
				c.setAddress(rs.getString("address"));
				c.setAge(rs.getInt("age"));
				c.setGender(rs.getString("gender"));
				c.setMobile(rs.getString("mobile"));
				c.setId(rs.getInt("id"));
				c.setUsername(rs.getString("username"));
				c.setPassword(rs.getString("password"));
				c.setWxopenid(rs.getString("wxopenid"));
				c.setMpopenid(rs.getString("mpopenid"));
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

	/**
	 * 登录函数
	 * 根据用户名和密码尝试从数据库中查找对应的客户信息
	 * 
	 * @param username 用户名，用于查找客户
	 * @param password 密码，用于验证客户
	 * @return 如果找到匹配的客户信息，则返回该客户对象；否则返回null
	 */
	public static Customer login(String username, String password) {
		Customer c = null;
		if (username == null || password == null)
			return c;
		try {
			Connection con = getCon();
			// 3.创建Statement
			Statement st = con.createStatement();
			// 4.执行查询语句
			String sql = "select * from customer where username='" + username + "'and password ='" + password + "';";
			ResultSet rs = st.executeQuery(sql);
			// 5.处理查询结果
			if (rs.next()) {
				c = new Customer();// 创建一个新的客户对象来存储查询到的值

				c.setRealname(rs.getString("realname"));
				c.setAddress(rs.getString("address"));
				c.setAge(rs.getInt("age"));
				c.setGender(rs.getString("gender"));
				c.setMobile(rs.getString("mobile"));
				c.setId(rs.getInt("id"));
				c.setUsername(username);
				c.setPassword(password);
			}
			// 6.关闭数据库连接
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("sql连接失败");
			c = null;
			e.printStackTrace();// 打印异常信息，不需要进一步处理
		}
		return c;
	}

	/**
	 * 保存产品信息到数据库
	 * 
	 * @param p 产品对象，包含要保存的产品信息
	 * @return 如果产品信息成功保存到数据库，则返回true；否则返回false
	 */
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

	/**
	 * 获取所有产品信息
	 * 
	 * @return 返回一个包含所有产品的ArrayList，如果数据库连接失败或没有产品，则返回空列表
	 */
	public static ArrayList<Product> getProducts() {
		ArrayList<Product> c = new ArrayList<Product>();
		try {
			// 获取数据库连接
			Connection con = getCon();
			// 创建Statement以执行SQL查询
			Statement st = con.createStatement();
			// 定义SQL查询语句
			String sql = "select * from product";
			// 执行查询并获取结果集
			ResultSet rs = st.executeQuery(sql);
			// 遍历结果集，为每个产品创建Product对象，并添加到列表中
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
			// 关闭资源
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("sql连接失败");
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * 添加商品到购物车
	 * 
	 * @param customerid 购物车所属的客户ID
	 * @param productid  要添加的产品ID
	 * @param amount     要添加的产品数量
	 * @return 返回更新后的购物车商品总数，如果操作失败则返回-1
	 */
	public static int addCart(int customerid, int productid, int amount) {
		int r = -1;
		try {
			// 获取数据库连接
			Connection con = getCon();
			// 准备SQL语句以检查购物车中是否已有相同产品
			String sql = "select * from cart where customerid=? and productid=? and status=0";
			PreparedStatement pstm = con.prepareStatement(sql);
			pstm.setInt(1, customerid);
			pstm.setInt(2, productid);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				// 如果购物车中有相同产品，更新数量
				sql = "update cart set amount=amount+" + amount + " where id=" + rs.getInt("id");
				con.createStatement().executeUpdate(sql);
			} else {
				// 如果购物车中没有相同产品，插入新记录
				sql = "insert into cart(productid,customerid,amount,addtime)values(?,?,?,?);";
				pstm.close();
				pstm = con.prepareStatement(sql);
				pstm.setInt(1, productid);
				pstm.setInt(2, customerid);
				pstm.setInt(3, amount);
				pstm.setString(4, new Date().toLocaleString());
				pstm.executeUpdate();
			}
			// 查询并返回购物车中的商品总数
			sql = "select sum(amount) as cnt from cart where status = 0 and customerid =" + customerid;
			rs.close();
			rs = con.createStatement().executeQuery(sql);
			if (rs.next()) {
				r = rs.getInt("cnt");
			}
			rs.close();
			pstm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}// 获取指定用户ID的购物车列表

	public static ArrayList<Cart> getCart(int customerid) {
		ArrayList<Cart> list = new ArrayList<Cart>();
		// SQL查询语句，从购物车表左连接产品表，获取指定用户ID的购物车信息
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

	// 修改购物车中指定商品的数量，并返回更新后购物车中商品的总数量
	public static int modityCart(int cartid, int value, int customerid) {
		int r = 0;
		// SQL更新语句，增加或减少指定购物车ID和用户ID的商品数量
		String sql = "update cart set amount=amount+" + value + " where id=" + cartid + " and customerid=" + customerid;
		Connection con = getCon();
		try {
			con.createStatement().executeUpdate(sql);
			// SQL查询语句，获取更新后用户购物车中状态为未结算的商品总数量
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

	/**
	 * 处理支付逻辑，更新购物车和商品库存
	 * 
	 * @param cartids 一个包含购物车ID的数组，标识待支付的购物车项
	 * @param id      用户ID，用于确认购物车属于哪个用户
	 * @return 成功更新的购物车项数量
	 */
	public static int pay(String[] cartids, Integer id) {
		int r = 0;

		Connection con = getCon();
		try {
			// 遍历每个购物车ID，更新其状态和对应商品的库存
			for (String cartid : cartids) {
				// 更新购物车项的状态为已支付
				String sql = "update cart set status=1 where id=" + cartid + " and customerid=" + id;
				con.createStatement().executeUpdate(sql);

				// 减少商品库存，数量减少与购物车中该商品的数量相等
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

	/**
	 * 从购物车中删除指定的项
	 * 
	 * @param id 要从购物车中删除的项的ID
	 * @return 操作成功时返回0，表示删除成功
	 */
	public static int del(int id) {
		int r = 0;
		Connection con = getCon();
		try {
			// 执行删除操作，从数据库中移除指定ID的购物车项
			String sql = "DELETE FROM `db2022a`.`cart` WHERE id =" + id + ";";

			con.createStatement().executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 绑定用户信息到Customer实体
	 * 当用户名和密码为空，或手机号、微信openid、小程序openid不为空时，查询数据库中是否存在相应记录
	 * 如果存在，则更新记录；如果不存在，则插入新记录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param realname 真实姓名
	 * @param address  地址
	 * @param mobile   手机号
	 * @param gender   性别
	 * @param age      年龄
	 * @param mpopenid 小程序openid
	 * @param wxopenid 微信openid
	 * @return 返回Customer对象，包含用户信息
	 */
	public static Customer bindU(String username, String password, String realname, String address, String mobile,
			String gender, int age, String mpopenid, String wxopenid) {
		Customer c = null;
		Connection con = getCon();
		try {
			// 构建初始查询SQL语句
			String sql = "select id from customer where (username='' and password='') or mobile='' or wxopenid='' or mpopenid=''";
			// 根据mpopenid和wxopenid动态更新SQL语句
			if (!mpopenid.equals(""))
				sql = sql + "or mpopenid='" + mpopenid + "'";
			if (!wxopenid.equals(""))
				sql = sql + "or wxopenid='" + wxopenid + "'";
			ResultSet rs = con.createStatement().executeQuery(sql);
			int id = -1;
			// 如果查询到记录，则更新记录
			if (rs.next()) {
				id = rs.getInt("id");
				sql = "update customer set  where id = " + id;
				sql = "update customer set ";
				// 动态构建更新SQL语句
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
				// 如果未查询到记录，则插入新记录
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
			// 根据id查询最新记录并构建Customer对象
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