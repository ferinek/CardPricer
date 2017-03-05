package com.kslodowicz.tools.mtg.comparators;

import java.util.Comparator;

import com.kslodowicz.tools.mtg.dto.CardDTO;

public class CardDtoByPriceComparator implements Comparator<CardDTO> {

	@Override
	public int compare(CardDTO o1, CardDTO o2) {
		return -1*Double.compare(o1.getPrice(), o2.getPrice());

	}

}
