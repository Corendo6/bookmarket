package com.market.page;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.MemberDao;

public class AdminLoginDialog extends JDialog implements ActionListener {

	JTextField idField;
	JPasswordField pwField;
	MemberDao memberDao;
	JButton okButton, cancelBtn;
	public boolean isLogin = false;

	public AdminLoginDialog(JFrame frame, String str, MemberDao memberDao) {
		super(frame, "관리자 로그인", true);
		this.memberDao = memberDao;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 400) / 2, (screenSize.height - 300) / 2);
		setSize(400, 300);
		setLayout(null);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 20, 400, 50);
		add(titlePanel);
		JLabel titleLabel = new JLabel("관리자 로그인");
		MarketFont.getFont(titleLabel);
		titlePanel.add(titleLabel);

		JPanel idPanel = new JPanel();
		idPanel.setBounds(0, 70, 400, 50);
		add(idPanel);
		JLabel idLabel = new JLabel("아 이 디 : ");
		MarketFont.getFont(idLabel);
		idField = new JTextField(10);
		MarketFont.getFont(idField);
		idPanel.add(idLabel);
		idPanel.add(idField);

		JPanel pwPanel = new JPanel();
		pwPanel.setBounds(0, 120, 400, 50);
		add(pwPanel);
		JLabel pwLabel = new JLabel("패스워드 : ");
		MarketFont.getFont(pwLabel);
		pwField = new JPasswordField(10);
		MarketFont.getFont(pwField);
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 170, 400, 50);
		add(buttonPanel);
		JLabel okLabel = new JLabel("확인");
		MarketFont.getFont(okLabel);
		okButton = new JButton();
		okButton.add(okLabel);
		buttonPanel.add(okButton);

		JLabel cancelLabel = new JLabel("취소");
		MarketFont.getFont(cancelLabel);
		cancelBtn = new JButton();
		cancelBtn.add(cancelLabel);
		buttonPanel.add(cancelBtn);

		okButton.addActionListener(this);
		pwField.addActionListener(this);
		cancelBtn.addActionListener(this);
//		okButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
////				AdminVo admin = new AdminVo();
//				String uid = idField.getText().trim().toUpperCase();
//				String upass = pwField.getText().trim();
//				
//				if (memberDao.select(uid, upass) == 1) {
//					isLogin = true;
//					dispose();
//				} else
//					JOptionPane.showMessageDialog(okButton, "관리자 정보가 일치하지 않습니다.");
//			}
//		});
//
//		cancelBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				isLogin = false;
//				dispose();
//			}
//		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == okButton || obj == pwField) {
			String uid = idField.getText().trim().toUpperCase();
			String upass = pwField.getText().trim();
			
			if (memberDao.select(uid, upass) == 1) {
				isLogin = true;
				dispose();
			} else
				JOptionPane.showMessageDialog(okButton, "관리자 정보가 일치하지 않습니다.");
		} else if (obj == cancelBtn) {
			isLogin = false;
			dispose();
		}
	}

}