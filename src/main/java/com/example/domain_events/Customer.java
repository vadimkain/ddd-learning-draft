package com.example.domain_events;

public class Customer {
    public void doSomething() {
        DomainEvents.raise(new CustomerBecamePreferred(this));
    }
}
