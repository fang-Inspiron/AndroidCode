package com.function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.airplane.Function;
import com.bean.Order;
import com.thread.GetDeleteResult;
import com.thread.GetFindAllResult;
import com.thread.GetModifyFindResult;
import com.thread.GetOrderAllResult;

public class OrderManager implements ActionListener {
	public static JPanel jp_cancel, jp_function, jp_findOne, jp_findAll,
			jp_delete;
	static JButton jb_findOne, jb_findAll, jb_delete, cancel, jb_ensure;
	static JLabel jl_orderNum, jl_IDnumber, jl_flightNum, jl_ticketNum,
			jl_purchaseDate, jl_conductor;
	static JTextField jtf_findOne, jtf_orderNum, jtf_IDnumber, jtf_flightNum,
			jtf_ticketNum, jtf_purchaseDate, jtf_conductor;
	static String selectedOrder = null;
	static JScrollPane scrollPane;
	static JTable table;
	public static List<Map<String, Order>> list = null;

	public void remove() {
		if (FlightManager.jp_function != null)
			Function.frame.getContentPane().remove(FlightManager.jp_function);
		if (FlightManager.jp_cancel != null)
			Function.frame.getContentPane().remove(FlightManager.jp_cancel);
		if (FlightManager.jp_insert != null)
			Function.frame.getContentPane().remove(FlightManager.jp_insert);
		if (FlightManager.jp_delete != null)
			Function.frame.getContentPane().remove(FlightManager.jp_delete);
		if (FlightManager.jp_modify != null)
			Function.frame.getContentPane().remove(FlightManager.jp_modify);
		if (FlightManager.jp_find != null)
			Function.frame.getContentPane().remove(FlightManager.jp_find);
		if (FlightManager.jp_one != null)
			Function.frame.getContentPane().remove(FlightManager.jp_one);
		if (FlightManager.jp_all != null)
			Function.frame.getContentPane().remove(FlightManager.jp_all);
		if (TicketManager.jp_flightMsg != null)
			Function.frame.getContentPane().remove(TicketManager.jp_flightMsg);
		if (TicketManager.jp_cancel != null)
			Function.frame.getContentPane().remove(TicketManager.jp_cancel);
		if (TicketManager.jp_purchaseTicket != null)
			Function.frame.getContentPane().remove(TicketManager.jp_purchaseTicket);
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
		Function.frame.getContentPane().add(jp_function);

		jtf_findOne = new JTextField("�����붩����");
		jb_findOne = new JButton("��ѯ������");
		jb_findAll = new JButton("��ʾ����");
		jb_delete = new JButton("��������");
		jtf_findOne.setBounds(70, 10, 100, 25);
		jb_findOne.setBounds(70, 40, 100, 25);
		jb_findAll.setBounds(240, 40, 90, 25);
		jb_delete.setBounds(400, 40, 90, 25);
		jtf_findOne.setVisible(false);
		jb_findOne.addActionListener(this);
		jb_findAll.addActionListener(this);
		jb_delete.addActionListener(this);

		jp_function.add(jtf_findOne);
		jp_function.add(jb_findOne);
		jp_function.add(jb_findAll);
		jp_function.add(jb_delete);

		jp_function.revalidate();
		jp_function.repaint();
	}

	public static void findOne() {
		if (jp_findAll != null)
			Function.frame.remove(jp_findAll);
		if (jp_delete != null)
			Function.frame.remove(jp_delete);

		jp_findOne = new JPanel();
		jp_findOne.setBounds(0, 46, 584, 280);
		Function.frame.getContentPane().add(jp_findOne);
		jp_findOne.setLayout(null);

		jl_orderNum = new JLabel("*  ��  ��  �ţ�");
		jl_IDnumber = new JLabel("*  ���֤�ţ�");
		jl_flightNum = new JLabel("*  �����ţ�");
		jl_ticketNum = new JLabel("*  ��Ʊ��Ŀ��");
		jl_purchaseDate = new JLabel("*  ��Ʊ���ڣ�");
		jl_conductor = new JLabel("*  ��  Ʊ  �ˣ�");
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
				String str = jtf_findOne.getText().trim();
				new GetModifyFindResult(str, 4, Function.frame).start();
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

	public static void change(Order order) {
		jtf_orderNum.setText(order.getOrderNum());
		jtf_IDnumber.setText(order.getIDnumber());
		jtf_flightNum.setText(order.getFlightNum());
		jtf_ticketNum.setText(String.valueOf(order.getTicketNum()));
		jtf_purchaseDate.setText(order.getPurchaseDate());
		jtf_conductor.setText(order.getConductor());
	}

	public static void findAll() {
		if (jp_findOne != null)
			Function.frame.remove(jp_findOne);
		if (jp_delete != null)
			Function.frame.remove(jp_delete);

		jp_findAll = new JPanel();
		jp_findAll.setBounds(0, 46, 584, 280);
		Function.frame.getContentPane().add(jp_findAll);
		jp_findAll.setLayout(null);

//		if (GetFindAllResult.getInfo) {
//			list = GetFindAllResult.orderList;
//		} else {
//			System.out.println("hhh");
//		}
		String[] columnNames = { "������", "���֤��", "������", "��Ʊ��Ŀ", "��Ʊʱ��", "��Ʊ��" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Order order = list.get(i).get("order");
			tableValues[i][0] = order.getOrderNum();
			tableValues[i][1] = order.getIDnumber();
			tableValues[i][2] = order.getFlightNum();
			tableValues[i][3] = String.valueOf(order.getTicketNum());
			tableValues[i][4] = order.getPurchaseDate();
			tableValues[i][5] = order.getConductor();
		}

		final JTable table = new JTable(tableValues, columnNames);// ����ָ�����������ݵı��
		table.setBounds(0, 0, 584, 280);
		JScrollPane scrollPane = new JScrollPane(table);// ������ʾ���Ĺ������
		scrollPane.setBounds(0, 0, 584, 280);

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					selectedOrder = (String) table.getValueAt(table.getSelectedRow(), 0);
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
		jp_findAll.add(scrollPane);
	}

	public static void findAllForDelete() {
//		if (jp_findOne != null)
//			Function.frame.remove(jp_findOne);
//		if (jp_findAll != null)
//			Function.frame.remove(jp_delete);
//
//		jp_delete = new JPanel();
//		jp_delete.setBounds(0, 46, 584, 280);
//		Function.frame.getContentPane().add(jp_delete);
//		jp_delete.setLayout(null);

//		if (GetFindAllResult.getInfo) {
//			list = GetFindAllResult.orderList;
//		} else {
//			System.out.println("hhh");
//		}
		String[] columnNames = { "������", "���֤��", "������", "��Ʊ��Ŀ", "��Ʊʱ��", "��Ʊ��" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Order order = list.get(i).get("order");
			tableValues[i][0] = order.getOrderNum();
			tableValues[i][1] = order.getIDnumber();
			tableValues[i][2] = order.getFlightNum();
			tableValues[i][3] = String.valueOf(order.getTicketNum());
			tableValues[i][4] = order.getPurchaseDate();
			tableValues[i][5] = order.getConductor();
		}
		
		System.out.println("list:"+list);
		table = new JTable(tableValues, columnNames);// ����ָ�����������ݵı��
		table.setBounds(0, 0, 584, 240);
		JScrollPane scrollPane = new JScrollPane(table);// ������ʾ���Ĺ������
		scrollPane.setBounds(0, 0, 584, 240);

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedRow() != -1) {
					selectedOrder = (String) table.getValueAt(table.getSelectedRow(), 0);
					System.out.println("selectedOrder:"+selectedOrder);
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
		jp_delete.add(scrollPane);
	}

	public void delete() {
		if (jp_findOne != null)
			Function.frame.remove(jp_findOne);
		if (jp_findAll != null)
			Function.frame.remove(jp_findAll);

		jp_delete = new JPanel();
		jp_delete.setBounds(0, 46, 584, 280);
		Function.frame.getContentPane().add(jp_delete);
		jp_delete.setLayout(null);
		
		new GetOrderAllResult(4, Function.frame).start();
		
		jb_ensure = new JButton("ȷ�ϳ���");
		cancel = new JButton("ȡ��");
		jb_ensure.setBounds(180, 250, 90, 25);
		cancel.setBounds(300, 250, 90, 25);
		cancel.addActionListener(this);
		jb_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(selectedOrder);
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(jp_delete, "��ѡ����Ҫ�����ĺ�����Ϣ��",
							"��ʾ��", JOptionPane.CANCEL_OPTION);
				} else {
					new GetDeleteResult(selectedOrder, 4, Function.frame).start();
				}
			}
		});
		jp_delete.add(jb_ensure);
		jp_delete.add(cancel);
		jp_delete.revalidate();
		jp_delete.repaint();
	}



	public void cancel() {
		if (jp_findOne != null)
			Function.frame.remove(jp_findOne);
		if (jp_findAll != null)
			Function.frame.remove(jp_findAll);
		if (jp_delete != null)
			Function.frame.remove(jp_delete);

		jp_cancel = new JPanel();
		jp_cancel.setBounds(0, 46, 584, 280);
		Function.frame.getContentPane().add(jp_cancel);
		jp_cancel.setLayout(null);

		jp_cancel.revalidate();
		jp_cancel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "��ѯ������":
			jtf_findOne.setVisible(true);
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
		case "��ʾ����":
			jtf_findOne.setVisible(false);
			new GetFindAllResult(4, Function.frame).start();
			break;
		case "��������":
			jtf_findOne.setVisible(false);
			delete();
			break;
		case "ȡ��":
			cancel();
			break;
		default:
			break;
		}
	}
}
