package com.market.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.dao.OrderDao;
import com.market.page.AdminLoginDialog;
import com.market.page.AdminPage;
import com.market.page.CartAddItemPage;
import com.market.page.CartItemListPage;
import com.market.page.CartShippingPage;
import com.market.page.GuestInfoPage;
import com.market.page.HistoryListPage;
import com.market.page.OrderHistoryPage;
import com.market.vo.MemberVo;

public class MainWindow extends JFrame {
	static JPanel mMenuPanel, mPagePanel;
	public static MemberVo member;
	MemberDao memberDao;
	BookDao bookDao;
	CartDao cartDao;
	OrderDao orderDao;
	MainWindow main = this;
	Map<String, DBConn> daoList = new HashMap<String, DBConn>();

	public MainWindow(Map param) {
		bookDao = new BookDao();
		cartDao = new CartDao();
		orderDao = new OrderDao();
		this.member = (MemberVo) param.get("member");
		this.memberDao = (MemberDao) param.get("memberDao");
		daoList.put("memberDao", memberDao);
		daoList.put("cartDao", cartDao);
		daoList.put("bookDao", bookDao);
		daoList.put("orderDao", orderDao);

		String title = (String)param.get("title");
		int x = (int)param.get("x");
		int y = (int)param.get("y");
		int width = (int)param.get("width");
		int height = (int)param.get("height");
		initContainer(title, x, y, width, height);

		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./images/shop.png").getImage());
	}
	
	public MainWindow(String title, int x, int y, int width, int height, MemberVo member) {
		this.member = member;
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

		mMenuPanel = new JPanel();
		mMenuPanel.setBounds(0, 20, width, 130);
		menuIntroduction();
		add(mMenuPanel);

		mPagePanel = new JPanel();
		mPagePanel.setBounds(0, 150, width, height);
		add(mPagePanel);
		
		//장바구니 항목 추가 화면 디폴트로 출력	 
		mPagePanel.removeAll();
		mPagePanel.add(new CartAddItemPage(mPagePanel, daoList));
		mPagePanel.revalidate();
		mPagePanel.repaint();

	}

	private void menuIntroduction() {

		JButton bt1 = new JButton("고객 정보 확인", new ImageIcon("./images/1.png"));
		bt1.setBounds(0, 0, 100, 50);
		MarketFont.getFont(bt1);
		mMenuPanel.add(bt1);


		bt1.addActionListener(e -> {
			mPagePanel.removeAll();
			mPagePanel.add(new GuestInfoPage(mPagePanel, member, memberDao));
			mPagePanel.revalidate();
			mPagePanel.repaint();
		});

		JButton bt2 = new JButton("장바구니 리스트", new ImageIcon("./images/2.png"));
		bt2.setBounds(0, 0, 100, 30);
		MarketFont.getFont(bt2);
		mMenuPanel.add(bt2);

		bt2.addActionListener(e -> {

				if (cartDao.getSize(member.getMid()) == 0)
					JOptionPane.showMessageDialog(bt2, "장바구니가 비어 있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {
					mPagePanel.removeAll();
					mPagePanel.add( new CartItemListPage(mPagePanel, daoList));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
		});

//		JButton bt3 = new JButton("장바구니 전체 삭제", new ImageIcon("./images/3.png"));
//		bt3.setBounds(0, 0, 100, 30);
//		MarketFont.getFont(bt3);
//		mMenuPanel.add(bt3);

//		bt3.addActionListener(e -> {
//
//			if (cartDao.getSize(member.getMid().toUpperCase()) == 0) {
//				JOptionPane.showMessageDialog(bt3, "장바구니가 비어 있습니다", "message", JOptionPane.ERROR_MESSAGE);
//			}else {
//				mPagePanel.removeAll();
//				menuCartClear(bt3);
//				mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//				mPagePanel.revalidate();
//				mPagePanel.repaint();
//			}
//		});

		/** 장바구니 항목 추가하기 **/
		JButton bt4 = new JButton("장바구니 항목 추가하기", new ImageIcon("./images/3.png"));
		MarketFont.getFont(bt4);
		mMenuPanel.add(bt4);
		bt4.addActionListener(e -> {
				mPagePanel.removeAll();
				mPagePanel.add(new CartAddItemPage(mPagePanel, daoList));
				mPagePanel.revalidate();
				mPagePanel.repaint();
		});

		
//		JButton bt5 = new JButton("장바구니 항목수량 줄이기", new ImageIcon("./images/5.png"));
//		MarketFont.getFont(bt5); 
//		mMenuPanel.add(bt5);
//		
//		bt5.addActionListener(e -> {
//
//			if (cm.getSize() == 0) {
//				JOptionPane.showMessageDialog(bt5, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
//			}else {
//				mPagePanel.removeAll();
//				CartItemListPage cartList = new CartItemListPage(mPagePanel, cm, daoList);
//				if(cartList.mSelectRow == -1) {
//					JOptionPane.showMessageDialog(bt5, "수량을 차감할 항목을 선택해주세요");
//				}else {
//					cartList.mSelectRow = -1;
//				}
//			}
//
//			mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//			mPagePanel.revalidate();
//			mPagePanel.repaint();
//		});
		
		/** 장바구니 항목 삭제하기 **/
//		JButton bt6 = new JButton("장바구니 항목 삭제하기", new ImageIcon("./images/6.png"));
//		MarketFont.getFont(bt6);
//		mMenuPanel.add(bt6);

//		bt6.addActionListener(e -> {
//
//			if (cartDao.getSize(member.getMid().toUpperCase()) == 0) {
//				JOptionPane.showMessageDialog(bt3, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
//			}else {
//
//				mPagePanel.removeAll();
//				CartItemListPage cartList = new CartItemListPage(mPagePanel, cm, daoList);
//				if(cartList.mSelectRow == -1) {
//					JOptionPane.showMessageDialog(bt6, "삭제할 항목을 선택해주세요");
//				}else {
//					ArrayList<CartVo> cartVo = cartDao.select(member.getMid().toUpperCase());
//					
//					cartDao.delete(cartVo.get(cartList.mSelectRow).getCid());
//					cartList.mSelectRow = -1;
//				}
//			}
//			mPagePanel.add(new CartItemListPage(mPagePanel, cm, daoList));
//
//			mPagePanel.revalidate();
//			mPagePanel.repaint();
//		});

		/** 주문하기 **/
		JButton bt7 = new JButton("주문하기", new ImageIcon("./images/4.png"));
		MarketFont.getFont(bt7);
		mMenuPanel.add(bt7);

		bt7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cartDao.getSize(member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(bt7, "장바구니가 비어있습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {

					mPagePanel.removeAll();
					mPagePanel.add(new CartShippingPage(mPagePanel, daoList, main));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
			}
		});
		
		/** 주문내역 출력 **/
		JButton bt10 = new JButton("주문내역", new ImageIcon("./images/5.png"));
		MarketFont.getFont(bt10);
		mMenuPanel.add(bt10);

		bt10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (orderDao.getSize(member.getMid().toUpperCase()) == 0)
					JOptionPane.showMessageDialog(bt10, "주문 내역이 없습니다", "message", JOptionPane.ERROR_MESSAGE);
				else {

					mPagePanel.removeAll();
					mPagePanel.add(new HistoryListPage(mPagePanel, daoList, main));
					mPagePanel.revalidate();
					mPagePanel.repaint();
				}
			}
		});
		

		JButton bt8 = new JButton("로그아웃", new ImageIcon("./images/6.png"));
		MarketFont.getFont(bt8);
		mMenuPanel.add(bt8);

		bt8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int select = JOptionPane.showConfirmDialog(bt8, "로그아웃 하시겠습니까? ");

				if (select == 0) {
//					cartDao.close();
//					System.exit(1);
					setVisible(false);
					new GuestWindow("온라인 서점", 0, 0, 1000, 750);
				}
			}
		});

		if (member.getMid().equalsIgnoreCase("ADMIN1234")) {
			JButton bt9 = new JButton("관리자", new ImageIcon("./images/7.png"));
			MarketFont.getFont(bt9);
			mMenuPanel.add(bt9);
	
			bt9.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminLoginDialog adminDialog;
					JFrame frame = new JFrame();
					adminDialog = new AdminLoginDialog(frame, "관리자 화면", memberDao);
					
					adminDialog.setVisible(true);
					if (adminDialog.isLogin) {
						mPagePanel.removeAll();
						mPagePanel.add(new AdminPage(mPagePanel, bookDao));
						mPagePanel.revalidate();
						mPagePanel.repaint();
					}
				}
			});
		}
		
	}

//	private void menuCartClear(JButton button) {
//
//		if (cartDao.getSize(member.getMid().toUpperCase()) == 0)
//			JOptionPane.showMessageDialog(button, "장바구니가 비어 있습니다");
//		else {
//
//			int select = JOptionPane.showConfirmDialog(button, "정말로 삭제하시겠습니까? ");
//
//			if (select == 0) {
//				cartDao.deleteAll(member.getMid().toUpperCase());
//				JOptionPane.showMessageDialog(button, "삭제가 완료되었습니다.");
//			}
//		}
//	}

}