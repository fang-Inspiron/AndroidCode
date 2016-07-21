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
		jrb_manager = new JRadioButton("����");
		jrb_employee = new JRadioButton("Ա��");
		jrb_manager.setBounds(180, 230, 55, 25);
		jrb_employee.setBounds(265, 230, 55, 25);
		jrb_manager.setOpaque(false);
		jrb_employee.setOpaque(false);
		jrb_manager.addItemListener(this);
		jrb_employee.addItemListener(this);
		bGroup.add(jrb_manager);
		bGroup.add(jrb_employee);

		jb_ensure = new JButton("ȷ��");
		jb_cancel = new JButton("ȡ��");
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
		this.setTitle("Airplane System");// ���ô������
		this.setBounds(440, 200, 445, 395);// ���ó�ʼλ�á������С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �رմ���
		this.setVisible(true);// ��ʾ����
		this.setResizable(false);// ��ֹ�û��ı䴰���С
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
		case "ȷ��":
			System.out.println(tf_register_pawd.getText());
			new GetRegisterResult(tf_register_user.getText(),
					tf_register_pawd.getText(), tf_register_pawd2.getText(),
					permission, this).start();
		case "ȡ��":
			this.dispose();
			break;
		}
	}

	public void registerZero() {
		JOptionPane.showMessageDialog(this, "���û����Ѵ��ڣ�������ע�ᣡ", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerOne() {
		JOptionPane.showMessageDialog(this, "�����ע����Ϣ��", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerTwo() {
		JOptionPane.showMessageDialog(this, "�������벻ƥ�䣡", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

	public void registerThree() {
		JOptionPane.showMessageDialog(this, "ע��ɹ���", "��ʾ��",
				JOptionPane.CANCEL_OPTION);
		this.setVisible(true);
	}

}
