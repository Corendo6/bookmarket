package com.market.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.vo.MemberVo;

public class GuestWindow extends JFrame implements ActionListener {
	MemberDao memberDao;
	JTextField nameField;
	JPasswordField phoneField;
	JButton enterButton, resetButton, exitButton;

	public GuestWindow(String title, int x, int y, int width, int height) {
		memberDao = new MemberDao();
		initContainer(title, x, y, width, height);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}

	private void initContainer(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 1000) / 2, (screenSize.height - 750) / 2);

		JPanel userPanel = new JPanel();
		userPanel.setBounds(0, 100, 1000, 256);

		ImageIcon imageIcon = new ImageIcon("./images/user.png");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
		JLabel userLabel = new JLabel(imageIcon);
		userPanel.add(userLabel);
		add(userPanel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBounds(0, 350, 1000, 50);
		add(titlePanel);

		JLabel titleLabel = new JLabel("-- 고객 정보를 입력해주세요 --");
		MarketFont.getFont(titleLabel);
		titleLabel.setForeground(Color.BLUE);
		titlePanel.add(titleLabel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 400, 1000, 50);
		add(namePanel);

		JLabel nameLabel = new JLabel("아 이 디 : ");
		MarketFont.getFont(nameLabel);
		namePanel.add(nameLabel);

		nameField = new JTextField(10);
		MarketFont.getFont(nameField);
		namePanel.add(nameField);

		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(0, 450, 1000, 50);
		add(phonePanel);

		JLabel phoneLabel = new JLabel("패스워드: ");
		MarketFont.getFont(phoneLabel);
		phonePanel.add(phoneLabel);

		phoneField = new JPasswordField(10);
		MarketFont.getFont(phoneField);
		phonePanel.add(phoneField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 500, 1000, 100);
		add(buttonPanel);

		JLabel buttonLabel1 = new JLabel("  로그인 ", new ImageIcon("images/shop.png"), JLabel.LEFT);
		MarketFont.getFont(buttonLabel1);
		enterButton = new JButton();
		enterButton.add(buttonLabel1);
		buttonPanel.add(enterButton);

		JLabel buttonLabel2 = new JLabel("  다시쓰기 ", new ImageIcon("images/shop.png"), JLabel.LEFT);
		MarketFont.getFont(buttonLabel2);
		resetButton = new JButton();
		resetButton.add(buttonLabel2);
		buttonPanel.add(resetButton);

		JLabel buttonLabel3 = new JLabel("  쇼핑종료 ", new ImageIcon("images/shop.png"), JLabel.LEFT);
		MarketFont.getFont(buttonLabel3);
		exitButton = new JButton();
		exitButton.add(buttonLabel3);
		buttonPanel.add(exitButton);

		enterButton.addActionListener(this);
		resetButton.addActionListener(this);
		exitButton.addActionListener(this);
		phoneField.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == phoneField || obj == enterButton) {
			JLabel message = new JLabel("사용자 정보 에러");
			MarketFont.getFont(message);
			login();
		} else if (obj == resetButton) {
			nameField.setText("");
			phoneField.setText("");
			nameField.requestFocus();
		} else if (obj == exitButton) {
			memberDao.close();
			System.exit(0);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void login() {
		if (nameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
			nameField.requestFocus();
		} else if (phoneField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "패스워드를 입력해주세요");
			phoneField.requestFocus();
		} else {
			String mid = nameField.getText().trim();
			String pass = phoneField.getText().trim();
			if (memberDao.select(mid, pass) == 1) {
				MemberVo member = new MemberVo();
				member.setMid(nameField.getText().trim());
				member.setPass(phoneField.getText().trim());
				setVisible(false);
				Map param = new HashMap();
				param.put("title", "온라인 서점");
				param.put("x", 0);
				param.put("y", 0);
				param.put("width", 1000);
				param.put("height", 750);
				param.put("member", member);
				param.put("memberDao", memberDao);

				new MainWindow(param);
			} else {
				String msg = "아이디 또는 패스워드가 다릅니다.\n다시 입력해주세요";
				JOptionPane.showMessageDialog(null, msg);
				nameField.setText("");
				phoneField.setText("");
				nameField.requestFocus();
			}
		}
	}

}