package com.function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bean.Customer;
import com.bean.Flight;
import com.bean.Order;
import com.utils.DBUtils;

public class TicketManager implements ActionListener {

	static JPanel jp_flightMsg, jp_cancel, jp_purchaseTicket;
	JButton jb_ensureFlight, cancel, jb_ensureInfo;
	String selectedFlight = null;
	JLabel jl_flightMsg, jl_customerMsg, jl_flightNum, jl_leaveCity,
			jl_arriveCity, jl_leaveDate, jl_price, jl_name, jl_sex,
			jl_IDnumber, jl_phone, jl_ticketNum, jl_orderNum;
	JTextField jtf_flightNum, jtf_leaveCity, jtf_arriveCity, jtf_leaveDate,
			jtf_price, jtf_name, jtf_sex, jtf_IDnumber, jtf_phone,
			jtf_ticketNum, jtf_orderNum;
	public void remove() {
		if (com.function.FlightManager.jp_function != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_function);
		if (com.function.FlightManager.jp_cancel != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_cancel);
		if (com.function.FlightManager.jp_insert != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_insert);
		if (com.function.FlightManager.jp_delete != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_delete);
		if (com.function.FlightManager.jp_modify != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_modify);
		if (com.function.FlightManager.jp_find != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_find);
		if (com.function.FlightManager.jp_one != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_one);
		if (com.function.FlightManager.jp_all != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.FlightManager.jp_all);
		if (com.function.OrderManager.jp_findOne!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_findOne);
		if (com.function.OrderManager.jp_findAll!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_findAll);
		if (com.function.OrderManager.jp_function!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_function);
		if (com.function.OrderManager.jp_cancel!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_cancel);
		if (com.function.OrderManager.jp_delete!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_delete);
		if (com.function.OrderManager.jp_modify!= null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.OrderManager.jp_modify);
	}

	public TicketManager() {
		remove();

		jp_flightMsg = new JPanel();
		jp_flightMsg.setBounds(0, 46, 584, 365);
		com.airplane.Function.frame.getContentPane().add(jp_flightMsg);
		jp_flightMsg.setLayout(null);

		List list = null;
		try {
			list = DBUtils.findAllFlight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] columnNames = { "航班编号", "出发城市", "到达城市", "出发日期", "机票价格" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Flight flight = (Flight) list.get(i);
			tableValues[i][0] = flight.getFlightNum();
			tableValues[i][1] = flight.getLeaveCity();
			tableValues[i][2] = flight.getArriveCity();
			tableValues[i][3] = flight.getLeaveDate();
			tableValues[i][4] = "" + flight.getPrice();
		}

		final JTable table = new JTable(tableValues, columnNames);// 创建指定列名和数据的表格
		table.setBounds(30, 30, 524, 220);
		JScrollPane scrollPane = new JScrollPane(table);// 创建显示表格的滚动面板
		scrollPane.setBounds(30, 30, 524, 220);

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					selectedFlight = (String) table.getValueAt(
							table.getSelectedRow(), 0);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		jb_ensureFlight = new JButton("确认航班");
		cancel = new JButton("取消");
		jb_ensureFlight.setBounds(180, 290, 90, 25);
		cancel.setBounds(300, 290, 90, 25);
		cancel.addActionListener(this);
		jb_ensureFlight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFlight == null) {
					JOptionPane.showMessageDialog(jp_flightMsg,
							"请选择所要购买的航班信息！", "提示框", JOptionPane.CANCEL_OPTION);
				} else {
					purchaseTicket();
				}
			}
		});

		jp_flightMsg.add(scrollPane);
		jp_flightMsg.add(jb_ensureFlight);
		jp_flightMsg.add(cancel);
		jp_flightMsg.revalidate();
		jp_flightMsg.repaint();
	}

	public void purchaseTicket() {
		if (jp_flightMsg != null)
			com.airplane.Function.frame.getContentPane().remove(jp_flightMsg);

		jp_purchaseTicket = new JPanel();
		jp_purchaseTicket.setBounds(0, 46, 584, 365);
		com.airplane.Function.frame.getContentPane().add(jp_purchaseTicket);
		jp_purchaseTicket.setLayout(null);

		Flight flight = null;
		try {
			flight = DBUtils.findOneFlight(selectedFlight);

		} catch (Exception e) {
			e.printStackTrace();
		}

		jl_flightMsg = new JLabel("***^_^***  机票信息：");
		jl_flightNum = new JLabel("航班编号：");
		jl_leaveDate = new JLabel("出发日期：");
		jl_leaveCity = new JLabel("出发城市：");
		jl_arriveCity = new JLabel("到达城市：");
		jl_price = new JLabel("机票价格：");
		jl_customerMsg = new JLabel("***^_^***  客户信息：");
		jl_name = new JLabel("客户姓名：");
		jl_sex = new JLabel("性        别：");
		jl_IDnumber = new JLabel("身份证号：");
		jl_phone = new JLabel("联系电话：");
		jl_ticketNum = new JLabel("购票数目：");
		jl_orderNum = new JLabel("订  单  号：");
		jl_flightMsg.setBounds(25, 15, 130, 15);
		jl_flightNum.setBounds(60, 50, 65, 15);
		jl_leaveDate.setBounds(340, 50, 65, 15);
		jl_leaveCity.setBounds(60, 80, 65, 15);
		jl_arriveCity.setBounds(340, 80, 65, 15);
		jl_price.setBounds(60, 110, 65, 15);
		jl_customerMsg.setBounds(25, 150, 130, 15);
		jl_name.setBounds(60, 180, 65, 15);
		jl_sex.setBounds(340, 180, 65, 15);
		jl_IDnumber.setBounds(60, 210, 65, 15);
		jl_phone.setBounds(340, 210, 65, 15);
		jl_ticketNum.setBounds(60, 240, 65, 15);
		jl_orderNum.setBounds(340, 240, 65, 15);

		jtf_flightNum = new JTextField();
		jtf_leaveDate = new JTextField();
		jtf_leaveCity = new JTextField();
		jtf_arriveCity = new JTextField();
		jtf_price = new JTextField();
		jtf_name = new JTextField();
		jtf_sex = new JTextField();
		jtf_IDnumber = new JTextField();
		jtf_phone = new JTextField();
		jtf_ticketNum = new JTextField();
		jtf_orderNum = new JTextField();
		jtf_flightNum.setBounds(130, 47, 120, 25);
		jtf_leaveDate.setBounds(410, 47, 120, 25);
		jtf_leaveCity.setBounds(130, 77, 120, 25);
		jtf_arriveCity.setBounds(410, 77, 120, 25);
		jtf_price.setBounds(130, 107, 120, 25);
		jtf_name.setBounds(130, 177, 120, 25);
		jtf_sex.setBounds(410, 177, 120, 25);
		jtf_IDnumber.setBounds(130, 207, 120, 25);
		jtf_phone.setBounds(410, 207, 120, 25);
		jtf_ticketNum.setBounds(130, 237, 120, 25);
		jtf_orderNum.setBounds(410, 237, 120, 25);
		jtf_IDnumber.setColumns(18);

		jtf_flightNum.setText(flight.getFlightNum());
		jtf_leaveDate.setText(flight.getLeaveDate());
		jtf_leaveCity.setText(flight.getLeaveCity());
		jtf_arriveCity.setText(flight.getArriveCity());
		jtf_price.setText(String.valueOf(flight.getPrice()));
		List list = null;
		try {
			list = DBUtils.findAllOrder();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Order order = (Order) list.get(list.size() - 1);
		jtf_orderNum.setText(String.valueOf(Integer.parseInt(order
				.getOrderNum()) + 1));

		jb_ensureInfo = new JButton("确认");
		cancel = new JButton("取消");
		jb_ensureInfo.setBounds(180, 295, 60, 25);
		cancel.setBounds(300, 295, 60, 25);
		cancel.addActionListener(this);
		jb_ensureInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean flag1 = DBUtils.insert(new Customer(jtf_name
							.getText(), jtf_sex.getText(), jtf_IDnumber
							.getText(), jtf_phone.getText()));
					boolean flag2 = DBUtils.insert(new Order(jtf_orderNum
							.getText(), jtf_IDnumber.getText(), jtf_flightNum
							.getText(), Integer.parseInt(jtf_ticketNum
							.getText()), new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date()), com.airplane.Login.tf_user
							.getText()));
					if (flag1 && flag2) {
						JOptionPane.showMessageDialog(jp_purchaseTicket,
								"购买成功！", "提示框", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jp_purchaseTicket.add(jl_flightMsg);
		jp_purchaseTicket.add(jl_flightNum);
		jp_purchaseTicket.add(jl_leaveDate);
		jp_purchaseTicket.add(jl_leaveCity);
		jp_purchaseTicket.add(jl_arriveCity);
		jp_purchaseTicket.add(jl_price);
		jp_purchaseTicket.add(jl_customerMsg);
		jp_purchaseTicket.add(jl_name);
		jp_purchaseTicket.add(jl_sex);
		jp_purchaseTicket.add(jl_IDnumber);
		jp_purchaseTicket.add(jl_phone);
		jp_purchaseTicket.add(jl_ticketNum);
		jp_purchaseTicket.add(jl_orderNum);
		jp_purchaseTicket.add(jtf_flightNum);
		jp_purchaseTicket.add(jtf_leaveDate);
		jp_purchaseTicket.add(jtf_leaveCity);
		jp_purchaseTicket.add(jtf_arriveCity);
		jp_purchaseTicket.add(jtf_price);
		jp_purchaseTicket.add(jtf_name);
		jp_purchaseTicket.add(jtf_sex);
		jp_purchaseTicket.add(jtf_IDnumber);
		jp_purchaseTicket.add(jtf_phone);
		jp_purchaseTicket.add(jtf_ticketNum);
		jp_purchaseTicket.add(jtf_orderNum);
		jp_purchaseTicket.add(jb_ensureInfo);
		jp_purchaseTicket.add(cancel);

		jp_purchaseTicket.revalidate();
		jp_purchaseTicket.repaint();
	}

	public void cancel() {
		if (jp_flightMsg != null)
			com.airplane.Function.frame.remove(jp_flightMsg);

		jp_cancel = new JPanel();
		jp_cancel.setBounds(0, 46, 584, 365);
		com.airplane.Function.frame.getContentPane().add(jp_cancel);
		jp_cancel.setLayout(null);

		jp_cancel.revalidate();
		jp_cancel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "取消":
			cancel();
			break;

		default:
			break;
		}
	}

}
