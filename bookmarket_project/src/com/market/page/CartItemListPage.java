package com.market.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.main.MainWindow;
import com.market.vo.CartVo;

public class CartItemListPage extends JPanel implements ActionListener, MouseListener {

	JTable cartTable;
	Object[] tableHeader = { "번호", "도서ID", "도서명", "단가", "수량", "총가격" };
	CartDao cartDao;
	MemberDao memberDao;
	BookDao bookDao;
	public static int mSelectRow = -1;
	Integer totalPrice = 0;
	String[] menu_list = { "장바구니 비우기", "장바구니 항목 삭제하기", "수량 +", "수량 -" };
	ArrayList<JButton> button_list = new ArrayList<JButton>();
	JLabel totalPricelabel;
	ArrayList<CartVo> cartItemList;

	public CartItemListPage(JPanel panel, Map<String, DBConn> daoList) {
		this.cartDao = (CartDao) daoList.get("cartDao");
		this.memberDao = (MemberDao) daoList.get("memberDao");
		this.bookDao = (BookDao) daoList.get("bookDao");
		this.setLayout(null);

		Rectangle rect = panel.getBounds();
		this.setPreferredSize(rect.getSize());

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(0, 0, 1000, 400);
		add(bookPanel);

		cartTable = new JTable(showList(), tableHeader);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(600, 350));
		jScrollPane.setViewportView(cartTable);
		bookPanel.add(jScrollPane);

		JPanel totalPricePanel = new JPanel();
		totalPricePanel.setBounds(0, 400, 1000, 50);
		totalPricelabel = new JLabel("총금액: " + priceFormat(totalPrice) + " 원");
		totalPricelabel.setForeground(Color.red);
		MarketFont.getFont(totalPricelabel);
		totalPricePanel.add(totalPricelabel);
		add(totalPricePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBounds(0, 450, 1000, 50);
		add(buttonPanel);

		for (String name : menu_list) {
			JLabel buttonLabel = new JLabel(name);
			MarketFont.getFont(buttonLabel);
			JButton Button = new JButton();
			Button.add(buttonLabel);
			Button.addActionListener(this);
			button_list.add(Button);
			buttonPanel.add(Button);
		}

		cartTable.addMouseListener(this);

	}

	public Object[][] showList() {
		Object[][] content = null;

		cartItemList = cartDao.select(MainWindow.member.getMid());
		content = new Object[cartItemList.size()][tableHeader.length];
		totalPrice = 0;
		for (int i = 0; i < cartItemList.size(); i++) {
			CartVo item = cartItemList.get(i);
			content[i][0] = item.getRno();
			content[i][1] = item.getIsbn();
			content[i][2] = item.getTitle();
			content[i][3] = item.getSprice().trim();
			content[i][4] = item.getQty();
			content[i][5] = item.getStotal_price().trim();
			totalPrice += item.getTotal_price();
		}
		return content;
	}

	public String priceFormat(long price) {
		DecimalFormat df = new DecimalFormat("#,###");
		String sprice = df.format(price);

		return sprice;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		/** 장바구니 비우기 버튼 이벤트 처리 **/
		if (button == button_list.get(0)) {
//			if (cartDao.getSize(MainWindow.member.getMid().toUpperCase()) == 0)
//				JOptionPane.showMessageDialog(button_list.get(0), "장바구니가 비어 있습니다");
//			else {
			int select = JOptionPane.showConfirmDialog(button_list.get(0), "정말로 삭제하시겠습니까? ");
			if (select == 0) {
				int result = cartDao.deleteAll(MainWindow.member.getMid().toUpperCase());
				if (result == 1)
					JOptionPane.showMessageDialog(button_list.get(0), "삭제가 완료되었습니다");

				TableModel tableModel = new DefaultTableModel(new Object[0][0], tableHeader);
				cartTable.setModel(tableModel);
				totalPricelabel.setText("총금액: " + 0 + " 원");

//				}
			}
		}
		/** 장바구니 항목 삭제 버튼 이벤트 처리 **/
		if (button == button_list.get(1)) {
//			if (cartDao.getSize(MainWindow.member.getMid().toUpperCase()) == 0)
//				JOptionPane.showMessageDialog(button_list.get(1), "장바구니가 비어있습니다");
			if (mSelectRow == -1)
				JOptionPane.showMessageDialog(button_list.get(1), "삭제할 항목을 선택해주세요");
			else {
				cartItemList = cartDao.select(MainWindow.member.getMid().toUpperCase());
				int result = cartDao.delete(cartItemList.get(mSelectRow).getCid());

				TableModel tableModel = new DefaultTableModel(showList(), tableHeader);
				totalPricelabel.setText("총금액: " + priceFormat(totalPrice) + " 원");
				cartTable.setModel(tableModel);
			}
			mSelectRow = -1;
		}
		if (button == button_list.get(2)) {
//			if (cm.getSize() == 0)
//				JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다");
			if (mSelectRow == -1)
				JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요");
			else {
				cartItemList = cartDao.select(MainWindow.member.getMid().toUpperCase());
				int qty = cartItemList.get(mSelectRow).getQty();
				if (qty >= 1) {
					// update Qty - 하나씩 감소
					cartDao.updateQty(cartItemList.get(mSelectRow).getCid(), "plus");
				} else {
					JOptionPane.showMessageDialog(null, "1개 이상인 경우에만 수정 가능합니다");
				}
				TableModel tableModel = new DefaultTableModel(showList(), tableHeader);
				totalPricelabel.setText("총금액: " + priceFormat(totalPrice) + " 원");
				cartTable.setModel(tableModel);
				cartTable.setRowSelectionInterval(mSelectRow, mSelectRow);
//				mSelectRow = -1;
			}
		}

		if (button == button_list.get(3)) {
//			if (cm.getSize() == 0)
//				JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다");
			if (mSelectRow == -1)
				JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요");
			else {
				cartItemList = cartDao.select(MainWindow.member.getMid().toUpperCase());
				int qty = cartItemList.get(mSelectRow).getQty();
				if (qty > 1) {
					// update Qty - 하나씩 감소
					cartDao.updateQty(cartItemList.get(mSelectRow).getCid(), "minus");
				} else {
					JOptionPane.showMessageDialog(null, "1개 이상인 경우에만 수정 가능합니다");
				}
				TableModel tableModel = new DefaultTableModel(showList(), tableHeader);
				totalPricelabel.setText("총금액: " + priceFormat(totalPrice) + " 원");
				cartTable.setModel(tableModel);
				cartTable.setRowSelectionInterval(mSelectRow, mSelectRow);
//				mSelectRow = -1;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = cartTable.getSelectedRow();
		mSelectRow = row;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}