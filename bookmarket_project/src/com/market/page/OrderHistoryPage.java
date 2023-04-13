package com.market.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.market.commons.MarketFont;
import com.market.dao.OrderDao;
import com.market.main.MainWindow;
import com.market.vo.CartVo;
import com.market.vo.OrderVo;

public class OrderHistoryPage extends JTable {

	OrderDao orderDao;
	OrderVo orderMember;
	JPanel shippingPanel;
	JPanel radioPanel;
	JPanel mPagePanel;
	MainWindow main;

	public OrderHistoryPage(JPanel panel, OrderDao orderDao, MainWindow main, String oid) {
		this.orderDao = orderDao;
		this.mPagePanel = panel;
		this.main = main;
		orderMember = orderDao.select(oid).get(0);

		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		shippingPanel = new JPanel();
		shippingPanel.setBounds(0, 0, 700, 500);
		shippingPanel.setLayout(null);
		panel.add(shippingPanel);

		printBillInfo(orderMember);
	}

	public void printBillInfo(OrderVo orderMember) {

		
		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 30);
		JLabel label01 = new JLabel("--------------------- 주문 정보 -----------------------");
		MarketFont.getFont(label01);
		panel01.add(label01);
		shippingPanel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 30, 500, 30);
		JLabel label02 = new JLabel("고객명 : " + orderMember.getName() + "             연락처 :      " + orderMember.getPhone());
		label02.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label02);
		panel02.add(label02);
		shippingPanel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 60, 500, 30);
		JLabel label03 = new JLabel("배송지 : " + orderMember.getOaddr() + "                 발송일 :       " + orderMember.getOdate());
		label03.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label03);
		panel03.add(label03);
		shippingPanel.add(panel03);

		JPanel printPanel = new JPanel();
		printPanel.setBounds(0, 100, 500, 300);
		printCart(printPanel);
		shippingPanel.add(printPanel);
	}

	public void printCart(JPanel panel) {

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 5);
		JLabel label01 = new JLabel("      장바구니 상품 목록 :");
		MarketFont.getFont(label01);
		panel01.add(label01);
		panel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 20, 500, 5);
		JLabel label02 = new JLabel("---------------------------------------------------------------");
		MarketFont.getFont(label02);
		panel02.add(label02);
		panel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 25, 500, 5);
		JLabel label03 = new JLabel("                        도서 ID           |        수량           |      합계        ");
		MarketFont.getFont(label03);
		panel03.add(label03);
		panel.add(panel03);

		JPanel panel04 = new JPanel();
		panel04.setBounds(0, 30, 500, 5);

		JPanel panel05 = new JPanel(new GridLayout(orderDao.getSize(orderMember.getMid().toUpperCase()), 1));
		ArrayList<OrderVo> orderList = orderDao.select(orderMember.getMid().toUpperCase());
		int sum = 0;
		for (int i = 0; i < orderList.size(); i++) { // 13
			OrderVo item = orderList.get(i);
			panel05.setBounds(50, 25 + (i * 5), 500, 5);
			panel05.setBackground(Color.GRAY);

			JLabel label05 = new JLabel("               " + item.getIsbn() + "                    " + item.getQty()
					+ "                    " + item.getStotal_price());
			MarketFont.getFont(label05);
			panel05.add(label05);
			panel.add(panel05);
			sum += item.getTotal_price();
		}

		JPanel panel06 = new JPanel();
		panel06.setBounds(0, 35 + (orderDao.getSize(orderMember.getMid().toUpperCase()) * 5), 500, 5);
		JLabel label06 = new JLabel("--------------------------------------");
		MarketFont.getFont(label06);
		panel06.add(label06);
		panel.add(panel06);

		JPanel panel07 = new JPanel();
		panel07.setBounds(0, 40 + (orderDao.getSize(orderMember.getMid().toUpperCase()) * 5), 500, 5);
		JLabel label07 = new JLabel("      주문 총금액 : " + sum + " 원");
		MarketFont.getFont(label07);
		panel07.add(label07);
		panel.add(panel07);

		/** 주문 확정 버튼 **/
//		JPanel panel08 = new JPanel();
//		panel08.setBounds(0, 40 + (orderDao.getSize(orderMember.getMid().toUpperCase()) * 5), 500, 5);
//		JButton btnOrderFinish = new JButton("주문 확정");
//		MarketFont.getFont(btnOrderFinish);
//		panel08.add(btnOrderFinish);
//		panel.add(panel08);

//		btnOrderFinish.addActionListener(e -> {
//			int select = JOptionPane.showConfirmDialog(null, "정말로 주문을 확정하겠습니까?");
//			if (select == 0) {
//				// 오더 테이블에 저장할 데이터 생성
//				// oid, odate, qty리스트, isbn 리스트, mid, name, phone, addr
//				// qty리스트, isbn 리스트 => CartDao에서 생성 후 orderVo 타입으로 리턴
//				OrderVo orderVo = cartDao.getOrderVo(orderMember.getMid().toUpperCase());
//				// oid, odate => UUID, Calendar 클래스를 이용해 생성
//				UUID uuid = UUID.randomUUID();
//				Calendar cal = Calendar.getInstance();
//				String oid = uuid.toString();
//				String odate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE);
//				orderVo.setOid(oid);
//				orderVo.setOdate(odate);
//				// mid, name, phone, addr => orderMember 객체에서 가져옴
//				orderVo.setMid(orderMember.getMid());
//				orderVo.setName(orderMember.getName());
//				orderVo.setPhone(orderMember.getPhone());
//				orderVo.setOaddr(orderMember.getAddr());
//				
//				OrderDao orderDao = new OrderDao();
//				int result = orderDao.insertPrepared(orderVo);
//				if (result >= 1) {
//					JOptionPane.showMessageDialog(null, "주문이 완료되었습니다");
//					cartDao.deleteAll(orderMember.getMid().toUpperCase());
//				}
//			}
//		});

	}
}
