package com.statop.config;

import com.statop.service.AddressService;
import com.statop.service.GoogleMapsAddressService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ServiceConfiguration
{
    @Value("${google.addressService.url}")
    private String googleAddressServiceUrl;

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    public AddressService addressService(RestTemplate restTemplate) {
        return new GoogleMapsAddressService(googleAddressServiceUrl, restTemplate);
    }
}
