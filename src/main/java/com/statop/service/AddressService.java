package com.statop.service;

import com.statop.response.Address;

public interface AddressService {
    Address getAddress(double latitude, double longitude);
}
