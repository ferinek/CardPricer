package com.kslodowicz.tools.mtg.dao.googlesheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.kslodowicz.tools.mtg.dao.json.JsonDao;
import com.kslodowicz.tools.mtg.dao.mcm.PriceReader;
import com.kslodowicz.tools.mtg.dto.CardDTO;
import com.kslodowicz.tools.mtg.utils.ParserUtil;

@Service
public class GoogleSheetService extends GoogleSheetInterface {
	private static final Logger LOG = Logger.getLogger(GoogleSheetService.class);
	private String spreadsheetId;
	private String sheetName;
	@Autowired
	private Environment env;
	@Autowired
	private JsonDao jsonService;
	@Autowired
	private PriceReader priceReader;

	@PostConstruct
	private void setProperties() {
		spreadsheetId = env.getProperty("spreadsheet.id");
		sheetName = env.getProperty("spreadsheet.name");
	}

	public List<CardDTO> getCardsData() {
		List<CardDTO> dtos = new LinkedList<>();
		int i = 2;
		CardDTO dto;
		while ((dto = getDataFromRow(i)) != null) {
			dtos.add(dto);
			System.out.println(dto);
			i++;
		}

		return dtos;
	}

	private ValueRange getDataFromSpreedSheet(String range) {
		try {
			return service.spreadsheets().values().get(spreadsheetId, range).execute();
		} catch (IOException e) {
			LOG.error(e);
			return null;
		}
	}

	private CardDTO getDataFromRow(int rowNumber) {
		String range = String.format("%s!A%d:H%d", sheetName, rowNumber, rowNumber);
		ValueRange data = getDataFromSpreedSheet(range);
		if (isRowEmpty(data)) {
			return null;
		}
		return getCardData(data, rowNumber);

	}

	private CardDTO getCardData(ValueRange data, int rowNumber) {
		CardDTO dto = new CardDTO();
		List<Object> values = data.getValues().get(0);
		dto.setCategory(ParserUtil.getStringValue(0, values));
		dto.setAmount(ParserUtil.getIntValue(1, values));
		dto.setCardName(ParserUtil.getStringValue(2, values));
		dto.setExpansion(ParserUtil.getStringValue(3, values));
		setExpansionIfNotExist(rowNumber, dto);
		dto.setComment(ParserUtil.getStringValue(7, values));
		Double price = priceReader.getPriceTrend(dto.getCardName(), dto.getExpansion());
		dto.setPrice(price);
		return dto;
	}

	private void setExpansionIfNotExist(int rowNumber, CardDTO dto) {
		if (dto.getExpansion() == null) {
			List<String> expansions = jsonService.findExpansionForCardName(dto.getCardName());
			setExpansion(rowNumber, expansions);
		}
	}

	private void setExpansion(int rowNumber, List<String> expansions) {
		if (expansions.isEmpty()) {
			throw new RuntimeException("Can't find expansion for card in row " + rowNumber);
		}
		if (expansions.size() == 1) {
			setValue("D" + (rowNumber + 1), expansions.get(0));
		}

	}

	private boolean isRowEmpty(ValueRange data) {
		if (data.getValues() == null || data.getValues().size() == 0) {
			return true;
		}
		for (Object value : data.getValues().get(0)) {
			if (!"".equals(value.toString())) {
				return false;
			}
		}
		return true;
	}



	private List<List<Object>> getData(String value) {
		List<Object> data1 = new ArrayList<>();
		data1.add(value);
		List<List<Object>> data = new ArrayList<>();
		data.add(data1);
		return data;
	}

	private void setValue(String cell, String value) {
		ValueRange oRange = new ValueRange();
		oRange.setRange(cell + ":" + cell);
		oRange.setValues(getData(value));
		List<ValueRange> oList = new ArrayList<>();
		oList.add(oRange);

		BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest();
		oRequest.setValueInputOption("RAW");
		oRequest.setData(oList);

		executeUpdate(oRequest);

	}

	private void executeUpdate(BatchUpdateValuesRequest oRequest) {
		try {
			service.spreadsheets().values().batchUpdate(spreadsheetId, oRequest).execute();
		} catch (IOException e) {
			LOG.error(e);
		}
	}

}