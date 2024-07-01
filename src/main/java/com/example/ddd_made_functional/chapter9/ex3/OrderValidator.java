package com.example.ddd_made_functional.chapter9.ex3;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderValidator {
    public static CustomerInfo toCustomerInfo(UnvalidatedCustomerInfo customer) {
        String50 firstName = String50.create(customer.getFirstName());
        String50 lastName = String50.create(customer.getLastName());
        EmailAddress emailAddress = EmailAddress.create(customer.getEmailAddress());

        PersonalName name = new PersonalName(firstName, lastName);
        return new CustomerInfo(name, emailAddress);
    }

    public static Address toAddress(CheckAddressExists checkAddressExists, UnvalidatedAddress unvalidatedAddress) {
        CheckedAddress checkedAddress = checkAddressExists.check(unvalidatedAddress);

        String50 addressLine1 = String50.create(checkedAddress.getAddressLine1());
        Optional<String50> addressLine2 = String50.createOption(checkedAddress.getAddressLine2());
        String50 city = String50.create(checkedAddress.getCity());
        ZipCode zipCode = ZipCode.create(checkedAddress.getZipCode());

        return new Address(addressLine1, addressLine2, city, zipCode);
    }

    public static ValidatedOrder validateOrder(CheckProductCodeExists checkProductCodeExists,
                                               CheckAddressExists checkAddressExists,
                                               UnvalidatedOrder unvalidatedOrder) {
        OrderId orderId = OrderId.create(unvalidatedOrder.getOrderId());
        CustomerInfo customerInfo = toCustomerInfo(unvalidatedOrder.getCustomerInfo());
        Address shippingAddress = toAddress(checkAddressExists, unvalidatedOrder.getShippingAddress());
        Address billingAddress = toAddress(checkAddressExists, unvalidatedOrder.getBillingAddress());

        List<ValidatedOrderLine> validatedLines = unvalidatedOrder.getLines().stream()
                .map(line -> toValidatedOrderLine(checkProductCodeExists, line))
                .collect(Collectors.toList());

        return new ValidatedOrder(orderId, customerInfo, shippingAddress, billingAddress, validatedLines);
    }

    private static ValidatedOrderLine toValidatedOrderLine(CheckProductCodeExists checkProductCodeExists,
                                                           UnvalidatedOrderLine unvalidatedOrderLine) {
        if (!checkProductCodeExists.check(unvalidatedOrderLine.getProductCode())) {
            throw new IllegalArgumentException("Invalid product code: " + unvalidatedOrderLine.getProductCode());
        }

        return new ValidatedOrderLine(unvalidatedOrderLine.getProductCode(), unvalidatedOrderLine.getQuantity());
    }

}
