package com.incident.management.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.incident.management.dto.LocationResponse;

@Service
public class LocationService {

	@Autowired
    private RestTemplate restTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public LocationResponse getLocationByCountryAndPincode(String countryCode, String pincode) {
        try {
            if (countryCode == null || pincode == null) {
                return new LocationResponse("Unknown", "Unknown", "Unknown");
            }

            if ("IN".equalsIgnoreCase(countryCode)) {
                return getIndianLocation(pincode);
            } else {
                return getGlobalLocation(countryCode, pincode);
            }
        } catch (Exception e) {
            System.out.println("Error in LocationService: " + e.getMessage());
            return new LocationResponse("Unknown", "Unknown", "Unknown");
        }
    }

    private LocationResponse getIndianLocation(String pincode) {
        try {
            String url = "https://api.postalpincode.in/pincode/" + pincode;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            logger.info("response:"+response.getStatusCode());
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            	logger.info("body:"+response.getBody());
                JSONArray arr = new JSONArray(response.getBody());
                if (arr.length() > 0) {
                    JSONObject root = arr.getJSONObject(0);
                    JSONArray postOffices = root.optJSONArray("PostOffice");
                    if (postOffices != null && postOffices.length() > 0) {
                        JSONObject first = postOffices.getJSONObject(0);
                        String city = first.optString("District", "Unknown");
                        String state = first.optString("State", "Unknown");
                        String country = first.optString("Country", "India");
                        return new LocationResponse(city, state, country);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Indian location fetch failed: " + e.getMessage());
        }
        return new LocationResponse("Unknown", "Unknown", "India");
    }

    private LocationResponse getGlobalLocation(String countryCode, String pincode) {
        try {
            String url = "https://api.zippopotam.us/" + countryCode.toLowerCase() + "/" + pincode;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JSONObject json = new JSONObject(response.getBody());
                String country = json.optString("country", "Unknown");
                JSONObject place = json.getJSONArray("places").getJSONObject(0);
                String city = place.optString("place name", "Unknown");
                String state = place.optString("state", "Unknown");
                return new LocationResponse(city, state, country);
            }
        } catch (Exception e) {
            System.out.println("Global location fetch failed: " + e.getMessage());
        }
        return new LocationResponse("Unknown", "Unknown", "Unknown");
    }
}

