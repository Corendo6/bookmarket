package com.market.vo;

public class OrderVo {
	String oid, odate, isbn, mid, name, phone, oaddr, sprice, stotal_price;
	int qty, rno, price, total_price;
	int[] qtyList; // 데이터 추가
	String[] isbnList; // 데이터 추가

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOdate() {
		return odate;
	}

	public void setOdate(String odate) {
		this.odate = odate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOaddr() {
		return oaddr;
	}

	public void setOaddr(String oaddr) {
		this.oaddr = oaddr;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int[] getQtyList() {
		return qtyList;
	}

	public void setQtyList(int[] qtyList) {
		this.qtyList = qtyList;
	}

	public String[] getIsbnList() {
		return isbnList;
	}

	public void setIsbnList(String[] isbnList) {
		this.isbnList = isbnList;
	}

	public String getSprice() {
		return sprice;
	}

	public void setSprice(String sprice) {
		this.sprice = sprice;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStotal_price() {
		return stotal_price;
	}

	public void setStotal_price(String stotal_price) {
		this.stotal_price = stotal_price;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

}
