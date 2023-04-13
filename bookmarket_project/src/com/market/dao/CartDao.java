package com.market.dao;

import java.util.ArrayList;

import com.market.vo.CartVo;
import com.market.vo.OrderVo;

public class CartDao extends DBConn {

	public int getSize(String mid) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) FROM BOOKMARKET_CART ");
		sql.append(" WHERE MID = ?");

		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid.toUpperCase());

			rs = pstmt.executeQuery();
			while (rs.next())
				result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/** 장바구니 추가 **/
	public int insert(CartVo cartVo) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO BOOKMARKET_CART ");
		sql.append(" VALUES ('C_'||LTRIM(TO_CHAR(SEQU_BOOKMARKET_CART_CID.NEXTVAL,'0000')), ");
		sql.append(" SYSDATE, 1, ?, ?)");

		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cartVo.getIsbn());
			pstmt.setString(2, cartVo.getMid().toUpperCase());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int insertCheck(CartVo cartVo) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) FROM BOOKMARKET_CART ");
		sql.append(" WHERE ISBN = ? AND MID = ?");

		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cartVo.getIsbn().toUpperCase());
			pstmt.setString(2, cartVo.getMid().toUpperCase());

			rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<CartVo> select(String mid) {
		ArrayList<CartVo> cartlist = new ArrayList<CartVo>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ISBN, TITLE, PRICE, QTY, TOTAL_PRICE, SPRICE, STOTAL_PRICE, ROWNUM RNO, CID");
		sql.append(" FROM (SELECT B.ISBN, B.TITLE, B.PRICE, C.QTY, ");
		sql.append("  C.QTY*B.PRICE TOTAL_PRICE, TO_CHAR(B.PRICE,'L999,999') SPRICE, ");
		sql.append("  TO_CHAR(C.QTY*B.PRICE, 'L999,999') STOTAL_PRICE, C.CID ");
		sql.append(" FROM BOOKMARKET_CART C, BOOKMARKET_MEMBER M, BOOKMARKET_BOOK B");
		sql.append(" WHERE C.MID = M.MID AND C.ISBN = B.ISBN ");
		sql.append(" AND M.MID = ?)");

		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CartVo cart = new CartVo();
				cart.setIsbn(rs.getString(1));
				cart.setTitle(rs.getString(2));
				cart.setPrice(rs.getInt(3));
				cart.setQty(rs.getInt(4));
				cart.setTotal_price(rs.getInt(5));
				cart.setSprice(rs.getString(6));
				cart.setStotal_price(rs.getString(7));
				cart.setRno(rs.getInt(8));
				cart.setCid(rs.getString(9));

				cartlist.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cartlist;
	}

	/** 선택 항목 삭제 **/
	public int delete(String cid) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM BOOKMARKET_CART ");
		sql.append("WHERE CID = ?");
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/** 전체 삭제 **/
	public int deleteAll(String mid) {
		int result = 0;
		String sql = "DELETE FROM BOOKMARKET_CART WHERE MID = ?";
		try {
			getPreparedStatement(sql);
			pstmt.setString(1, mid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/** 수량 수정하기 **/
	public int updateQty(String cid, String status) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		if (status.equals("plus")) {
			sql.append(" UPDATE BOOKMARKET_CART SET QTY = QTY + 1 ");
			sql.append(" WHERE CID = ?");
		} else if (status.equals("minus")) {
			sql.append(" UPDATE BOOKMARKET_CART SET QTY = QTY - 1 ");
			sql.append(" WHERE CID = ?");
		}
		try {
			getPreparedStatement(sql.toString());
			pstmt.setString(1, cid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/** 주문 테이블 데이터 생성 **/
	public OrderVo getOrderVo(String mid) {
		OrderVo orderVo = new OrderVo();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT QTY, ISBN FROM BOOKMARKET_CART WHERE MID = ? ORDER BY CDATE DESC");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			rs.last();
			int[] qtyList = new int[rs.getRow()];
			String[] isbnList = new String[rs.getRow()];

			rs.beforeFirst();
			int idx = 0;
			while (rs.next()) {
				// orderVo의 qtyList[], isbnList[] 데이터 저장
				qtyList[idx] = rs.getInt(1);
				isbnList[idx] = rs.getString(2);
				idx++;
			}
			orderVo.setQtyList(qtyList);
			orderVo.setIsbnList(isbnList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderVo;
	}
}
