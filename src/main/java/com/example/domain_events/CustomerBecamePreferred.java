package com.example.domain_events;

public class CustomerBecamePreferred implements IDomainEvent {
    private Customer<T> customer;

    public CustomerBecamePreferred(Customer<T> customer) {
        this.customer = customer;
    }

    public Customer<T> getCustomer() {
        return customer;
    }

    public void setCustomer(Customer<T> customer) {
        this.customer = customer;
    }
}
