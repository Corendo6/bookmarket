package com.market.commons;

import java.awt.Component;
import java.awt.Font;

public class MarketFont {
	
	public static Component getFont(Component comp) {
		Font ft  = new Font("맑은 고딕", Font.BOLD, 15);
		comp.setFont(ft);
		return comp;
	}
}
