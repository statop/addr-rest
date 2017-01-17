package com.statop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import com.statop.error.NotFoundException;
import com.statop.response.Address;
import com.statop.service.AddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    private final AtomicLong currentId = new AtomicLong(Long.MIN_VALUE + 100);
    private final ConcurrentSkipListMap<Long, Address> last10Calls = new ConcurrentSkipListMap<>();

    @RequestMapping("/address/{latitude}/{longitude}")
    public Address address(@PathVariable double latitude, @PathVariable double longitude) {
        Address address = addressService.getAddress(latitude, longitude);

        if (address == null)
        {
            throw new NotFoundException(latitude, longitude);
        }

        //last10Calls is a non-blocking map ordered by an "id" that always goes up
        //so we simply get the next id and remove all the addresses with an id that is less than id - 10

        //yes, this would be super easy with a LinkedBlockingQueue, but that would basically make this controller single-threaded

        long id = currentId.incrementAndGet();

        last10Calls.put(id, address);

        for (Iterator<Entry<Long, Address>> itr = last10Calls.entrySet().iterator(); itr.hasNext(); ) {
            if (itr.next().getKey() < id - 10) {
                itr.remove();
            } else {
                break;
            }
        }

        return address;
    }

    @RequestMapping("/last10Addresses")
    public List<Address> last10Addresses() {
        List<Address> addresses = new ArrayList<>(10);

        for (Address addr : last10Calls.descendingMap().values())
        {
            addresses.add(addr);
            if (addresses.size() >= 10)
            {
                break;
            }
        }

        return addresses;
    }
}
