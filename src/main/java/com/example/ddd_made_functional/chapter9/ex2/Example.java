package com.example.ddd_made_functional.chapter9.ex2;

import java.util.Optional;

public class Example {

}

// Interfaces and types
interface CheckProductCodeExists {
    boolean check(String productCode);
}

interface CheckAddressExists {
    CheckedAddress check(UnvalidatedAddress address);
}

interface ValidateOrder {
    ValidatedOrder validate(CheckProductCodeExists checkProductCodeExists,
                            CheckAddressExists checkAddressExists,
                            UnvalidatedOrder unvalidatedOrder);
}

// Main validation function
class OrderValidator implements ValidateOrder {
    @Override
    public ValidatedOrder validate(CheckProductCodeExists checkProductCodeExists,
                                   CheckAddressExists checkAddressExists,
                                   UnvalidatedOrder unvalidatedOrder) {
        OrderId orderId = OrderId.create(unvalidatedOrder.getOrderId());
        CustomerInfo customerInfo = toCustomerInfo(unvalidatedOrder.getCustomerInfo());
        Address shippingAddress = toAddress(checkAddressExists, unvalidatedOrder.getShippingAddress());

        // ... And so on for each field of the unvalidated order.
        // When everything is ready and checked, return the validated order:

        return new ValidatedOrder(
                orderId,
                customerInfo,
                shippingAddress
                // billingAddress,
                // lines,
                // ... other fields
        );
    }

    private CustomerInfo toCustomerInfo(UnvalidatedCustomerInfo customer) {
        String50 firstName = String50.create(customer.getFirstName());
        String50 lastName = String50.create(customer.getLastName());
        EmailAddress emailAddress = EmailAddress.create(customer.getEmailAddress());

        PersonalName name = new PersonalName(firstName, lastName);
        return new CustomerInfo(name, emailAddress);
    }

    private Address toAddress(CheckAddressExists checkAddressExists, UnvalidatedAddress unvalidatedAddress) {
        CheckedAddress checkedAddress = checkAddressExists.check(unvalidatedAddress);

        String50 addressLine1 = String50.create(checkedAddress.getAddressLine1());
        Optional<String50> addressLine2 = Optional.ofNullable(checkedAddress.getAddressLine2())
                .map(String50::create);
        String50 city = String50.create(checkedAddress.getCity());
        ZipCode zipCode = ZipCode.create(checkedAddress.getZipCode());

        return new Address(addressLine1, addressLine2, city, zipCode);
    }

    private ProductCode toProductCode(CheckProductCodeExists checkProductCodeExists, String productCode) {
        // Implementation details
        return null; // Placeholder
    }

    private ValidatedOrderLine toValidatedOrderLine(CheckProductCodeExists checkProductExists,
                                                    UnvalidatedOrderLine unvalidatedOrderLine) {
        // OrderLineId orderLineId = ...;
        ProductCode productCode = toProductCode(checkProductExists, unvalidatedOrderLine.getProductCode());

        // ... other implementation details

        return null; // Placeholder
    }
}

// Other necessary classes (just signatures for demonstration)
class OrderId {
    public static OrderId create(String id) {
        return null;
    }
}

class String50 {
    public static String50 create(String s) {
        return null;
    }
}

class EmailAddress {
    public static EmailAddress create(String email) {
        return null;
    }
}

class ZipCode {
    public static ZipCode create(String zipCode) {
        return null;
    }
}

// Data classes
class PersonalName {
    private final String50 firstName;
    private final String50 lastName;

    public PersonalName(String50 firstName, String50 lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

class CustomerInfo {
    private final PersonalName name;
    private final EmailAddress emailAddress;

    public CustomerInfo(PersonalName name, EmailAddress emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }
}

class Address {
    private final String50 addressLine1;
    private final Optional<String50> addressLine2;
    private final String50 city;
    private final ZipCode zipCode;

    public Address(String50 addressLine1, Optional<String50> addressLine2, String50 city, ZipCode zipCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.zipCode = zipCode;
    }
}

// Placeholder classes (you would need to implement these)
class UnvalidatedOrder {
    private String orderId;
    private UnvalidatedCustomerInfo unvalidatedCustomerInfo;
    private UnvalidatedAddress unvalidatedAddress;

    public String getOrderId() {
        return orderId;
    }

    public UnvalidatedCustomerInfo getCustomerInfo() {
        return unvalidatedCustomerInfo;
    }

    public UnvalidatedAddress getShippingAddress() {
        return unvalidatedAddress;
    }
}

class ValidatedOrder {
    public ValidatedOrder(OrderId orderId, CustomerInfo customerInfo, Address shippingAddress) {

    }
}

class UnvalidatedCustomerInfo {
    public String getFirstName() {
        return null;
    }

    public String getLastName() {
        return null;
    }

    public String getEmailAddress() {
        return null;
    }
}

class UnvalidatedAddress {
}

class CheckedAddress {
    public String getAddressLine1() {
        return null;
    }

    public String getAddressLine2() {
        return null;
    }

    public String getCity() {
        return null;
    }

    public String getZipCode() {
        return null;
    }
}

class UnvalidatedOrderLine {
    public String getProductCode() {
        return null;
    }
}

class ValidatedOrderLine {
}

class ProductCode {
}

