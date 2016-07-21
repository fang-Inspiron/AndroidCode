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
		 * ���ñ���ͼƬ 433*299
		 */
		img = new ImageIcon("res/login.png");
		jLabel = new JLabel();
		jLabel.setIcon(img);
		jLabel.setBounds(0, 0, 433, 299);
		/*
		 * �����û����������ǩ
		 */
		jl_user = new JLabel("�û�����");
		jl_pawd = new JLabel("��     �룺");
		jl_user.setBounds(100, 50, 60, 25);
		jl_pawd.setBounds(100, 110, 60, 25);
		/*
		 * �����û��������������
		 */
		tf_user = new TextField();
		tf_pawd = new TextField();
		tf_user.setBounds(165, 50, 140, 25);
		tf_pawd.setBounds(165, 110, 140, 25);
		tf_pawd.setColumns(6);
		tf_pawd.setEchoChar('*');
		/*
		 * ���õ�½��ע�ᰴť
		 */
		jb_login = new JButton("��½");
		jb_register = new JButton("ע��");
		jb_login.setBounds(130, 180, 60, 27);
		jb_register.setBounds(230, 180, 60, 27);
		jb_login.addActionListener(this);
		jb_register.addActionListener(this);
		/*
		 * ��һЩ�����ӵ�JPanel�����
		 */
		jp_login.add(jl_user);
		jp_login.add(jl_pawd);
		jp_login.add(tf_user);
		jp_login.add(tf_pawd);
		jp_login.add(jb_login);
		jp_login.add(jb_register);
		jp_login.add(jLabel);
		/*
		 * ���ô�������
		 */
		this.setLayout(null);
		this.setTitle("���տ��˶�Ʊϵͳ");// ���ô������
		this.setBounds(450, 200, 435, 300);// ���ó�ʼλ�á������С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رմ���
		this.setVisible(true);// ��ʾ����
		this.setResizable(false);// ��ֹ�û��ı䴰���С
		jp_login.setBounds(0, 0, 433, 299);
		jp_login.setLayout(null);
		jp_login.setVisible(true);
		this.add(jp_login);
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "��½":
			new GetLoginResult(tf_user.getText(), tf_pawd.getText(), this).start();
			break;
		case "ע��":
			this.dispose();
			new Register();
			break;
		default:
			break;
		}
	}

	public static void loginZero() {
		JOptionPane.showMessageDialog(jp_login, "���û������ڣ���ע����ٵ�½��", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginOne() {
		JOptionPane.showMessageDialog(jp_login, "���벻��ȷ��", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginTwo() {
		JOptionPane.showMessageDialog(jp_login, "��ƱԱ��", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
	}

	public static void loginThree() {
		JOptionPane.showMessageDialog(jp_login, "����", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
	}

}
