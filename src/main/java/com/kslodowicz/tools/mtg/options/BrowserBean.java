package com.kslodowicz.tools.mtg.options;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import io.github.bonigarcia.wdm.ChromeDriverManager;

@Configuration
public class BrowserBean {

	@Bean
	public WebDriver initializeBrowser() {
		ChromeDriverManager.getInstance().setup();
		return new ChromeDriver();

	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
