package ja.esta.trips.backend.model;

import java.util.Map;

public class City {
	
		String name;
		String country;
		String countryCode;
		
		public City(Map mapObj) {
			this.name = mapObj.get("name").toString();
			this.country = mapObj.get("countryName").toString();
			this.countryCode = mapObj.get("countryCode").toString();
	
		}
		
		@Override
		public String toString() {
			return "City [name=" + name + ", country=" + country + ", countryCode=" + countryCode + "]";
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		
		
}
