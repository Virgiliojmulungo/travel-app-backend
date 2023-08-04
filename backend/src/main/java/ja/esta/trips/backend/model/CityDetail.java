package ja.esta.trips.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityDetail {

		private double population;
		private double gdp;
		private Map conversionRates;
		private List<WeatherForecast> forecasts;
		public double getPopulation() {
			return population;
		}
		public void setPopulation(double population) {
			this.population = population;
		}
		public double getGdp() {
			return gdp;
		}
		public void setGdp(double gdp) {
			this.gdp = gdp;
		}
		public Map getConversionRates() {
			return conversionRates;
		}
		public void setConversionRates(Map conversionRates) {
			this.conversionRates = conversionRates;
		}
		public List<WeatherForecast> getForecasts() {
			return forecasts;
		}
		public void setForecasts(List<WeatherForecast> forecasts) {
			this.forecasts = forecasts;
		}
		
		
		
}

