package com.kslodowicz.tools.mtg.dao.json;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Repository;

import com.google.common.io.Files;

@Repository
public class JsonDao {
	private static final Logger LOG = Logger.getLogger(JsonDao.class);
	private JSONObject sets;

	@PostConstruct
	private void initialize() {
		File file = new File("C:\\Users\\Wookie\\workspace\\CardPricer\\src\\main\\resources\\AllSets.json");
		try {
			String content = Files.toString(file, StandardCharsets.UTF_8);
			JSONParser parser = new JSONParser();
			sets = (JSONObject) parser.parse(content);
		} catch (Exception e) {
			LOG.error(e);
		}

	}

	@SuppressWarnings("unchecked")
	public List<String> findExpansionForCardName(String cardName) {
		List<String> expansions = new LinkedList<String>();
		Set<String> setCodes = sets.keySet();
		for (String setCode : setCodes) {
			JSONObject set = (JSONObject) sets.get(setCode);
			JSONArray cards = (JSONArray) set.get("cards");
			for (int i = 0; i < cards.size(); i++) {
				String name = (String) ((JSONObject) cards.get(i)).get("name");
				if (name.equals(cardName)) {
					expansions.add((String) set.get("name"));
				}
			}
		}
		return expansions;
	}

}
