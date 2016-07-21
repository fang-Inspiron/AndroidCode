package com.utils;

import java.util.ArrayList;
import java.util.List;

import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.bean.User;


public class DBUtils {
	
	public static boolean insert(User user) throws Exception {
		JdbcUtils jUtils = new JdbcUtils();
		jUtils.getConnection();
		
		String sql = "insert into userInfo values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser());
		params.add(user.getPassword());
		params.add(user.getPermission());
		
		return jUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean insert(Customer customer) throws Exception {
		JdbcUtils jUtils = new JdbcUtils();
		jUtils.getConnection();
		
		String sql = "insert into customerInfo values(?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(customer.getName());
		params.add(customer.getSex());
		params.add(customer.getIDnumber());
		params.add(customer.getPhone());
		
		return jUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean insert(Flight flight) throws Exception {
		JdbcUtils jUtils = new JdbcUtils();
		jUtils.getConnection();
		
		String sql = "insert into flightInfo values(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(flight.getFlightNum());
		params.add(flight.getLeaveCity());
		params.add(flight.getArriveCity());
		params.add(flight.getLeaveDate());
		params.add(flight.getPrice());
		
		return jUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean insert(Order order) throws Exception {
		JdbcUtils jUtils = new JdbcUtils();
		jUtils.getConnection();
		
		String sql = "insert into orderInfo values(?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(order.getOrderNum());
		params.add(order.getIDnumber());
		params.add(order.getFlightNum());
		params.add(order.getTicketNum());
		params.add(order.getPurchaseDate());
		params.add(order.getConductor());
		
		return jUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean deleteUser(String user) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "delete from userInfo where user=?";
		List<Object> params = new ArrayList<Object>();
		params.add(user);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean deleteCustomer(String IDnumber) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "delete from customerInfo where IDnumber=?";
		List<Object> params = new ArrayList<Object>();
		params.add(IDnumber);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean deleteFlight(String flightNum) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "delete from flightInfo where flightNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(flightNum);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean deleteOrder(String orderNum) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "delete from orderInfo where orderNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(orderNum);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean modify(String userName, User user) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "update userInfo set user=?, password=?, permission=? where userName=?";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUser());//add次序要和sql语句的“？”的参数顺序保持一致
		params.add(user.getPassword());
		params.add(user.getPermission());
		params.add(userName);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean modify(String IDnumber, Customer customer) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "update customerInfo set name=?, sex=?, IDnumber=?,  phone=? where IDnumber=?";
		List<Object> params = new ArrayList<Object>();
		params.add(customer.getName());//add次序要和sql语句的“？”的参数顺序保持一致
		params.add(customer.getSex());
		params.add(customer.getIDnumber());
		params.add(customer.getPhone());
		params.add(IDnumber);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean modify(String flightNum, Flight flight) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "update flightInfo set flightNum=?, leaveCity=?, arriveCity=?,  leaveDate=?, price=? where flightNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(flight.getFlightNum());//add次序要和sql语句的“？”的参数顺序保持一致
		params.add(flight.getLeaveCity());
		params.add(flight.getArriveCity());
		params.add(flight.getLeaveDate());
		params.add(flight.getPrice());
		params.add(flightNum);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static boolean modify(String orderNum, Order order) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "update orderInfo set orderNum=?, IDnumber=?, flightNum=?,  ticketNum=?, purchaseDate=?, conductor=? where orderNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(order.getOrderNum());//add次序要和sql语句的“？”的参数顺序保持一致
		params.add(order.getIDnumber());
		params.add(order.getFlightNum());
		params.add(order.getTicketNum());
		params.add(order.getPurchaseDate());
		params.add(order.getConductor());
		params.add(orderNum);
		return jdbcUtils.updateBypreparedStatement(sql, params);
	}
	
	public static User findOneUser(String user) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from userInfo where user=?";
		List<Object> params = new ArrayList<Object>();
		params.add(user);
		return jdbcUtils.findSimpleRefResult(sql, params, User.class);
	}
	
	public static Customer findOneCustomer(String IDnumber) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from customerInfo where IDnumber=?";
		List<Object> params = new ArrayList<Object>();
		params.add(IDnumber);
		return jdbcUtils.findSimpleRefResult(sql, params, Customer.class);
	}
	
	public static Flight findOneFlight(String flightNum) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from flightInfo where flightNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(flightNum);
		return jdbcUtils.findSimpleRefResult(sql, params, Flight.class);
	}
	
	public static Order findOneOrder(String orderNum) throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from orderInfo where orderNum=?";
		List<Object> params = new ArrayList<Object>();
		params.add(orderNum);
		return jdbcUtils.findSimpleRefResult(sql, params, Order.class);
	}
	
	public static List<User> findAllUser() throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from userInfo";
		return jdbcUtils.findMoreRefResult(sql, null, User.class);
	}
	
	public static List<Customer> findAllCustomer() throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from customerInfo";
		return jdbcUtils.findMoreRefResult(sql, null, Customer.class);
	}
	
	public static List<Flight> findAllFlight() throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from flightInfo";
		return jdbcUtils.findMoreRefResult(sql, null, Flight.class);
	}
	
	public static List<Order> findAllOrder() throws Exception {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		String sql = "select * from orderInfo";
		return jdbcUtils.findMoreRefResult(sql, null, Order.class);
	}
	
}
