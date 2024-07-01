package com.example.ddd_made_functional.chapter9.ex1;

import reactor.core.publisher.Mono;

/**
 * <pre>
 * let placeOrder unvalidatedOrder =
 *   unvalidatedOrder
 *   |> validateOrder
 *   |> priceOrder
 *   |> acknowledgeOrder
 *   |> createEvents
 * </pre>
 */
public class OrderService {
    public Mono<Order> placeOrder(UnvalidatedOrder unvalidatedOrder) {
        return validateOrder(unvalidatedOrder)
                .flatMap(this::priceOrder)
                .flatMap(this::acknowledgeOrder)
                .flatMap(this::createEvents);
    }

    private Mono<ValidatedOrder> validateOrder(UnvalidatedOrder unvalidatedOrder) {
        // Implement validation logic
        return Mono.just(new ValidatedOrder(unvalidatedOrder));
    }

    private Mono<PricedOrder> priceOrder(ValidatedOrder validatedOrder) {
        // Implement pricing logic
        return Mono.just(new PricedOrder(validatedOrder));
    }

    private Mono<AcknowledgedOrder> acknowledgeOrder(PricedOrder pricedOrder) {
        // Implement acknowledgement logic
        return Mono.just(new AcknowledgedOrder(pricedOrder));
    }

    private Mono<Order> createEvents(AcknowledgedOrder acknowledgedOrder) {
        // Implement event creation logic
        return Mono.just(new Order(acknowledgedOrder));
    }

    private class Order {
        public Order(AcknowledgedOrder acknowledgedOrder) {

        }
    }

    private class UnvalidatedOrder { }

    private class ValidatedOrder {
        public ValidatedOrder(UnvalidatedOrder unvalidatedOrder) {

        }
    }


    private class PricedOrder {
        public PricedOrder(ValidatedOrder validatedOrder) {

        }
    }

    private class AcknowledgedOrder {
        public AcknowledgedOrder(PricedOrder pricedOrder) {

        }
    }
}