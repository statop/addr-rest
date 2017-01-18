
package com.statop.service;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.ImmutableMap;
import com.statop.response.Address;

import org.springframework.web.client.RestTemplate;

public class GoogleMapsAddressService implements AddressService {
    private final String url;
    private final RestTemplate restTemplate;

    public GoogleMapsAddressService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public Address getAddress(double latitude, double longitude) {
        GoogleMapsResponse response = restTemplate.getForObject(url, GoogleMapsResponse.class,
                ImmutableMap.of("latitude", String.valueOf(latitude), "longitude", String.valueOf(longitude)));

        if ((response != null) && (response.getResults() != null) && (response.getResults().length > 0) && (response.getResults()[0].getFormatted_address() != null)) {
            return new Address(latitude, longitude, new Date(), response.getResults()[0].getFormatted_address());
        }

        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoogleMapsResponse {
        private GoogleMapsResponseResult[] results;

        public GoogleMapsResponseResult[] getResults() {
            return results;
        }

        public void setResults(GoogleMapsResponseResult[] results) {
            this.results = results;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoogleMapsResponseResult {
        private String formatted_address;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }
    }
}
