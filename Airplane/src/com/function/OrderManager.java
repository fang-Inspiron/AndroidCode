package com.function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bean.Flight;
import com.bean.Order;
import com.utils.DBUtils;

public class OrderManager implements ActionListener {
	static JPanel jp_cancel, jp_function, jp_findOne, jp_findAll, jp_modify,jp_delete;
	JButton jb_findOne, jb_findAll, jb_delete, jb_modify, cancel, jb_ensure;
	JLabel jl_orderNum, jl_IDnumber, jl_flightNum, jl_ticketNum,
			jl_purchaseDate, jl_conductor;
	JTextField jtf_findOne, jtf_orderNum, jtf_IDnumber, jtf_flightNum,
			jtf_ticketNum, jtf_purchaseDate, jtf_conductor, jtf_modify;
	String selectedOrder = null;

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
		if (com.function.TicketManager.jp_flightMsg != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_flightMsg);
		if (com.function.TicketManager.jp_cancel != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_cancel);
		if (com.function.TicketManager.jp_purchaseTicket != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_purchaseTicket);

	}

	public OrderManager() {
		remove();
		OrderFunc();
		cancel();
	}

	public void OrderFunc() {
		jp_function = new JPanel();
		jp_function.setBounds(0, 330, 584, 80);
		jp_function.setLayout(null);
		com.airplane.Function.frame.getContentPane().add(jp_function);

		jtf_findOne = new JTextField("请输入订单号");
		jtf_modify = new JTextField("请输入订单号");
		jb_findOne = new JButton("查询订单号");
		jb_findAll = new JButton("显示所有");
		jb_delete = new JButton("撤销订单");
		jb_modify = new JButton("修改订单");
		jtf_findOne.setBounds(59, 10, 100, 25);
		jtf_modify.setBounds(420, 10, 100, 25);
		jb_findOne.setBounds(59, 40, 100, 25);
		jb_findAll.setBounds(188, 40, 90, 25);
		jb_delete.setBounds(308, 40, 90, 25);
		jb_modify.setBounds(421, 40, 90, 25);
		jtf_findOne.setVisible(false);
		jtf_modify.setVisible(false);
		jb_findOne.addActionListener(this);
		jb_findAll.addActionListener(this);
		jb_delete.addActionListener(this);
		jb_modify.addActionListener(this);

		jp_function.add(jtf_findOne);
		jp_function.add(jtf_modify);
		jp_function.add(jb_findOne);
		jp_function.add(jb_findAll);
		jp_function.add(jb_delete);
		jp_function.add(jb_modify);

		jp_function.revalidate();
		jp_function.repaint();
	}

	public void findOne() {
		if (jp_findAll != null)
			com.airplane.Function.frame.remove(jp_findAll);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);

		jp_findOne = new JPanel();
		jp_findOne.setBounds(0, 46, 584, 280);
		com.airplane.Function.frame.getContentPane().add(jp_findOne);
		jp_findOne.setLayout(null);

		jl_orderNum = new JLabel("*  订  单  号：");
		jl_IDnumber = new JLabel("*  身份证号：");
		jl_flightNum = new JLabel("*  航班编号：");
		jl_ticketNum = new JLabel("*  购票数目：");
		jl_purchaseDate = new JLabel("*  购票日期：");
		jl_conductor = new JLabel("*  售  票  人：");
		jl_orderNum.setBounds(60, 50, 80, 15);
		jl_IDnumber.setBounds(310, 50, 80, 15);
		jl_flightNum.setBounds(60, 110, 80, 15);
		jl_ticketNum.setBounds(310, 110, 80, 15);
		jl_purchaseDate.setBounds(60, 170, 80, 15);
		jl_conductor.setBounds(310, 170, 80, 15);

		jtf_orderNum = new JTextField();
		jtf_IDnumber = new JTextField();
		jtf_flightNum = new JTextField();
		jtf_ticketNum = new JTextField();
		jtf_purchaseDate = new JTextField();
		jtf_conductor = new JTextField();
		jtf_orderNum.setBounds(145, 47, 120, 25);
		jtf_IDnumber.setBounds(395, 47, 120, 25);
		jtf_flightNum.setBounds(145, 107, 120, 25);
		jtf_ticketNum.setBounds(395, 107, 120, 25);
		jtf_purchaseDate.setBounds(145, 167, 120, 25);
		jtf_conductor.setBounds(395, 167, 120, 25);

		jtf_findOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Order order = null;
				try {
					order = DBUtils.findOneOrder(jtf_findOne.getText().trim());
					System.out.println(order);
					if (order != null) {
						jtf_orderNum.setText(order.getOrderNum());
						jtf_IDnumber.setText(order.getIDnumber());
						jtf_flightNum.setText(order.getFlightNum());
						jtf_ticketNum.setText(String.valueOf(order
								.getTicketNum()));
						jtf_purchaseDate.setText(order.getPurchaseDate());
						jtf_conductor.setText(order.getConductor());
					} else {
						JOptionPane.showMessageDialog(jp_findOne, "没有该航班的信息！",
								"提示框", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jp_findOne.add(jl_orderNum);
		jp_findOne.add(jl_IDnumber);
		jp_findOne.add(jl_flightNum);
		jp_findOne.add(jl_ticketNum);
		jp_findOne.add(jl_purchaseDate);
		jp_findOne.add(jl_conductor);
		jp_findOne.add(jtf_orderNum);
		jp_findOne.add(jtf_IDnumber);
		jp_findOne.add(jtf_flightNum);
		jp_findOne.add(jtf_ticketNum);
		jp_findOne.add(jtf_purchaseDate);
		jp_findOne.add(jtf_conductor);

		jp_findOne.revalidate();
		jp_findOne.repaint();
	}

	public void findAll() {
		if (jp_findOne != null)
			com.airplane.Function.frame.remove(jp_findOne);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);

		jp_findAll = new JPanel();
		jp_findAll.setBounds(0, 46, 584, 280);
		com.airplane.Function.frame.getContentPane().add(jp_findAll);
		jp_findAll.setLayout(null);

		List list = null;
		try {
			list = DBUtils.findAllOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] columnNames = { "订单号", "身份证号", "航班编号", "购票数目", "购票时间", "售票人" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Order order = (Order) list.get(i);
			tableValues[i][0] = order.getOrderNum();
			tableValues[i][1] = order.getIDnumber();
			tableValues[i][2] = order.getFlightNum();
			tableValues[i][3] = String.valueOf(order.getTicketNum());
			tableValues[i][4] = order.getPurchaseDate();
			tableValues[i][5] = order.getConductor();
		}

		JTable table = new JTable(tableValues, columnNames);// 创建指定列名和数据的表格
		table.setBounds(0, 0, 584, 280);
		JScrollPane scrollPane = new JScrollPane(table);// 创建显示表格的滚动面板
		scrollPane.setBounds(0, 0, 584, 280);

		jp_findAll.add(scrollPane);
		jp_findAll.revalidate();
		jp_findAll.repaint();
	}

	public void delete() {
		if (jp_findOne != null)
			com.airplane.Function.frame.remove(jp_findOne);
		if (jp_findAll != null)
			com.airplane.Function.frame.remove(jp_findAll);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);

		jp_delete = new JPanel();
		jp_delete.setBounds(0, 46, 584, 280);
		com.airplane.Function.frame.getContentPane().add(jp_delete);
		jp_delete.setLayout(null);

		List list = null;
		try {
			list = DBUtils.findAllOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] columnNames = { "订单号", "身份证号", "航班编号", "购票数目", "购票日期", "售票人" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Order order = (Order) list.get(i);
			tableValues[i][0] = order.getOrderNum();
			tableValues[i][1] = order.getIDnumber();
			tableValues[i][2] = order.getFlightNum();
			tableValues[i][3] = String.valueOf(order.getTicketNum());
			tableValues[i][4] = order.getPurchaseDate();
			tableValues[i][5] = order.getConductor();
		}

		final JTable table = new JTable(tableValues, columnNames);// 创建指定列名和数据的表格
		table.setBounds(10, 10, 560, 200);
		JScrollPane scrollPane = new JScrollPane(table);// 创建显示表格的滚动面板
		scrollPane.setBounds(10, 10, 560, 200);

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					selectedOrder = (String) table.getValueAt(
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

		jb_ensure = new JButton("确认撤销");
		cancel = new JButton("取消");
		jb_ensure.setBounds(180, 250, 90, 25);
		cancel.setBounds(300, 250, 90, 25);
		cancel.addActionListener(this);
		jb_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(jp_delete, "请选择所要购买的航班信息！",
							"提示框", JOptionPane.CANCEL_OPTION);
				} else {
					try {
						if (DBUtils.deleteOrder(selectedOrder)) {
							JOptionPane.showMessageDialog(jp_delete, "订单撤销成功！",
									"提示框", JOptionPane.CANCEL_OPTION);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		jp_delete.add(scrollPane);
		jp_delete.add(jb_ensure);
		jp_delete.add(cancel);

		jp_delete.revalidate();
		jp_delete.repaint();
	}

	public void modify() {
		if (jp_findOne != null)
			com.airplane.Function.frame.remove(jp_findOne);
		if (jp_findAll != null)
			com.airplane.Function.frame.remove(jp_findAll);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);

		jp_modify = new JPanel();
		jp_modify.setBounds(0, 46, 584, 280);
		com.airplane.Function.frame.getContentPane().add(jp_modify);
		jp_modify.setLayout(null);

		jl_orderNum = new JLabel("*  订  单  号：");
		jl_IDnumber = new JLabel("*  身份证号：");
		jl_flightNum = new JLabel("*  航班编号：");
		jl_ticketNum = new JLabel("*  购票数目：");
		jl_purchaseDate = new JLabel("*  购票日期：");
		jl_conductor = new JLabel("*  售  票  人：");
		jl_orderNum.setBounds(60, 50, 80, 15);
		jl_IDnumber.setBounds(310, 50, 80, 15);
		jl_flightNum.setBounds(60, 110, 80, 15);
		jl_ticketNum.setBounds(310, 110, 80, 15);
		jl_purchaseDate.setBounds(60, 170, 80, 15);
		jl_conductor.setBounds(310, 170, 80, 15);

		jtf_orderNum = new JTextField();
		jtf_IDnumber = new JTextField();
		jtf_flightNum = new JTextField();
		jtf_ticketNum = new JTextField();
		jtf_purchaseDate = new JTextField();
		jtf_conductor = new JTextField();
		jtf_orderNum.setBounds(145, 47, 120, 25);
		jtf_IDnumber.setBounds(395, 47, 120, 25);
		jtf_flightNum.setBounds(145, 107, 120, 25);
		jtf_ticketNum.setBounds(395, 107, 120, 25);
		jtf_purchaseDate.setBounds(145, 167, 120, 25);
		jtf_conductor.setBounds(395, 167, 120, 25);

		jtf_modify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Order order = null;
				try {
					order = DBUtils.findOneOrder(jtf_modify.getText().trim());
					System.out.println(order);
					if (order != null) {
						jtf_orderNum.setText(order.getOrderNum());
						jtf_IDnumber.setText(order.getIDnumber());
						jtf_flightNum.setText(order.getFlightNum());
						jtf_ticketNum.setText(String.valueOf(order
								.getTicketNum()));
						jtf_purchaseDate.setText(order.getPurchaseDate());
						jtf_conductor.setText(order.getConductor());
					} else {
						JOptionPane.showMessageDialog(jp_delete, "没有该航班的信息！",
								"提示框", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jb_ensure = new JButton("确认修改");
		cancel = new JButton("取消");
		jb_ensure.setBounds(180, 250, 90, 25);
		cancel.setBounds(300, 250, 90, 25);
		cancel.addActionListener(this);
		jb_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (DBUtils.modify(
							jtf_modify.getText(),
							new Order(jtf_orderNum.getText(), jtf_IDnumber
									.getText(), jtf_flightNum.getText(),
									Integer.parseInt(jtf_ticketNum.getText()),
									jtf_purchaseDate.getText(), jtf_conductor
											.getText()))) {
						JOptionPane.showMessageDialog(jp_modify,
								"修改成功！", "提示框", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jp_modify.add(jl_orderNum);
		jp_modify.add(jl_IDnumber);
		jp_modify.add(jl_flightNum);
		jp_modify.add(jl_ticketNum);
		jp_modify.add(jl_purchaseDate);
		jp_modify.add(jl_conductor);
		jp_modify.add(jtf_orderNum);
		jp_modify.add(jtf_IDnumber);
		jp_modify.add(jtf_flightNum);
		jp_modify.add(jtf_ticketNum);
		jp_modify.add(jtf_purchaseDate);
		jp_modify.add(jtf_conductor);
		jp_modify.add(jb_ensure);
		jp_modify.add(cancel);

		jp_modify.revalidate();
		jp_modify.repaint();
	}

	public void cancel() {
		if (jp_findOne != null)
			com.airplane.Function.frame.remove(jp_findOne);
		if (jp_findAll != null)
			com.airplane.Function.frame.remove(jp_findAll);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);

		jp_cancel = new JPanel();
		jp_cancel.setBounds(0, 46, 584, 280);
		com.airplane.Function.frame.getContentPane().add(jp_cancel);
		jp_cancel.setLayout(null);

		jp_cancel.revalidate();
		jp_cancel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "查询订单号":
			jtf_findOne.setVisible(true);
			jtf_modify.setVisible(false);
			jtf_findOne.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
				}

				@Override
				public void focusGained(FocusEvent e) {
					jtf_findOne.setText("");
				}
			});
			findOne();
			break;
		case "显示所有":
			jtf_findOne.setVisible(false);
			jtf_modify.setVisible(false);
			findAll();
			break;
		case "撤销订单":
			jtf_findOne.setVisible(false);
			jtf_modify.setVisible(false);
			delete();
			break;
		case "修改订单":
			jtf_findOne.setVisible(false);
			jtf_modify.setVisible(true);
			jtf_modify.addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
				}

				@Override
				public void focusGained(FocusEvent e) {
					jtf_modify.setText("");
				}
			});
			modify();
			break;
		case "取消":
			cancel();
			break;
		default:
			break;
		}
	}
}
