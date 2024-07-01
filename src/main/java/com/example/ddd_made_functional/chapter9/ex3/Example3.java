package com.example.ddd_made_functional.chapter9.ex3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Example3 {
    public static void main(String[] args) {
//        Описываем логику функций по выполнению команды (они же зависимости)
        CheckProductCodeExists checkProductCodeExists = productCode -> productCode != null && !productCode.isEmpty();
        CheckAddressExists checkAddressExists = address -> {
            // Пример проверки и создания CheckedAddress
            return new CheckedAddress(address.getAddressLine1(), address.getAddressLine2(), address.getCity(), address.getZipCode());
        };

//        входные данные
        UnvalidatedOrder unvalidatedOrder = new UnvalidatedOrder("12345",
                new UnvalidatedCustomerInfo("John", "Doe", "john.doe@example.com"),
                new UnvalidatedAddress("123 Main St", "", "Springfield", "12345"),
                new UnvalidatedAddress("456 Elm St", "", "Shelbyville", "67890"),
                new ArrayList<>());

//      ValidatedOrder - выходные данные и результат валидации
        ValidatedOrder validatedOrder = OrderValidator.validateOrder(checkProductCodeExists, checkAddressExists, unvalidatedOrder);
    }

}

@FunctionalInterface
interface CheckProductCodeExists {
    boolean check(String productCode);
}

@FunctionalInterface
interface CheckAddressExists {
    CheckedAddress check(UnvalidatedAddress address);
}

@Data
@AllArgsConstructor
class UnvalidatedOrder {
    private String orderId;
    private UnvalidatedCustomerInfo customerInfo;
    private UnvalidatedAddress shippingAddress;
    private UnvalidatedAddress billingAddress;
    private List<UnvalidatedOrderLine> lines;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class ValidatedOrder {
    private OrderId orderId;
    private CustomerInfo customerInfo;
    private Address shippingAddress;
    private Address billingAddress;
    private List<ValidatedOrderLine> lines;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class UnvalidatedCustomerInfo {
    private String firstName;
    private String lastName;
    private String emailAddress;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class CustomerInfo {
    private PersonalName name;
    private EmailAddress emailAddress;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class UnvalidatedAddress {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zipCode;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class CheckedAddress {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String zipCode;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class Address {
    private String50 addressLine1;
    private Optional<String50> addressLine2;
    private String50 city;
    private ZipCode zipCode;

    // Конструкторы, геттеры и сеттеры
}

@Data
@AllArgsConstructor
class UnvalidatedOrderLine {
    private String productCode;
    private int quantity;
}

@Data
@AllArgsConstructor
class ValidatedOrderLine {
    private String productCode;
    private int quantity;
}

@Data
class OrderId {
    public static OrderId create(String id) {
        // Логика создания OrderId

        return null;
    }
}

@Data
class PersonalName {
    public PersonalName(String50 firstName, String50 lastName) {
    }
    // Поля и методы
}

@Data
class EmailAddress {
    public static EmailAddress create(String email) {
        // Логика создания EmailAddress
        return null;
    }
}

@Data
class String50 {
    public static String50 create(String value) {
        // Логика создания String50
        return null;
    }

    public static Optional<String50> createOption(String value) {
        // Логика создания Optional<String50>
        return null;
    }
}

@Data
class ZipCode {
    public static ZipCode create(String zip) {
        // Логика создания ZipCode
        return null;
    }
}


