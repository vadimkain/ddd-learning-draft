package com.example.ddd_made_functional.chapter13.ex3;

import java.util.function.Function;

public class Pricing {
    public static void main(String[] args) {
        // Пример создания объектов для использования
        PromotionCode promotionCode = new PromotionCode(/* инициализация объекта PromotionCode */);
        ValidatedOrder validatedOrder = new ValidatedOrder();
        validatedOrder.setPricingMethod(PricingMethod.PROMOTION); // Пример установки PricingMethod

        // Пример создания объекта GetPricingFunction
        GetPricingFunction getPricingFunction = pricingMethod -> {
            switch (pricingMethod) {
                case STANDARD:
                    return productCode -> {
                        // Логика получения стандартной цены для productCode
                        return 100.0; // Пример возвращаемой цены
                    };
                case PROMOTION:
                    return productCode -> {
                        // Логика получения цены с применением промо-кода для productCode
                        return 80.0; // Пример возвращаемой цены с промо-акцией
                    };
                default:
                    throw new IllegalArgumentException("Unsupported pricing method: " + pricingMethod);
            }
        };

        // Пример использования PriceOrder для получения PricedOrder
        PriceOrder priceOrder = PriceOrder.create(getPricingFunction);
        PricedOrder pricedOrder = priceOrder.apply(validatedOrder);

        // Вывод результата или дальнейшая обработка pricedOrder
        System.out.println("Priced Order: " + pricedOrder);
    }
}

/**
 * <pre><code>
 * type PricingMethod =
 * | Standard
 * | Promotion of PromotionCode
 *
 * type ValidatedOrder = {
 * PricingMethod : PricingMethod
 * }
 *
 * type GetPricingFunction = PricingMethod -> GetProductPrice
 *
 * type PriceOrder =
 * GetPricingFunction // новая зависимость
 * -> ValidatedOrder // вход
 * -> PricedOrder // выход
 * </code></pre>
 */

enum PricingMethod {
    STANDARD,
    PROMOTION
}

class PromotionCode {
    // Implementation details for PromotionCode
}

class ValidatedOrder {
    // Other fields as before
    private PricingMethod pricingMethod;

    public void setPricingMethod(PricingMethod pricingMethod) {
        this.pricingMethod = pricingMethod;
    }

    // Constructor, getters, and setters
}

@FunctionalInterface
interface GetProductPrice {
    double apply(String productCode);
}

@FunctionalInterface
interface GetPricingFunction extends Function<PricingMethod, GetProductPrice> {
}

@FunctionalInterface
interface PriceOrder extends Function<ValidatedOrder, PricedOrder> {
    static PriceOrder create(GetPricingFunction getPricingFunction) {
        return validatedOrder -> {
            // Implementation of pricing logic
            // This is where you would use the getPricingFunction and validatedOrder
            // to create and return a PricedOrder
            return new PricedOrder();
        };
    }
}

class PricedOrder {
    // Implementation of PricedOrder
}


