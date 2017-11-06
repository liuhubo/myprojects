package com.hello.algorithm.comm;

import java.io.Serializable;

public class Human implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5753348980408547799L;
	/**
	 * @param args
	 */
		private String country;
		private String region;
		
		public Human(String country, String region) {
			super();
			this.country = country;
			this.region = region;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		@Override
		public String toString() {
			return "Human [country=" + country + ", region=" + region + "]";
		}
}
