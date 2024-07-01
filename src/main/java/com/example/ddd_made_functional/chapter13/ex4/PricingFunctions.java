package com.example.ddd_made_functional.chapter13.ex4;

import java.util.Map;
import java.util.function.Function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Enum для типа PricingMethod
enum PricingMethod {
    STANDARD,
    PROMOTION
}

// Классы для представления ProductCode и Price (предположим, что они определены)
class ProductCode {}
class Price {}

// Класс для представления PromotionCode (предположим, что он определен)
class PromotionCode {
    // Реализация класса PromotionCode
}

/**
 * <pre><code>
 * type GetStandardPriceTable =
 *     // без входных данных -> возвращаем стандартные цены
 *     unit -> IDictionary<ProductCode, Price>
 *
 * type GetPromotionPriceTable =
 *     // ввод промо -> возвращаем цены для промо
 *     PromotionCode -> IDictionary<ProductCode, Price>
 *
 * let getPricingFunction
 *     (standardPrices: GetStandardPriceTable)
 *     (promoPrices: GetPromotionPriceTable)
 *     : GetPricingFunction =
 *
 *     // оригинальная функция ценообразования
 *     let getStandardPrice : GetProductPrice =
 *         // кэширование стандартных цен
 *         let standardPricesCache = standardPrices()
 *
 *         // возвращаем функцию поиска
 *         fun productCode -> standardPricesCache.[productCode]
 *
 *     // функция ценообразования по промо
 *     let getPromotionPrice (promotionCode: PromotionCode) : GetProductPrice =
 *         // кэширование промо-цен
 *         let promotionPrices = promoPrices promotionCode
 *
 *         // возвращаем функцию поиска
 *         fun productCode ->
 *             match promotionPrices.TryGetValue productCode with
 *             | true, price -> price
 *             | false, _ -> getStandardPrice productCode
 *
 *     // возвращаем функцию, соответствующую типу GetPricingFunction
 *     fun pricingMethod ->
 *         match pricingMethod with
 *         | Standard -> getStandardPrice
 *         | Promotion promotionCode -> getPromotionPrice promotionCode
 * </code></pre>
 */

// Класс для представления PricingFunctions
public class PricingFunctions {

    // Функциональный интерфейс для GetStandardPriceTable
    @FunctionalInterface
    interface GetStandardPriceTable {
        Map<ProductCode, Price> apply();
    }

    // Функциональный интерфейс для GetPromotionPriceTable
    @FunctionalInterface
    interface GetPromotionPriceTable {
        Map<ProductCode, Price> apply(PromotionCode promotionCode);
    }

    // Функциональный интерфейс для GetProductPrice
    @FunctionalInterface
    interface GetProductPrice {
        Price apply(ProductCode productCode);
    }

    // Функциональный интерфейс для GetPricingFunction
    @FunctionalInterface
    interface GetPricingFunction {
        GetProductPrice apply(PricingMethod pricingMethod);
    }

    // Метод для создания GetPricingFunction
    public static GetPricingFunction getPricingFunction(
            GetStandardPriceTable standardPrices,
            GetPromotionPriceTable promoPrices) {

        // Original pricing function for standard prices
        GetProductPrice getStandardPrice = productCode -> {
            Map<ProductCode, Price> standardPricesCache = standardPrices.apply();
            return standardPricesCache.get(productCode);
        };

        // Pricing function for promotion prices
        Function<PromotionCode, GetProductPrice> getPromotionPrice = promotionCode -> {
            Map<ProductCode, Price> promotionPrices = promoPrices.apply(promotionCode);
            return productCode -> promotionPrices.getOrDefault(productCode, getStandardPrice.apply(productCode));
        };

        // Return function corresponding to GetPricingFunction type
        return pricingMethod -> {
            if (pricingMethod == PricingMethod.STANDARD) {
                return getStandardPrice;
            } else if (pricingMethod == PricingMethod.PROMOTION) {
                // Assuming you have a specific promotion code to use here
                PromotionCode promotionCode = new PromotionCode(); // Create a promotion code object
                return getPromotionPrice.apply(promotionCode);
            } else {
                throw new IllegalArgumentException("Unknown pricing method");
            }
        };
    }

    // Пример использования getPricingFunction
    public static void main(String[] args) {
        // Пример создания объектов GetStandardPriceTable и GetPromotionPriceTable
        GetStandardPriceTable getStandardPriceTable = () -> {
            Map<ProductCode, Price> standardPrices = new HashMap<>();
            // Логика заполнения стандартных цен
            return standardPrices;
        };

        GetPromotionPriceTable getPromotionPriceTable = promotionCode -> {
            Map<ProductCode, Price> promotionPrices = new HashMap<>();
            // Логика заполнения цен по промо-коду
            return promotionPrices;
        };

        // Получение функции GetPricingFunction
        GetPricingFunction getPricingFunction = getPricingFunction(getStandardPriceTable, getPromotionPriceTable);

        // Использование GetPricingFunction для получения цены
        GetProductPrice getProductPrice = getPricingFunction.apply(PricingMethod.STANDARD);
        Price price = getProductPrice.apply(new ProductCode());
        System.out.println("Price: " + price);
    }
}


