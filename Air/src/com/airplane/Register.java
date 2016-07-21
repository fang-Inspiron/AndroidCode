package com.airplane;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.thread.GetRegisterResult;

public class Register extends JFrame implements ActionListener, ItemListener {

	private static JPanel jp_register;
	private JLabel jLabel2, jl_register_user, jl_register_pawd,
			jl_register_pawd2, jl_register_permission;
	private ImageIcon img;
	private TextField tf_register_user, tf_register_pawd, tf_register_pawd2;
	private JButton jb_ensure, jb_cancel;
	private ButtonGroup bGroup;
	private JRadioButton jrb_manager, jrb_employee;
	private int permission = -1;

	public Register() {
		jp_register = new JPanel(null);
		
		img = new ImageIcon("res/register.png");
		jLabel2 = new JLabel();
		jLabel2.setIcon(img);
		jLabel2.setBounds(0, 0, 441, 395);
		/*
		 * 设置用户名、密码及权限标签
		 */
		jl_register_user = new JLabel("用  户  名：");
		jl_register_pawd = new JLabel("密        码：");
		jl_register_pawd2 = new JLabel("确认密码：");
		jl_register_permission = new JLabel("权        限：");
		jl_register_user.setBounds(100, 50, 70, 25);
		jl_register_pawd.setBounds(100, 100, 70, 25);
		jl_register_pawd2.setBounds(100, 150, 70, 25);
		jl_register_permission.setBounds(100, 230, 70, 25);

		tf_register_user = new TextField();
		tf_register_pawd = new TextField();
		tf_register_pawd2 = new TextField();
		tf_register_user.setBounds(180, 50, 140, 25);
		tf_register_pawd.setBounds(180, 100, 140, 25);
		tf_register_pawd2.setBounds(180, 150, 140, 25);
		tf_register_pawd.setColumns(6);
		tf_register_pawd2.setColumns(6);
		tf_register_pawd.setEchoChar('*');
		tf_register_pawd2.setEchoChar('*');

		bGroup = new ButtonGroup();
		jrb_manager = new JRadioButton("经理");
		jrb_employee = new JRadioButton("员工");
		jrb_manager.setBounds(180, 230, 55, 25);
		jrb_employee.setBounds(265, 230, 55, 25);
		jrb_manager.setOpaque(false);
		jrb_employee.setOpaque(false);
		jrb_manager.addItemListener(this);
		jrb_employee.addItemListener(this);
		bGroup.add(jrb_manager);
		bGroup.add(jrb_employee);

		jb_ensure = new JButton("确定");
		jb_cancel = new JButton("取消");
		jb_ensure.setBounds(130, 285, 60, 27);
		jb_cancel.setBounds(250, 285, 60, 27);
		jb_ensure.addActionListener(this);
		jb_cancel.addActionListener(this);

		jp_register.add(jl_register_user);
		jp_register.add(jl_register_pawd);
		jp_register.add(jl_register_pawd2);
		jp_register.add(tf_register_user);
		jp_register.add(tf_register_pawd);
		jp_register.add(tf_register_pawd2);
		jp_register.add(jl_register_permission);
		jp_register.add(jrb_manager);
		jp_register.add(jrb_employee);
		jp_register.add(jb_ensure);
		jp_register.add(jb_cancel);
		jp_register.add(jLabel2);

		this.setLayout(null);
		this.setTitle("Airplane System");// 设置窗体标题
		this.setBounds(440, 200, 445, 395);// 设置初始位置、窗体大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体
		this.setVisible(true);// 显示窗体
		this.setResizable(false);// 禁止用户改变窗体大小
		jp_register.setBounds(0, 0, 441, 395);
		jp_register.setLayout(null);
		jp_register.setVisible(true);
		this.add(jp_register);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == jrb_manager) {
			permission = 1;
		} else if (e.getSource() == jrb_employee) {
			permission = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "确定":
			System.out.println(tf_register_pawd.getText());
			new GetRegisterResult(tf_register_user.getText(),
					tf_register_pawd.getText(), tf_register_pawd2.getText(),
					permission, this).start();
		case "取消":
			this.dispose();
			break;
		}
	}

	public void registerZero() {
		JOptionPane.showMessageDialog(this, "该用户名已存在，请重新注册！", "提示框",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerOne() {
		JOptionPane.showMessageDialog(this, "请完成注册信息！", "提示框",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerTwo() {
		JOptionPane.showMessageDialog(this, "两个密码不匹配！", "提示框",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerThree() {
		JOptionPane.showMessageDialog(this, "注册成功！", "提示框",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

}
