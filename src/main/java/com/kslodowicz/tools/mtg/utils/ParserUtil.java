package com.kslodowicz.tools.mtg.utils;

import java.util.List;

public class ParserUtil {
	public static int getIntValue(int column, List<Object> values) {
		if (column < values.size()) {
			return Integer.parseInt(values.get(column).toString());
		}
		return -1;
	}

	public static String getStringValue(int column, List<Object> values) {

		if (column < values.size()) {
			return values.get(column).toString();
		}
		return null;
	}
}
