package ja.esta.trips.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ja.esta.trips.backend.exceptions.FailedUrlConnectionException;
import ja.esta.trips.backend.exceptions.IncorrectUrlFormatException;
import ja.esta.trips.backend.exceptions.ResourceNotFound;
import ja.esta.trips.backend.exceptions.ServerErrorException;
import ja.esta.trips.backend.model.WeatherForecast;

public class GeneralAPIHelper {
	
private WebServiceUtility webServiceUtility;
	
	public GeneralAPIHelper(WebServiceUtility webServiceUtility) {
		super();
		this.webServiceUtility = webServiceUtility;
	}
	
	public Double getPopulation(String countryCode) throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		String url = "https://api.worldbank.org/v2/country/"+countryCode+"/indicator/SP.POP.TOTL?format=json";
		Map<String, Object> response =  webServiceUtility.httpGetMethod(url);
		ArrayList<Map> resps = (ArrayList<Map>) response.get("data");
		ArrayList<Map> resps2 = (ArrayList<Map>) resps.get(1);
		Double population = (Double) resps2.get(0).get("value");
		return population;
	}
	
	public Double getGDP(String countryCode) throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		String url = "https://api.worldbank.org/v2/country/"+countryCode+"/indicator/NY.GDP.MKTP.CD?format=json";  
		Map<String, Object> response =  webServiceUtility.httpGetMethod(url);
		ArrayList<Map> resps = (ArrayList<Map>) response.get("data");
		ArrayList<Map> resps2 = (ArrayList<Map>) resps.get(1);
		Double gdp = (Double) resps2.get(0).get("value");
		return gdp;
	}
	
	public List<WeatherForecast> getForeCasts(String cityName) throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		
		String url = "https://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&appid=e82f557d6e912e702f5b15622b6edc3f";  
		Map<String, Object> response =  webServiceUtility.httpGetMethod(url);
		Map dataMap = (Map) response.get("data");
		List<Map> forecastsMap = (List<Map>) dataMap.get("list");
		

		List<WeatherForecast> forecasts = new ArrayList();
		for(Map fc: forecastsMap) {
			WeatherForecast forecast = new WeatherForecast();
			forecast.setDate(fc.get("dt_txt").toString()); 
			Map mainMap = (Map)fc.get("main");
			forecast.setMax((Double) mainMap.get("temp_max"));
			forecast.setMin((Double) mainMap.get("temp_min"));
			forecasts.add(forecast);
		}
		
		return forecasts;
	}
	

public Map getRates(String countryCurrency) throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		
		String url = "https://v6.exchangerate-api.com/v6/14246318c9cc6b9127033fb6/latest/"+countryCurrency;  
		Map<String, Object> response =  webServiceUtility.httpGetMethod(url);
		Map dataMap = (Map) response.get("data");
		
		return (Map) dataMap.get("conversion_rates");
		
	}
	
	
}
