package com.typeahead.data;

import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.annconia.api.json.JsonUtils;
import com.annconia.util.StringUtils;
import com.typeahead.entity.City;
import com.typeahead.entity.County;
import com.typeahead.entity.State;

public class CensusCityFileConverter {

	Set<State> states = new HashSet<State>();
	Set<City> cities = new HashSet<City>();
	Set<County> counties = new HashSet<County>();

	public static void main(String... args) {
		new CensusCityFileConverter().run();
	}

	public void run() {
		try {
			parseFile();
			writeStates();
			writeCities();
			writeCounties();

		} catch (Throwable ex) {
			System.out.println("Error: " + ex);
		}
	}

	public void parseFile() throws Exception {
		ClassPathResource resource = new ClassPathResource("/data/SUB-EST2011_ALL.csv");
		List<String> lines = IOUtils.readLines(resource.getInputStream());
		int countyId = 1000;
		int cityId = 10000;
		for (String line : lines) {
			String[] values = StringUtils.split(line, ',');

			int code = StringUtils.parseInteger(values[0]);

			if (code == 40) {
				State state = new State();
				state.setElementId(StringUtils.parseInteger(values[1]));
				state.setState(values[6]);
				state.setPop(StringUtils.parseInteger(values[11]));
				state.setTerms(createTerms(StringUtils.lowerCase(state.getState())));
				state.setTimestamp(System.currentTimeMillis());
				states.add(state);
			}

			if (code == 50) {
				County value = new County();
				value.setElementId(countyId++);
				value.setCounty(values[6]);
				value.setState(values[7]);
				value.setPop(StringUtils.parseInteger(values[11]));
				value.setTerms(createTerms(StringUtils.lowerCase(value.getCounty())));
				value.setTimestamp(System.currentTimeMillis());
				counties.add(value);
			}

			if (code == 162) {
				City value = new City();
				value.setElementId(cityId++);
				value.setCity(normalizeCity(values[6]));
				value.setState(values[7]);
				value.setPop(StringUtils.parseInteger(values[11]));
				value.setTerms(createTerms(StringUtils.lowerCase(value.getCity())));
				value.setTimestamp(System.currentTimeMillis());
				cities.add(value);
			}
		}
	}

	public void writeStates() throws Exception {
		StringBuilder output = new StringBuilder();
		for (State state : states) {
			output.append(JsonUtils.toJson(state));
			output.append("\n");
		}

		FileCopyUtils.copy(StringUtils.replace(output.toString(), "\"id\"", "\"_id\""), new FileWriter(new File("c:/states.json")));
	}

	public void writeCities() throws Exception {
		StringBuilder output = new StringBuilder();
		for (City city : cities) {
			output.append(JsonUtils.toJson(city));
			output.append("\n");
		}

		FileCopyUtils.copy(StringUtils.replace(output.toString(), "\"id\"", "\"_id\""), new FileWriter(new File("c:/cities.json")));
	}

	public void writeCounties() throws Exception {
		StringBuilder output = new StringBuilder();
		for (County county : counties) {
			output.append(JsonUtils.toJson(county));
			output.append("\n");
		}

		FileCopyUtils.copy(StringUtils.replace(output.toString(), "\"id\"", "\"_id\""), new FileWriter(new File("c:/counties.json")));
	}

	protected String normalizeCity(String city) {
		return StringUtils.removeEnd(StringUtils.removeEnd(StringUtils.removeEnd(city, " city"), " town"), " village");
	}

	protected String[] createTerms(String query) {
		return query.replaceAll("\\W+", " ").toLowerCase().split(" ");
	}

}
