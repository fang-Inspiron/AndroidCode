package com.airplane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.function.FlightManager;
import com.function.OrderManager;
import com.function.TicketManager;

public class Function implements ActionListener{
	public static  JFrame frame;
	private JButton btn_ticket, btn_order, btn_fight;
	private ImageIcon img;
	private JLabel jLabel;
	
	public Function() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 584, 45);
		panel.setLayout(null);
		/*
		 * 设置背景图片 
		 */
		img = new ImageIcon("res/function.png");
		jLabel = new JLabel();
		jLabel.setIcon(img);
		jLabel.setBounds(0, 0, 584, 45);

		btn_ticket = new JButton("购票管理");
		btn_order = new JButton("订单管理");
		btn_fight = new JButton("航班管理");
		btn_ticket.setBounds(80, 10, 90, 25);
		btn_order.setBounds(250, 10, 90, 25);
		btn_fight.setBounds(420, 10, 90, 25);
		btn_ticket.addActionListener(this);
		btn_order.addActionListener(this);
		btn_fight.addActionListener(this);
		
		// 将各组件添加到JPanel面板中
		panel.add(btn_ticket);
		panel.add(btn_order);
		panel.add(btn_fight);
		panel.add(jLabel);
		frame.setBounds(400, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "购票管理":
//			if(OrderManager.jp_delete != null){
//				frame.remove(OrderManager.jp_delete);
//				frame.repaint();
//			}
			new TicketManager();
			break;
		case "订单管理":
			new OrderManager();
			break;
		case "航班管理":
			new FlightManager();
			break;

		default:
			break;
		}
	}
}
