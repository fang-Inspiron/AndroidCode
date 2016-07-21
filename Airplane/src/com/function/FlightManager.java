package com.function;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bean.Flight;
import com.utils.DBUtils;

public class FlightManager implements ActionListener, ItemListener {

	static JPanel jp_function, jp_cancel, jp_insert, jp_delete, jp_modify,
			jp_find, jp_one, jp_all;
	JButton insert, delete, modify, find, cancel, insert_ensure, delete_ensure,
			modify_ensure, find_ensure;
	JLabel jLabel, jl_flightNum, jl_leaveCity, jl_arriveCity, jl_leaveDate,
			jl_price;
	JTextField jtf_flightNum, jtf_leaveCity, jtf_arriveCity, jtf_leaveDate,
			jtf_price, jtf_delete, jtf_modify, jtf_find;
	ButtonGroup bg_find;
	JRadioButton rb_findOne, rb_findAll;

	public void remove() {
		if (com.function.TicketManager.jp_flightMsg != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_flightMsg);
		if (com.function.TicketManager.jp_cancel != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_cancel);
		if (com.function.TicketManager.jp_purchaseTicket != null)
			com.airplane.Function.frame.getContentPane().remove(
					com.function.TicketManager.jp_purchaseTicket);
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

	public FlightManager() {
		remove();
		fightFunc();
		cancel();
	}

	public void fightFunc() {
		jp_function = new JPanel();
		jp_function.setBounds(0, 365, 584, 45);
		jp_function.setLayout(null);
		com.airplane.Function.frame.getContentPane().add(jp_function);

		insert = new JButton("��������");
		delete = new JButton("��������");
		modify = new JButton("�޸ĺ���");
		find = new JButton("���Һ���");
		insert.setBounds(69, 7, 90, 25);
		delete.setBounds(188, 7, 90, 25);
		modify.setBounds(308, 7, 90, 25);
		find.setBounds(421, 7, 90, 25);
		insert.addActionListener(this);
		delete.addActionListener(this);
		modify.addActionListener(this);
		find.addActionListener(this);

		jp_function.add(insert);
		jp_function.add(delete);
		jp_function.add(modify);
		jp_function.add(find);

		jp_function.revalidate();
		jp_function.repaint();

	}

	public void cancel() {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_cancel = new JPanel();
		jp_cancel.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_cancel);
		jp_cancel.setLayout(null);

		jp_cancel.revalidate();
		jp_cancel.repaint();

	}

	public void insertFight() {
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_insert = new JPanel();
		jp_insert.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_insert);
		jp_insert.setLayout(null);

		jl_flightNum = new JLabel("*  �����ţ�");
		jl_leaveDate = new JLabel("*  �������ڣ�");
		jl_leaveCity = new JLabel("*  �������У�");
		jl_arriveCity = new JLabel("*  ������У�");
		jl_price = new JLabel("*  ��Ʊ�۸�");
		jl_flightNum.setBounds(60, 40, 80, 15);
		jl_leaveDate.setBounds(310, 40, 80, 15);
		jl_leaveCity.setBounds(60, 90, 80, 15);
		jl_arriveCity.setBounds(310, 90, 80, 15);
		jl_price.setBounds(60, 140, 80, 15);

		jtf_flightNum = new JTextField();
		jtf_leaveDate = new JTextField();
		jtf_leaveCity = new JTextField();
		jtf_arriveCity = new JTextField();
		jtf_price = new JTextField();
		jtf_flightNum.setBounds(145, 37, 120, 25);
		jtf_leaveDate.setBounds(395, 37, 120, 25);
		jtf_leaveCity.setBounds(145, 87, 120, 25);
		jtf_arriveCity.setBounds(395, 87, 120, 25);
		jtf_price.setBounds(145, 137, 120, 25);
		jtf_flightNum.setColumns(10);
		jtf_leaveDate.setColumns(10);
		jtf_leaveCity.setColumns(10);
		jtf_arriveCity.setColumns(10);
		jtf_price.setColumns(10);

		insert_ensure = new JButton("���");
		cancel = new JButton("ȡ��");
		insert_ensure.setBounds(157, 240, 60, 25);
		cancel.setBounds(305, 240, 60, 25);
		cancel.addActionListener(this);
		insert_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean flag = DBUtils.insert(new Flight(jtf_flightNum
							.getText(), jtf_leaveCity.getText(), jtf_arriveCity
							.getText(), jtf_leaveDate.getText(), Integer
							.parseInt(jtf_price.getText())));
					if (flag) {
						JOptionPane.showMessageDialog(jp_insert, "��ӳɹ���",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					} else {
						JOptionPane.showMessageDialog(jp_insert, "���ʧ�ܣ�",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jp_insert.add(jl_flightNum);
		jp_insert.add(jl_leaveDate);
		jp_insert.add(jl_leaveCity);
		jp_insert.add(jl_arriveCity);
		jp_insert.add(jl_price);
		jp_insert.add(jtf_flightNum);
		jp_insert.add(jtf_leaveDate);
		jp_insert.add(jtf_leaveCity);
		jp_insert.add(jtf_arriveCity);
		jp_insert.add(jtf_price);
		jp_insert.add(insert_ensure);
		jp_insert.add(cancel);
		jp_insert.revalidate();
		jp_insert.repaint();

	}

	public void deleteFight() {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_delete = new JPanel();
		jp_delete.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_delete);
		jp_delete.setLayout(null);

		JLabel jLabel = new JLabel("��ѡ����Ҫ�����ĺ����ţ�");
		jLabel.setBounds(50, 50, 180, 20);
		jtf_delete = new JTextField();
		jtf_delete.setBounds(250, 50, 100, 25);

		delete_ensure = new JButton("����");
		cancel = new JButton("ȡ��");
		delete_ensure.setBounds(200, 220, 60, 25);
		cancel.setBounds(330, 220, 60, 25);
		cancel.addActionListener(this);
		delete_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean flag = DBUtils.deleteFlight(jtf_delete.getText());
					if (flag) {
						JOptionPane.showMessageDialog(jp_delete, "�����ɹ���",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					} else {
						JOptionPane.showMessageDialog(jp_delete, "����ʧ�ܣ�",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		jp_delete.add(jtf_delete);
		jp_delete.add(jLabel);
		jp_delete.add(delete_ensure);
		jp_delete.add(cancel);
		jp_delete.revalidate();
		jp_delete.repaint();

	}

	public void modifyFight() {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_modify = new JPanel();
		jp_modify.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_modify);
		jp_modify.setLayout(null);

		jLabel = new JLabel("��ѡ����Ҫ�޸ĵĺ����ţ�");
		jLabel.setBounds(10, 47, 180, 17);
		jtf_modify = new JTextField();
		jtf_modify.setBounds(40, 70, 100, 25);

		jl_flightNum = new JLabel("*  �����ţ�");
		jl_leaveDate = new JLabel("*  �������ڣ�");
		jl_leaveCity = new JLabel("*  �������У�");
		jl_arriveCity = new JLabel("*  ������У�");
		jl_price = new JLabel("* ��Ʊ�۸�");
		jl_flightNum.setBounds(220, 35, 80, 15);
		jl_leaveDate.setBounds(220, 85, 80, 15);
		jl_leaveCity.setBounds(220, 135, 80, 15);
		jl_arriveCity.setBounds(220, 185, 80, 15);
		jl_price.setBounds(220, 235, 80, 15);

		jtf_flightNum = new JTextField();
		jtf_leaveDate = new JTextField();
		jtf_leaveCity = new JTextField();
		jtf_arriveCity = new JTextField();
		jtf_price = new JTextField();
		jtf_flightNum.setBounds(300, 30, 120, 25);
		jtf_leaveDate.setBounds(300, 80, 120, 25);
		jtf_leaveCity.setBounds(300, 130, 120, 25);
		jtf_arriveCity.setBounds(300, 180, 120, 25);
		jtf_price.setBounds(300, 230, 120, 25);
		jtf_flightNum.setColumns(10);
		jtf_leaveDate.setColumns(10);
		jtf_leaveCity.setColumns(10);
		jtf_arriveCity.setColumns(10);
		jtf_price.setColumns(10);

		jtf_modify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str = jtf_modify.getText().trim();
				try {
					Flight flight = DBUtils.findOneFlight(str);
					if (flight != null) {
						jtf_flightNum.setText(flight.getFlightNum());
						jtf_leaveCity.setText(flight.getLeaveCity());
						jtf_arriveCity.setText(flight.getArriveCity());
						jtf_leaveDate.setText(flight.getLeaveDate());
						jtf_price.setText(String.valueOf(flight.getPrice()));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		modify_ensure = new JButton("�޸�");
		cancel = new JButton("ȡ��");
		modify_ensure.setBounds(500, 100, 60, 25);
		cancel.setBounds(500, 150, 60, 25);
		cancel.addActionListener(this);
		modify_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean flag = DBUtils.modify(
							jtf_flightNum.getText(),
							new Flight(jtf_flightNum.getText(), jtf_leaveCity
									.getText(), jtf_arriveCity.getText(),
									jtf_leaveDate.getText(), Integer
											.parseInt(jtf_price.getText())));
					if (flag) {
						JOptionPane.showMessageDialog(jp_delete, "�޸ĳɹ���",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					} else {
						JOptionPane.showMessageDialog(jp_delete, "�޸�ʧ�ܣ�",
								"��ʾ��", JOptionPane.CANCEL_OPTION);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});

		jp_modify.add(jtf_modify);
		jp_modify.add(jLabel);
		jp_modify.add(jl_flightNum);
		jp_modify.add(jl_leaveDate);
		jp_modify.add(jl_leaveCity);
		jp_modify.add(jl_arriveCity);
		jp_modify.add(jl_price);
		jp_modify.add(jtf_flightNum);
		jp_modify.add(jtf_leaveDate);
		jp_modify.add(jtf_leaveCity);
		jp_modify.add(jtf_arriveCity);
		jp_modify.add(jtf_price);
		jp_modify.add(modify_ensure);
		jp_modify.add(cancel);

		jp_modify.revalidate();
		jp_modify.repaint();

	}

	public void findFight() {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_find = new JPanel();
		jp_find.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_find);
		jp_find.setLayout(null);

		// ���嵥ѡ��ť�Ͱ�ť��ؼ�
		bg_find = new ButtonGroup();
		rb_findAll = new JRadioButton("�������к������Ϣ");
		rb_findOne = new JRadioButton("����һ���������Ϣ");
		rb_findAll.setBounds(200, 50, 150, 23);
		rb_findOne.setBounds(200, 100, 150, 23);
		rb_findAll.addItemListener(this);
		rb_findOne.addItemListener(this);
		// ����ѡ��ť��ӵ���ť��ؼ���
		bg_find.add(rb_findAll);
		bg_find.add(rb_findOne);

		// ��ʾ
		jLabel = new JLabel("��������Ҫ���ҵĺ����ţ�");
		jLabel.setBounds(200, 150, 170, 15);
		jLabel.setVisible(false);
		// ������趨
		jtf_find = new JTextField();
		jtf_find.setBounds(230, 180, 100, 25);
		jtf_find.setVisible(false);
		jtf_find.setColumns(10);

		// ȷ�� �� ȡ����ť������
		find_ensure = new JButton("����");
		cancel = new JButton("ȡ��");
		find_ensure.setBounds(180, 240, 60, 25);
		cancel.setBounds(310, 240, 60, 25);
		find_ensure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rb_findAll.isSelected()) {
					showAll();
				} else if (rb_findOne.isSelected()) {
					showOne(jtf_find.getText());
				}
			}
		});
		cancel.addActionListener(this);

		// ���������ӵ�JPanel�����
		jp_find.add(rb_findAll);
		jp_find.add(rb_findOne);
		jp_find.add(jLabel);
		jp_find.add(jtf_find);
		jp_find.add(find_ensure);
		jp_find.add(cancel);

		jp_find.revalidate();
		jp_find.repaint();

	}

	public void showAll() {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_one != null)
			com.airplane.Function.frame.remove(jp_one);

		jp_all = new JPanel();
		jp_all.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_all);
		jp_all.setLayout(null);

		List list = null;
		try {
			list = DBUtils.findAllFlight();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] columnNames = { "������", "��������", "�������", "��������", "��Ʊ�۸�" };
		String[][] tableValues = new String[list.size()][columnNames.length];

		for (int i = 0; i < list.size(); i++) {
			Flight flight = (Flight) list.get(i);
			tableValues[i][0] = flight.getFlightNum();
			tableValues[i][1] = flight.getLeaveCity();
			tableValues[i][2] = flight.getArriveCity();
			tableValues[i][3] = flight.getLeaveDate();
			tableValues[i][4] = "" + flight.getPrice();
		}

		JTable table = new JTable(tableValues, columnNames);// ����ָ�����������ݵı��
		table.setBounds(0, 0, 584, 319);
		JScrollPane scrollPane = new JScrollPane(table);// ������ʾ���Ĺ������
		scrollPane.setBounds(0, 0, 584, 319);
		jp_all.add(scrollPane);

		jp_all.revalidate();
		jp_all.repaint();

	}

	public void showOne(String flightNum) {
		if (jp_insert != null)
			com.airplane.Function.frame.remove(jp_insert);
		if (jp_delete != null)
			com.airplane.Function.frame.remove(jp_delete);
		if (jp_modify != null)
			com.airplane.Function.frame.remove(jp_modify);
		if (jp_find != null)
			com.airplane.Function.frame.remove(jp_find);
		if (jp_all != null)
			com.airplane.Function.frame.remove(jp_all);

		jp_one = new JPanel();
		jp_one.setBounds(0, 46, 584, 319);
		com.airplane.Function.frame.getContentPane().add(jp_one);
		jp_one.setLayout(null);

		Flight flight = null;
		try {
			flight = DBUtils.findOneFlight(flightNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		jl_flightNum = new JLabel("*  �����ţ�");
		jl_leaveDate = new JLabel("*  �������ڣ�");
		jl_leaveCity = new JLabel("*  �������У�");
		jl_arriveCity = new JLabel("*  ������У�");
		jl_price = new JLabel("* ��Ʊ�۸�");
		jl_flightNum.setBounds(180, 35, 80, 15);
		jl_leaveDate.setBounds(180, 85, 80, 15);
		jl_leaveCity.setBounds(180, 135, 80, 15);
		jl_arriveCity.setBounds(180, 185, 80, 15);
		jl_price.setBounds(180, 235, 80, 15);

		jtf_flightNum = new JTextField();
		jtf_leaveDate = new JTextField();
		jtf_leaveCity = new JTextField();
		jtf_arriveCity = new JTextField();
		jtf_price = new JTextField();
		jtf_flightNum.setBounds(260, 30, 120, 25);
		jtf_leaveDate.setBounds(260, 80, 120, 25);
		jtf_leaveCity.setBounds(260, 130, 120, 25);
		jtf_arriveCity.setBounds(260, 180, 120, 25);
		jtf_price.setBounds(260, 230, 120, 25);
		jtf_flightNum.setColumns(10);
		jtf_leaveDate.setColumns(10);
		jtf_leaveCity.setColumns(10);
		jtf_arriveCity.setColumns(10);
		jtf_price.setColumns(10);
		jtf_flightNum.setText(flight.getFlightNum());
		jtf_leaveDate.setText(flight.getLeaveDate());
		jtf_leaveCity.setText(flight.getLeaveCity());
		jtf_arriveCity.setText(flight.getArriveCity());
		jtf_price.setText(String.valueOf(flight.getPrice()));

		jp_one.add(jl_flightNum);
		jp_one.add(jl_leaveDate);
		jp_one.add(jl_leaveCity);
		jp_one.add(jl_arriveCity);
		jp_one.add(jl_price);
		jp_one.add(jtf_flightNum);
		jp_one.add(jtf_leaveDate);
		jp_one.add(jtf_leaveCity);
		jp_one.add(jtf_arriveCity);
		jp_one.add(jtf_price);

		jp_one.revalidate();
		jp_one.repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "��������":
			insertFight();
			break;
		case "��������":
			deleteFight();
			break;
		case "�޸ĺ���":
			modifyFight();
			break;
		case "���Һ���":
			findFight();
			break;
		case "ȡ��":
			cancel();
			break;
		default:
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == rb_findOne) {
			jLabel.setVisible(true);
			jtf_find.setVisible(true);
		} else if (e.getSource() == rb_findAll) {
			jLabel.setVisible(false);
			jtf_find.setVisible(false);
		}
	}

}
