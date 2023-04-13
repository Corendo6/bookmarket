package com.market.dao;

import com.market.main.MainWindow;
import com.market.vo.MemberVo;

public class MemberDao extends DBConn {
	/*
	 * 고객정보 확인
	 */
	public MemberVo select(String mid) {
		MemberVo member = new MemberVo();
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT mid, pass, name, addr,phone,mdate FROM BOOKMARKET_MEMBER ");
		sb.append(" WHERE MID= ? ");
		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member.setMid(rs.getString(1));
				member.setPass(rs.getString(2));
				member.setName(rs.getString(3));
				member.setAddr(rs.getString(4));
				member.setPhone(rs.getString(5));
				member.setMdate(rs.getString(6));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return member;
	}

	/*
	 * 로그인 체크
	 */
	public int select(String mid, String pass) {
		int result = 0;
		StringBuffer sb = new StringBuffer(100);
		sb.append(" SELECT COUNT(*) FROM BOOKMARKET_MEMBER ");
		sb.append(" WHERE MID = ? AND PASS = ?");

		try {
			getPreparedStatement(sb.toString());
			pstmt.setString(1, mid.toUpperCase());
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			while (rs.next())
				result = rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
