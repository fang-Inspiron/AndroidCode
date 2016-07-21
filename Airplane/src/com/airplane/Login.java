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

import com.bean.User;
import com.utils.DBUtils;

public class Login extends JFrame implements ActionListener, ItemListener {

	private JPanel jp_login, jp_register;
	private JLabel jLabel, jLabel2, jl_user, jl_pawd, jl_register_user,
			jl_register_pawd, jl_register_pawd2, jl_register_permission;
	private ImageIcon img;
	public static TextField tf_user;
	private TextField tf_pawd, tf_register_user, tf_register_pawd,
			tf_register_pawd2;
	private JButton jb_login, jb_register, jb_ensure, jb_cancel;
	private ButtonGroup bGroup;
	private JRadioButton jrb_manager, jrb_employee;
	private int permission = -1;

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
		tf_user.setColumns(8);
		tf_pawd.setColumns(8);
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

	public void register() {
		if (jp_login != null)
			this.remove(jp_login);

		jp_register = new JPanel(null);

		/*
		 * 设置背景图片 441*395
		 */
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
		/*
		 * 设置用户名及密码输入框
		 */
		tf_register_user = new TextField();
		tf_register_pawd = new TextField();
		tf_register_pawd2 = new TextField();
		tf_register_user.setBounds(180, 50, 140, 25);
		tf_register_pawd.setBounds(180, 100, 140, 25);
		tf_register_pawd2.setBounds(180, 150, 140, 25);
		tf_register_user.setColumns(8);
		tf_register_pawd.setColumns(8);
		tf_register_pawd2.setColumns(8);
		tf_register_pawd.setEchoChar('*');
		tf_register_pawd2.setEchoChar('*');
		/*
		 * 定义单选按钮及按钮组控件
		 */
		bGroup = new ButtonGroup();
		jrb_manager = new JRadioButton("经理");
		jrb_employee = new JRadioButton("员工");
		jrb_manager.setBounds(180, 230, 55, 25);
		jrb_employee.setBounds(265, 230, 55, 25);
		jrb_manager.addItemListener(this);
		jrb_employee.addItemListener(this);
		bGroup.add(jrb_manager);
		bGroup.add(jrb_employee);
		/*
		 * 设置确定、取消按钮
		 */
		jb_ensure = new JButton("确定");
		jb_cancel = new JButton("取消");
		jb_ensure.setBounds(130, 285, 60, 27);
		jb_cancel.setBounds(250, 285, 60, 27);
		jb_ensure.addActionListener(this);
		jb_cancel.addActionListener(this);

		/*
		 * 将一些组件添加到JPanel面板里
		 */
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
		/*
		 * 设置窗体属性
		 */
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

		jp_register.revalidate();
		jp_register.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "登陆":
			User user = null;
			try {
				user = DBUtils.findOneUser(tf_user.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (user == null) {
				JOptionPane.showMessageDialog(jp_login, "该用户不存在，请注册后再登陆！",
						"提示框", JOptionPane.CANCEL_OPTION);
			} else if (!(tf_pawd.getText().equals(user.getPassword()))) {
				JOptionPane.showMessageDialog(jp_login, "密码不正确！", "提示框",
						JOptionPane.CANCEL_OPTION);
			} else {
				this.dispose();
				Function window = new Function();
				window.frame.setVisible(true);
			}
			break;
		case "注册":
			register();
			break;
		case "确定":
			String userName = tf_register_user.getText();
			String pawd1 = tf_register_pawd.getText();
			String pawd2 = tf_register_pawd2.getText();
			if (userName == null || pawd1 == null || pawd2 == null
					|| permission == -1) {
				JOptionPane.showMessageDialog(jp_register, "请完成注册信息！", "提示框",
						JOptionPane.CANCEL_OPTION);
				break;
			} else if (!pawd1.equals(pawd2)) {
				JOptionPane.showMessageDialog(jp_register, "两个密码不匹配！", "提示框",
						JOptionPane.CANCEL_OPTION);
				break;
			} else {
				JOptionPane.showMessageDialog(jp_register, "注册成功，请登录！", "提示框",
						JOptionPane.CANCEL_OPTION);
				User user2 = new User(userName, pawd1, permission);
				try {
					if (DBUtils.insert(user2)) {
						this.dispose();
						new Login();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			}
		case "取消":
			this.dispose();
			break;
		default:
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == jrb_manager) {
			permission = 1;
		} else if (e.getSource() == jrb_employee) {
			permission = 0;
		}
	}

}
