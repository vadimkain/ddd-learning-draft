package com.example.domain_events;

public class CustomerBecamePreferredHandler implements Handles<CustomerBecamePreferred> {
    @Override
    public void handle(CustomerBecamePreferred customerBecamePreferred) {
//        send email to customerBecamePreferred.getCustomer()
    }
}
