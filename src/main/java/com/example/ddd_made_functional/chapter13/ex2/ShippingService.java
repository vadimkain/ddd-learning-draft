package com.example.ddd_made_functional.chapter13.ex2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;

@Data
@AllArgsConstructor
class PricedOrder {
    private final String orderId;
    private final double price;
}

class PricedOrderWithShippingInfo extends PricedOrder {
    private final ShippingInfo shippingInfo;

    public PricedOrderWithShippingInfo(String orderId, double price, ShippingInfo shippingInfo) {
        super(orderId, price);
        this.shippingInfo = shippingInfo;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }
}

@Data
@AllArgsConstructor
class ShippingInfo {
    private final ShippingMethod shippingMethod;
    private final double shippingCost;
}

enum ShippingMethod {
    POSTAL_SERVICE,
    FEDEX_24,
    FEDEX_48,
    UPS_48;
}

@FunctionalInterface
interface AddShippingInfoToOrder {
    PricedOrderWithShippingInfo apply(PricedOrder pricedOrder);
}


public class ShippingService {

    public static AddShippingInfoToOrder addShippingInfoToOrder(Function<PricedOrder, Double> calculateShippingCost) {
        return pricedOrder -> {
            // Example: Create the shipping info
            ShippingInfo shippingInfo = new ShippingInfo(ShippingMethod.FEDEX_24, calculateShippingCost.apply(pricedOrder));

            // Add it to the order
            return new PricedOrderWithShippingInfo(pricedOrder.getOrderId(), pricedOrder.getPrice(), shippingInfo);
        };
    }

    public static void main(String[] args) {
        // Example of implement calculateShippingCost
        Function<PricedOrder, Double> calculateShippingCost = pricedOrder -> 10.0;

        // Getting function addShippingInfoToOrder
        AddShippingInfoToOrder addShippingInfoToOrder = addShippingInfoToOrder(calculateShippingCost);

        // Создание объекта PricedOrder
        PricedOrder pricedOrder = new PricedOrder("order123", 100.0);

        // Применение функции
        PricedOrderWithShippingInfo result = addShippingInfoToOrder.apply(pricedOrder);

        // Вывод результата
        System.out.println("Order ID: " + result.getOrderId());
        System.out.println("Price: " + result.getPrice());
        System.out.println("Shipping Method: " + result.getShippingInfo().getShippingMethod());
        System.out.println("Shipping Cost: " + result.getShippingInfo().getShippingCost());
    }
}
