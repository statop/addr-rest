package com.statop.response;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Address {
    private final double latitude;
    private final double longitude;
    private final Date requestTime;

    //only a string with the full address for now, don't want to worry about proper handling of international addresses
    private final String address;

    public Address(double latitude, double longitude, Date requestTime, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.requestTime = requestTime;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getRequestTime() {
        return requestTime;
    }
}
