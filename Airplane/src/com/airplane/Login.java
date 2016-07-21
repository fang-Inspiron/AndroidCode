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
		tf_user.setColumns(8);
		tf_pawd.setColumns(8);
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

	public void register() {
		if (jp_login != null)
			this.remove(jp_login);

		jp_register = new JPanel(null);

		/*
		 * ���ñ���ͼƬ 441*395
		 */
		img = new ImageIcon("res/register.png");
		jLabel2 = new JLabel();
		jLabel2.setIcon(img);
		jLabel2.setBounds(0, 0, 441, 395);
		/*
		 * �����û��������뼰Ȩ�ޱ�ǩ
		 */
		jl_register_user = new JLabel("��  ��  ����");
		jl_register_pawd = new JLabel("��        �룺");
		jl_register_pawd2 = new JLabel("ȷ�����룺");
		jl_register_permission = new JLabel("Ȩ        �ޣ�");
		jl_register_user.setBounds(100, 50, 70, 25);
		jl_register_pawd.setBounds(100, 100, 70, 25);
		jl_register_pawd2.setBounds(100, 150, 70, 25);
		jl_register_permission.setBounds(100, 230, 70, 25);
		/*
		 * �����û��������������
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
		 * ���嵥ѡ��ť����ť��ؼ�
		 */
		bGroup = new ButtonGroup();
		jrb_manager = new JRadioButton("����");
		jrb_employee = new JRadioButton("Ա��");
		jrb_manager.setBounds(180, 230, 55, 25);
		jrb_employee.setBounds(265, 230, 55, 25);
		jrb_manager.addItemListener(this);
		jrb_employee.addItemListener(this);
		bGroup.add(jrb_manager);
		bGroup.add(jrb_employee);
		/*
		 * ����ȷ����ȡ����ť
		 */
		jb_ensure = new JButton("ȷ��");
		jb_cancel = new JButton("ȡ��");
		jb_ensure.setBounds(130, 285, 60, 27);
		jb_cancel.setBounds(250, 285, 60, 27);
		jb_ensure.addActionListener(this);
		jb_cancel.addActionListener(this);

		/*
		 * ��һЩ�����ӵ�JPanel�����
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
		 * ���ô�������
		 */
		this.setLayout(null);
		this.setTitle("Airplane System");// ���ô������
		this.setBounds(440, 200, 445, 395);// ���ó�ʼλ�á������С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رմ���
		this.setVisible(true);// ��ʾ����
		this.setResizable(false);// ��ֹ�û��ı䴰���С
		jp_register.setBounds(0, 0, 441, 395);
		jp_register.setLayout(null);
		jp_register.setVisible(true);
		this.add(jp_register);

		jp_register.revalidate();
		jp_register.repaint();
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "��½":
			User user = null;
			try {
				user = DBUtils.findOneUser(tf_user.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (user == null) {
				JOptionPane.showMessageDialog(jp_login, "���û������ڣ���ע����ٵ�½��",
						"��ʾ��", JOptionPane.CANCEL_OPTION);
			} else if (!(tf_pawd.getText().equals(user.getPassword()))) {
				JOptionPane.showMessageDialog(jp_login, "���벻��ȷ��", "��ʾ��",
						JOptionPane.CANCEL_OPTION);
			} else {
				this.dispose();
				Function window = new Function();
				window.frame.setVisible(true);
			}
			break;
		case "ע��":
			register();
			break;
		case "ȷ��":
			String userName = tf_register_user.getText();
			String pawd1 = tf_register_pawd.getText();
			String pawd2 = tf_register_pawd2.getText();
			if (userName == null || pawd1 == null || pawd2 == null
					|| permission == -1) {
				JOptionPane.showMessageDialog(jp_register, "�����ע����Ϣ��", "��ʾ��",
						JOptionPane.CANCEL_OPTION);
				break;
			} else if (!pawd1.equals(pawd2)) {
				JOptionPane.showMessageDialog(jp_register, "�������벻ƥ�䣡", "��ʾ��",
						JOptionPane.CANCEL_OPTION);
				break;
			} else {
				JOptionPane.showMessageDialog(jp_register, "ע��ɹ������¼��", "��ʾ��",
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
		case "ȡ��":
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
