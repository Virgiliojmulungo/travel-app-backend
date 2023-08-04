package ja.esta.trips.backend.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ja.esta.trips.backend.exceptions.FailedUrlConnectionException;
import ja.esta.trips.backend.exceptions.IncorrectUrlFormatException;
import ja.esta.trips.backend.exceptions.ResourceNotFound;
import ja.esta.trips.backend.exceptions.ServerErrorException;


@Service
public class WebServiceUtility {

	 public Map<String, Object> httpGetMethod(String stringUrl) throws IncorrectUrlFormatException, FailedUrlConnectionException, IOException, ResourceNotFound, ServerErrorException {
		  URL url;
		  String jsonString = null;
		try {
			url = new URL(stringUrl);
		} catch (MalformedURLException e) {
			throw new IncorrectUrlFormatException("Url Format is not correct");
		}
			HttpURLConnection connection;
			try {
				connection = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				throw new FailedUrlConnectionException("Url Connect failed");
			}
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "https://explore.whatismybrowser.com/useragents/parse/?analyse-my-user-agent=yes");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				jsonString =  response.toString();
			}
			
			if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				throw new ResourceNotFound("Resource Not Found");
			}
			
			
			if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST || responseCode ==HttpURLConnection.HTTP_REQ_TOO_LONG)  {
				throw new ServerErrorException("Request Not Acceptable");
			}
			
			jsonString = "{\"data\":"+jsonString+"}";
			
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<String, Object>();
			return gson.fromJson(jsonString, map.getClass());
		
	 }
}
