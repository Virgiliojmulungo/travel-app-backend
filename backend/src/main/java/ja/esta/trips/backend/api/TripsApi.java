package ja.esta.trips.backend.api;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ja.esta.trips.backend.exceptions.FailedUrlConnectionException;
import ja.esta.trips.backend.exceptions.IncorrectUrlFormatException;
import ja.esta.trips.backend.exceptions.ResourceNotFound;
import ja.esta.trips.backend.exceptions.ServerErrorException;
import ja.esta.trips.backend.service.CityDetailsUtility;
import ja.esta.trips.backend.service.CitySearchUtility;
import ja.esta.trips.backend.service.WebServiceUtility;

@RestController
@RequestMapping("/api/v1")
public class TripsApi {

	private static final Logger logger = LogManager.getLogger(TripsApi.class);
	
	@Autowired
	WebServiceUtility webServiceUtility;
	
	@GetMapping(path="/city/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchCities(@RequestParam(value ="q")String query){
		logger.info("Searching for a city named "+ query);
		try {
			CitySearchUtility util = new CitySearchUtility(webServiceUtility);
			util.setCity(query);
			util.setMaxRows(5);
			ResponseEntity<String> ok = ResponseEntity.ok(util.go());

			logger.info("Found "+ query +"\n"+ ok.getBody());
			return ok;
		} catch (IncorrectUrlFormatException e) {
			logger.warn("Incorrect URL inserted.\n"+ ResponseEntity.badRequest().build().getBody());
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		} catch (FailedUrlConnectionException e) {
			logger.error("Could not access the URL.");
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		} catch (IOException e) {
			logger.error("Server is down.");
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
		} catch (ResourceNotFound e) {
			logger.warn("City "+ query + " was not found.");
			return (ResponseEntity<?>) ResponseEntity.notFound();
		} catch (ServerErrorException e) {
			logger.warn("Server error \n"+ ResponseEntity.badRequest().build().getBody());
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		}
	}
	
	@GetMapping(path = "/city/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCityDetails(
			@RequestParam(value ="city")String city,
			@RequestParam(value ="countryCode")String countryCode,
			@RequestParam(value ="countryName")String countryName
			){
		CityDetailsUtility util = new CityDetailsUtility(webServiceUtility, city, countryCode, countryName);
		try {
			return ResponseEntity.ok(util.go());
		} catch (IncorrectUrlFormatException e) {
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		} catch (FailedUrlConnectionException e) {
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		} catch (IOException e) {
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
		} catch (ResourceNotFound e) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		} catch (ServerErrorException e) {
			return  (ResponseEntity<?>) ResponseEntity.badRequest();
		}
	}

}
