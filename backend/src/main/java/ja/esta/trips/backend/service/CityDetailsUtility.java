package ja.esta.trips.backend.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import com.google.gson.Gson;

import ja.esta.trips.backend.exceptions.FailedUrlConnectionException;
import ja.esta.trips.backend.exceptions.IncorrectUrlFormatException;
import ja.esta.trips.backend.exceptions.ResourceNotFound;
import ja.esta.trips.backend.exceptions.ServerErrorException;
import ja.esta.trips.backend.model.City;
import ja.esta.trips.backend.model.CityDetail;
import ja.esta.trips.backend.model.WeatherForecast;

public class CityDetailsUtility {
	private WebServiceUtility webServiceUtility;
	private String countryCode;
	private String cityName;
	private String countryName;
	
	
	public CityDetailsUtility(WebServiceUtility webServiceUtility, String cityName, String countryCode, String countryName) {
		super();
		this.webServiceUtility = webServiceUtility;
		this.countryCode = countryCode;
		this.cityName = cityName;
		this.countryName = countryName;
	}


	public String go() throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		CityDetail details = new CityDetail();
		GeneralAPIHelper wbUtil = new GeneralAPIHelper(webServiceUtility);
		details.setPopulation(wbUtil.getPopulation(this.countryCode));
		details.setGdp(wbUtil.getGDP(this.countryCode));
		details.setForecasts(wbUtil.getForeCasts(this.cityName));
		
		//Transforming Country Name into Country Currency Code
		String countryCurrency = "MZN"; 
		File file = ResourceUtils.getFile("classpath:currencies.json");
        String jsonString = CityDetailsUtility.getFileContent(new FileInputStream(file),"UTF-8"); 
        List<Map> mapList = new ArrayList();
        
        mapList = new Gson().fromJson(jsonString, mapList.getClass());
        
        for(Map currency: mapList) {
        	if(currency.get("country").toString().equalsIgnoreCase(this.countryName)) {
        		countryCurrency = currency.get("currency_code").toString(); 
        	}
        }
        
        details.setConversionRates(wbUtil.getRates(countryCurrency));
        return new Gson().toJson(details);
		
	}
	
	public static String getFileContent(
			   FileInputStream fis,  String encoding ) throws IOException
			 {
			   try( BufferedReader br =
			           new BufferedReader( new InputStreamReader(fis, encoding )))
			   {
			      StringBuilder sb = new StringBuilder();
			      String line;
			      while(( line = br.readLine()) != null ) {
			         sb.append( line );
			         sb.append( '\n' );
			      }
			      return sb.toString();
			   }
			}
	
}
