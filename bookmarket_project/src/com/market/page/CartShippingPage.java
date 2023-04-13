package com.market.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.main.MainWindow;
import com.market.vo.MemberVo;

public class CartShippingPage extends JPanel {
	MemberVo orderMember;
	MemberDao memberDao;
	CartDao cartDao;
	JPanel shippingPanel;
	JPanel radioPanel;
	MainWindow main;

	public CartShippingPage(JPanel panel, Map<String, DBConn> daoList, MainWindow main) {
		this.memberDao = (MemberDao) daoList.get("memberDao");
		this.cartDao = (CartDao) daoList.get("cartDao");
		orderMember = new MemberVo();
		this.main = main;

		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		radioPanel = new JPanel();
		radioPanel.setBounds(300, 0, 700, 50);
		radioPanel.setLayout(new FlowLayout());
		add(radioPanel);
		JLabel radioLabel = new JLabel("배송받을 분의 고객정보와 동일합니까?");
		MarketFont.getFont(radioLabel);
		ButtonGroup group = new ButtonGroup();
		JRadioButton radioOk = new JRadioButton("예");
		MarketFont.getFont(radioOk);
		JRadioButton radioNo = new JRadioButton("아니오");
		MarketFont.getFont(radioNo);
		group.add(radioOk);
		group.add(radioNo);
		radioPanel.add(radioLabel);
		radioPanel.add(radioOk);
		radioPanel.add(radioNo);

		shippingPanel = new JPanel();
		shippingPanel.setBounds(200, 50, 700, 500);
		shippingPanel.setLayout(null);
		add(shippingPanel);

		radioOk.setSelected(true);
		radioNo.setSelected(false);
		UserShippingInfo("yes");

		radioOk.addActionListener(e -> {

			if (radioOk.isSelected()) {
				shippingPanel.removeAll();
				UserShippingInfo("yes");
				shippingPanel.revalidate();
				shippingPanel.repaint();
				radioNo.setSelected(false);
			}
		});

		radioNo.addActionListener(e -> {

			if (radioNo.isSelected()) {
				shippingPanel.removeAll();
				UserShippingInfo("no");
				shippingPanel.revalidate();
				shippingPanel.repaint();
				radioOk.setSelected(false);
			}
		});
	}

	public void UserShippingInfo(String select) {

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 100, 700, 50);
		JLabel nameLabel = new JLabel("고객명 : ");
		JTextField nameLabel2 = new JTextField(10);
		JPanel phonePanel = new JPanel();
		JLabel phoneLabel = new JLabel("연락처 : ");
		JTextField phoneLabel2 = new JTextField(10);
		JPanel addressPanel = new JPanel();
		JLabel label = new JLabel("배송지 : ");
		JTextField addressText = new JTextField(15);
		

		if (select == "yes") {
			// 로그인한 고객 정보 가져오기
			orderMember = memberDao.select(MainWindow.member.getMid().toUpperCase());
			
			MarketFont.getFont(nameLabel);
			namePanel.add(nameLabel);

			MarketFont.getFont(nameLabel2);
			nameLabel2.setBackground(Color.LIGHT_GRAY);
			nameLabel2.setText(orderMember.getName());
			nameLabel2.setEditable(false);
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.setBounds(0, 150, 700, 50);
			MarketFont.getFont(phoneLabel);
			phonePanel.add(phoneLabel);

			MarketFont.getFont(phoneLabel2);
			phoneLabel2.setBackground(Color.LIGHT_GRAY);
			phoneLabel2.setText(orderMember.getPhone());
			phoneLabel2.setEditable(false);
			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);
			
			addressPanel.setBounds(0, 200, 700, 50);
			MarketFont.getFont(label);
			addressPanel.add(label);
			
			MarketFont.getFont(addressText);
			addressText.setBackground(Color.LIGHT_GRAY);
			addressText.setText(orderMember.getAddr());
			addressText.setEditable(false);
			addressPanel.add(addressText);
			shippingPanel.add(addressPanel);
			
		} else {
			MarketFont.getFont(nameLabel);
			namePanel.add(nameLabel);

			MarketFont.getFont(nameLabel2);
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.setBounds(0, 150, 700, 50);
			MarketFont.getFont(phoneLabel);
			phonePanel.add(phoneLabel);

			MarketFont.getFont(phoneLabel2);
			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);
			
			addressPanel.setBounds(0, 200, 700, 50);
			MarketFont.getFont(label);
			addressPanel.add(label);

			MarketFont.getFont(addressText);
			addressPanel.add(addressText);
			shippingPanel.add(addressPanel);
		}

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 300, 700, 100);

		JLabel buttonLabel = new JLabel("주문완료");
		MarketFont.getFont(buttonLabel);
		JButton orderButton = new JButton();
		orderButton.add(buttonLabel);
		buttonPanel.add(orderButton);
		shippingPanel.add(buttonPanel);

		orderButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				orderMember.setName(nameLabel2.getText());
				orderMember.setPhone(phoneLabel2.getText());
				orderMember.setAddr(addressText.getText());
//				System.out.println(name + "," + phone + "," + address);

//				oUser.setName(name);
//				oUser.setPhoneNumber(phone);
//				oUser.setAdress(address);

				radioPanel.removeAll();

				radioPanel.revalidate();

				radioPanel.repaint();
				shippingPanel.removeAll();

				shippingPanel.add(new CartOrderBillPage(shippingPanel, orderMember, main, cartDao));

				shippingPanel.revalidate();
				shippingPanel.repaint();

			}
		});
	}

}