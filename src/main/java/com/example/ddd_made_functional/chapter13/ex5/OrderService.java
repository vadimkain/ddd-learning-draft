package com.example.ddd_made_functional.chapter13.ex5;

import java.time.LocalDateTime;
import java.util.function.Supplier;

class Order {
}

class Result<T, E> {
    public static Result<Order, PlaceOrderError> failure(PlaceOrderError placeOrderError) {
        return null;
    }
}

class UnvalidatedOrder {
}

/**
 * <pre><code>
 * /// Определяем рабочие часы
 * let isBusinessHour hour =
 *     hour >= 9 && hour <= 17
 *
 * /// Преобразователь
 * let businessHoursOnly getHour onError onSuccess =
 *     let hour = getHour()
 *     if isBusinessHour hour then
 *         onSuccess()
 *     else
 *         onError()
 *
 * type PlaceOrderError =
 *     | Validation of ValidationError
 *     ...
 *     | OutsideBusinessHours //новый!
 *
 * let placeOrder unvalidatedOrder =
 *     ...
 * let placeOrderInBusinessHours unvalidatedOrder =
 *     let onError() =
 *         Error OutsideBusinessHours
 *     let onSuccess() =
 *         placeOrder unvalidatedOrder
 *     let getHour() = DateTime.Now.Hour
 *     businessHoursOnly getHour onError onSuccess
 * </code></pre>
 */
// Define business hours
class BusinessHours {
    public static boolean isBusinessHour(int hour) {
        return hour >= 9 && hour <= 17;
    }

    // Converter
    public static <T> T businessHoursOnly(Supplier<Integer> getHour, Supplier<T> onError, Supplier<T> onSuccess) {
        int hour = getHour.get();
        if (isBusinessHour(hour)) {
            return onSuccess.get();
        } else {
            return onError.get();
        }
    }
}

enum PlaceOrderError {
    Validation,
    // ...
    OutsideBusinessHours // new!
}

public class OrderService {
    public Result<Order, PlaceOrderError> placeOrder(UnvalidatedOrder unvalidatedOrder) {
        // Implementation of placeOrder
        // ...
        return null;
    }

    public Result<Order, PlaceOrderError> placeOrderInBusinessHours(UnvalidatedOrder unvalidatedOrder) {
        Supplier<Result<Order, PlaceOrderError>> onError = () -> Result.failure(PlaceOrderError.OutsideBusinessHours);
        Supplier<Result<Order, PlaceOrderError>> onSuccess = () -> placeOrder(unvalidatedOrder);
        Supplier<Integer> getHour = () -> LocalDateTime.now().getHour();

        return BusinessHours.businessHoursOnly(getHour, onError, onSuccess);
    }
}



