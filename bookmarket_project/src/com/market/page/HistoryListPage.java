package com.market.page;

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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.dao.OrderDao;
import com.market.main.MainWindow;
import com.market.vo.CartVo;
import com.market.vo.OrderVo;

public class HistoryListPage extends JPanel implements MouseListener {

	JTable cartTable;
	Object[] tableHeader = { "번호", "주문번호", "주문일자", "주문 내역" };
	int mSelectRow = 0;
	Integer totalPrice = 0;
	ArrayList<OrderVo> orderList;
	OrderDao orderDao;
	MainWindow main;

	public HistoryListPage(JPanel panel, Map<String, DBConn> daoList, MainWindow main) {
		this.orderDao = (OrderDao) daoList.get("orderDao");
		this.setLayout(null);
		this.main = main;

		Rectangle rect = panel.getBounds();
		this.setPreferredSize(rect.getSize());

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(0, 0, 1000, 400);
		add(bookPanel);

		cartTable = new JTable(showList(), tableHeader);
		TableColumnModel columnModel = cartTable.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(50); // 첫 번째 열의 너비를 100으로 설정
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(2).setPreferredWidth(100);
		columnModel.getColumn(3).setPreferredWidth(100);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(600, 350));
		jScrollPane.setViewportView(cartTable);
		bookPanel.add(jScrollPane);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBounds(0, 450, 1000, 50);
		add(buttonPanel);

		JLabel buttonLabel = new JLabel("상세 정보");
		MarketFont.getFont(buttonLabel);
		JButton Button = new JButton();
		Button.add(buttonLabel);
		Button.addActionListener(e -> {
			bookPanel.removeAll();
			buttonPanel.removeAll();
			buttonPanel.revalidate();
			buttonPanel.repaint();
			
			bookPanel.add(new OrderHistoryPage(bookPanel, orderDao, main, orderList.get(mSelectRow).getOid()));
			bookPanel.revalidate();
			bookPanel.repaint();
			
			
		});
		buttonPanel.add(Button);

		cartTable.addMouseListener(this);

	}

	public Object[][] showList() {
		Object[][] content = null;

		orderList = orderDao.selectHistoryList(MainWindow.member.getMid());
		content = new Object[orderList.size()][tableHeader.length];
		totalPrice = 0;
		for (int i = 0; i < orderList.size(); i++) {
			OrderVo item = orderList.get(i);
			content[i][0] = item.getRno();
			content[i][1] = item.getOid();
			content[i][2] = item.getOdate();
			content[i][3] = orderDao.getIsbn(item.getOid()) + " 외 " + (orderDao.getSize(item.getOid(), "oid") - 1)
					+ "건";
		}
		return content;
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