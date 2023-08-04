package ja.esta.trips.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;

import ja.esta.trips.backend.api.TripsApi;
import ja.esta.trips.backend.exceptions.FailedUrlConnectionException;
import ja.esta.trips.backend.exceptions.IncorrectUrlFormatException;
import ja.esta.trips.backend.exceptions.ResourceNotFound;
import ja.esta.trips.backend.exceptions.ServerErrorException;
import ja.esta.trips.backend.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CitySearchUtility{
		private static final String API_URL = "http://api.geonames.org/searchJSON";
		private static final String API_KEY = "register2020";
		private static final Integer MAX_ROWS = 5;

		private static final Logger logger = LogManager.getLogger(CitySearchUtility.class);
		
		private String city;
		private Integer maxRows;
		private WebServiceUtility webServiceUtility;
		
		public CitySearchUtility(WebServiceUtility webServiceUtility) {
			super();
			this.webServiceUtility = webServiceUtility;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setMaxRows(Integer maxRows) {
			this.maxRows = maxRows;
		}


		public String go() throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
			String url = API_URL+"?"+"name_startsWith="+this.city+"&maxRows="+this.maxRows+"&isNameRequired=true&username="+API_KEY;
			
			Map<String, Object> response =  webServiceUtility.httpGetMethod(url);
			
			Map dataMap = (Map) response.get("data");
			List<Map> cityMaps = (List<Map>) dataMap.get("geonames");
			List<City> cities = new ArrayList<>();
			for(Map cityMap : cityMaps) {
				cities.add(new City(cityMap));
			}
			return new Gson().toJson(cities);
		}
		
}
