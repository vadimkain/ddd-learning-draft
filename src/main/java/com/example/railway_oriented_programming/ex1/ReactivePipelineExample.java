package com.example.railway_oriented_programming.ex1;

import reactor.core.publisher.Mono;

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
public class ReactivePipelineExample {
    public static void main(String[] args) {
        // Определение функций
        Function<Integer, Integer> add1 = x -> x + 1;
        Function<Integer, Integer> doubleValue = x -> x + x;
        Function<Integer, Integer> square = x -> x * x;

        // Создание исходного Mono с начальным значением 5
        Mono<Integer> resultMono = Mono.just(5)
                .map(add1)
                .map(doubleValue)
                .map(square);

        // Подписка на результат и вывод его в консоль
        resultMono.subscribe(result -> System.out.println("Result: " + result));
    }
}
