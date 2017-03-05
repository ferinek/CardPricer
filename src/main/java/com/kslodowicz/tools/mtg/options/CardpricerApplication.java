package com.kslodowicz.tools.mtg.options;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.kslodowicz.tools.mtg.comparators.CardDtoByPriceComparator;
import com.kslodowicz.tools.mtg.dao.googlesheet.GoogleSheetService;
import com.kslodowicz.tools.mtg.dto.CardDTO;

@SpringBootApplication
@ComponentScan("com.kslodowicz.tools.mtg")
@PropertySource("classpath:application.properties")
public class CardpricerApplication {

	public static void main(String[] args) throws IOException, ParseException {
		ConfigurableApplicationContext run = SpringApplication.run(CardpricerApplication.class, args);

		GoogleSheetService bean2 = run.getBean(GoogleSheetService.class);
		List<CardDTO> cardData = bean2.getCardsData();
	
		
		Collections.sort(cardData, new CardDtoByPriceComparator());
		for (CardDTO dto:cardData){
			System.out.println(dto);
		}
		
	}
}
