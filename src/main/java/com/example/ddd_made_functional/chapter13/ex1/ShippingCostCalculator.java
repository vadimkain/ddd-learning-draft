package com.example.ddd_made_functional.chapter13.ex1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
class Address {
    private final String country;
    private final String state;
}


interface AddressPattern {
    Optional<State> match(Address address);
}

class UsLocalStatePattern implements AddressPattern {
    @Override
    public Optional<State> match(Address address) {
        if ("US".equals(address.getCountry())) {
            switch (address.getState()) {
                case "CA":
                case "OR":
                case "AZ":
                case "NV":
                    return Optional.of(State.US_LOCAL);
                default:
                    return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

class UsRemoteStatePattern implements AddressPattern {
    @Override
    public Optional<State> match(Address address) {
        if ("US".equals(address.getCountry())) {
            switch (address.getState()) {
                case "CA":
                case "OR":
                case "AZ":
                case "NV":
                    return Optional.empty();
                default:
                    return Optional.of(State.US_REMOTE);
            }
        }
        return Optional.empty();
    }
}

class InternationalPattern implements AddressPattern {
    @Override
    public Optional<State> match(Address address) {
        if (!"US".equals(address.getCountry())) {
            return Optional.of(State.INTERNATIONAL);
        }
        return Optional.empty();
    }
}

enum State {
    US_LOCAL, US_REMOTE, INTERNATIONAL
}


public class ShippingCostCalculator {
    private final List<AddressPattern> patterns = List.of(
            new UsLocalStatePattern(),
            new UsRemoteStatePattern(),
            new InternationalPattern()
    );

    public double calculateShippingCost(Address address) {
        for (AddressPattern pattern : patterns) {
            Optional<State> state = pattern.match(address);
            if (state.isPresent()) {
                switch (state.get()) {
                    case US_LOCAL:
                        return 5.0;
                    case US_REMOTE:
                        return 10.0;
                    case INTERNATIONAL:
                        return 20.0;
                }
            }
        }
        throw new IllegalArgumentException("Unrecognized address");
    }

    public static void main(String[] args) {
        ShippingCostCalculator calculator = new ShippingCostCalculator();

        Address address1 = new Address("US", "CA");
        System.out.println("Shipping cost: " + calculator.calculateShippingCost(address1)); // Ожидается: 5.0

        Address address2 = new Address("US", "TX");
        System.out.println("Shipping cost: " + calculator.calculateShippingCost(address2)); // Ожидается: 10.0

        Address address3 = new Address("Canada", "ON");
        System.out.println("Shipping cost: " + calculator.calculateShippingCost(address3)); // Ожидается: 20.0
    }
}

