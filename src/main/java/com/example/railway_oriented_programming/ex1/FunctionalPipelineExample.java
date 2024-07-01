package com.example.railway_oriented_programming.ex1;

import java.util.function.Function;

/**
 * <pre><code>
 * let add1 x = x+1
 * let double x = x + x
 * let square x = x * x
 *
 * 5 |> add1 |> double |> square
 * </code></pre>
 */
public class FunctionalPipelineExample {
    public static void main(String[] args) {
        // Определение функций
        Function<Integer, Integer> add1 = x -> x + 1;
        Function<Integer, Integer> doubleValue = x -> x + x;
        Function<Integer, Integer> square = x -> x * x;

        // Использование функций в цепочке
        // Также подойдет и для let add1_double_square = add1 >> double >> square
        int result = add1
                .andThen(doubleValue)
                .andThen(square)
                .apply(5);

        // Вывод результата
        System.out.println(result); // Выведет 144
    }
}
