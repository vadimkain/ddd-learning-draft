package com.example.domain_events;

public class CustomerUnitTest {
    public void doSomethingShouldMakeCustomerPreferred() {
        Customer<T> c = new Customer<T>();
        final Customer<T>[] preferred = new Customer<T>[1];

        DomainEvents.register(CustomerBecamePreferred.class, event -> preferred[0] = event.getCustomer());

        c.doSomething();

        assert preferred[0] == c && c.isPreferred();
//        Assert.assertTrue(preferred[0] == c && c.isPreferred());
    }
}
