package com.market.dao;

import java.util.ArrayList;

import com.market.vo.OrderVo;

public class OrderDao extends DBConn {
	/* 데이터 추가 - PreparedStatement */
	public int insertPrepared(OrderVo orderVo) {
		int result = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO BOOKMARKET_ORDER (OID, ODATE, QTY, ISBN, MID, NAME, PHONE, OADDR)\r\n");
		sb.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		try {
			getPreparedStatement(sb.toString());
			for (int i = 0; i < orderVo.getQtyList().length; i++) {
				pstmt.setString(1, orderVo.getOid());
				pstmt.setString(2, orderVo.getOdate());
				pstmt.setInt(3, orderVo.getQtyList()[i]);
				pstmt.setString(4, orderVo.getIsbnList()[i]);
				pstmt.setString(5, orderVo.getMid());
				pstmt.setString(6, orderVo.getName());
				pstmt.setString(7, orderVo.getPhone());
				pstmt.setString(8, orderVo.getOaddr());
				pstmt.addBatch(); // 하나의 배치로 pstmt 파라미터 저장
				pstmt.clearParameters(); // 파라미터 초기화
			}
			result = pstmt.executeBatch().length; // 배치 실행
			pstmt.clearParameters();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/* 데이터 추가 - Statement*/
	public int insert(OrderVo orderVo) {
		int result = 0;
		getStatement();
		try {
			for (int i = 0; i < orderVo.getQtyList().length; i++) {
				StringBuffer sb = new StringBuffer();
				sb.append("INSERT INTO BOOKMARKET_ORDER (OID, ODATE, QTY, ISBN, MID, NAME, PHONE, OADDR)\r\n VALUES (");
				sb.append("'" + orderVo.getOid() + "', ");
				sb.append("'" + orderVo.getOdate() + "', ");
				sb.append(orderVo.getQtyList()[i] + ", ");
				sb.append("'" + orderVo.getIsbnList()[i] + "', ");
				sb.append("'" + orderVo.getMid() + "', ");
				sb.append("'" + orderVo.getName() + "', ");
				sb.append("'" + orderVo.getPhone() + "', ");
				sb.append("'" + orderVo.getOaddr() + "')");
				result = stmt.executeUpdate(sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/** **/
	public int getSize(String mid) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM BOOKMARKET_ORDER ");
		sql.append(" WHERE MID = ?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getSize(String oid, String str) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM BOOKMARKET_ORDER ");
		sql.append(" WHERE OID = ?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, oid);
			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<OrderVo> selectHistoryList(String mid) {
		ArrayList<OrderVo> orderVo = new ArrayList<OrderVo>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ROWNUM RNO, OID, ODATE, QTY ");
		sql.append(" FROM(SELECT OID, ODATE, SUM(QTY) QTY FROM BOOKMARKET_ORDER WHERE MID = ? GROUP BY OID, ODATE)");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderVo order = new OrderVo();
				order.setRno(rs.getInt(1));
				order.setOid(rs.getString(2));
				order.setOdate(rs.getString(3));
				order.setQty(rs.getInt(4));
				orderVo.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderVo;
	}
	
	public ArrayList<OrderVo> select(String oid) {
		ArrayList<OrderVo> orderVo = new ArrayList<OrderVo>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ROWNUM RNO, OID, ODATE, QTY, O.ISBN, MID, NAME, PHONE, OADDR, ");
		sql.append(" PRICE, QTY * PRICE OTOTAL_PRICE, TO_CHAR(PRICE, '999,999') SPRICE, TO_CHAR(QTY * PRICE, '999,999') STOTAL_PRICE ");
		sql.append(" FROM BOOKMARKET_ORDER O, BOOKMARKET_BOOK B WHERE O.ISBN = B.ISBN AND OID = ?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, oid);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderVo order = new OrderVo();
				order.setRno(rs.getInt(1));
				order.setOid(rs.getString(2));
				order.setOdate(rs.getString(3));
				order.setQty(rs.getInt(4));
				order.setIsbn(rs.getString(5));
				order.setMid(rs.getString(6));
				order.setName(rs.getString(7));
				order.setPhone(rs.getString(8));
				order.setOaddr(rs.getString(9));
				order.setPrice(rs.getInt(10));
				order.setTotal_price(rs.getInt(11));
				order.setSprice(rs.getString(12));
				order.setStotal_price(rs.getString(13));
				orderVo.add(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderVo;
	}
	
	public String getIsbn(String oid) {
		String isbn = "";
		OrderVo orderVo = new OrderVo();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ISBN FROM BOOKMARKET_ORDER WHERE OID = ?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, oid);
			rs = pstmt.executeQuery();
			rs.next();
			isbn = rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isbn;
	}
	
}
