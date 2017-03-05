package com.kslodowicz.tools.mtg.dao.mcm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wookie Class reading prices from magiccardmarket.eu using selenium
 *         library
 */
@Service
public class PriceReader {

	private static final String MCM_URL_PATTERN = "https://www.magiccardmarket.eu/Products/Singles/%s/%s";
	private static final Double EURO_VALUE = 4D;
	@Autowired
	private WebDriver driver;

	/**
	 * @param cardName
	 * @param expansion
	 * @return prize Trend on mcm as double object
	 */
	public Double getPriceTrend(String cardName, String expansion) {
		boolean exist = goToCardPagePage(cardName, expansion);
		if (exist) {
			String price = readPriceTrend();
			return parseEuroPrice(price);
		} else {
			return -1D;
		}

	}

	private Double parseEuroPrice(String price) {
		return Double.parseDouble(price.replace(",", ".").split(" ")[0]) * EURO_VALUE;
	}

	private String readPriceTrend() {
		WebElement findElement = driver.findElement(By.cssSelector(".availTable .outerRight.col_Odd.col_1.cell_2_1"));
		return findElement.getText();
	}

	private boolean goToCardPagePage(String cardName, String expansion) {
		if (cardName == null || expansion == null) {
			return false;
		}
		driver.get(String.format(MCM_URL_PATTERN, parseName(expansion), parseName(cardName)));
		return (driver.findElements(By.id("errorMessageBox")).size() == 0);
	}

	private String parseName(String name) {
		return name.replace(" ", "+").replace(",", "%2C").replace(":", "%3A");
	}
}
