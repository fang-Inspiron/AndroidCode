package com.airplane;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.thread.GetLoginResult;

public class Login extends JFrame implements ActionListener {

	private static JPanel jp_login;
	private JLabel jLabel, jl_user, jl_pawd;
	private ImageIcon img;
	public static TextField tf_user, tf_pawd;
	private JButton jb_login, jb_register;

	public Login() {
		jp_login = new JPanel(null);
		/*
		 * 设置背景图片 433*299
		 */
		img = new ImageIcon("res/login.png");
		jLabel = new JLabel();
		jLabel.setIcon(img);
		jLabel.setBounds(0, 0, 433, 299);
		/*
		 * 设置用户名及密码标签
		 */
		jl_user = new JLabel("用户名：");
		jl_pawd = new JLabel("密     码：");
		jl_user.setBounds(100, 50, 60, 25);
		jl_pawd.setBounds(100, 110, 60, 25);
		/*
		 * 设置用户名及密码输入框
		 */
		tf_user = new TextField();
		tf_pawd = new TextField();
		tf_user.setBounds(165, 50, 140, 25);
		tf_pawd.setBounds(165, 110, 140, 25);
		tf_pawd.setColumns(6);
		tf_pawd.setEchoChar('*');
		/*
		 * 设置登陆及注册按钮
		 */
		jb_login = new JButton("登陆");
		jb_register = new JButton("注册");
		jb_login.setBounds(130, 180, 60, 27);
		jb_register.setBounds(230, 180, 60, 27);
		jb_login.addActionListener(this);
		jb_register.addActionListener(this);
		/*
		 * 将一些组件添加到JPanel面板里
		 */
		jp_login.add(jl_user);
		jp_login.add(jl_pawd);
		jp_login.add(tf_user);
		jp_login.add(tf_pawd);
		jp_login.add(jb_login);
		jp_login.add(jb_register);
		jp_login.add(jLabel);
		/*
		 * 设置窗体属性
		 */
		this.setLayout(null);
		this.setTitle("航空客运订票系统");// 设置窗体标题
		this.setBounds(450, 200, 435, 300);// 设置初始位置、窗体大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体
		this.setVisible(true);// 显示窗体
		this.setResizable(false);// 禁止用户改变窗体大小
		jp_login.setBounds(0, 0, 433, 299);
		jp_login.setLayout(null);
		jp_login.setVisible(true);
		this.add(jp_login);
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "登陆":
			new GetLoginResult(tf_user.getText(), tf_pawd.getText(), this).start();
			break;
		case "注册":
			this.dispose();
			new Register();
			break;
		default:
			break;
		}
	}

	public static void loginZero() {
		JOptionPane.showMessageDialog(jp_login, "该用户不存在，请注册后再登陆！", "提示框",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginOne() {
		JOptionPane.showMessageDialog(jp_login, "密码不正确！", "提示框",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginTwo() {
		JOptionPane.showMessageDialog(jp_login, "售票员！", "提示框",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginThree() {
		JOptionPane.showMessageDialog(jp_login, "经理！", "提示框",
				JOptionPane.CANCEL_OPTION);
	}

}
