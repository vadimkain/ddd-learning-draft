package com.example.railway_oriented_programming.ex3;

import reactor.core.publisher.Mono;

/**
 * <pre><code>
 * type Result<'SuccessType, 'ErrorType> = | Ok of 'SuccessType | Error of 'ErrorType
 * </code></pre>
 * @param <TSuccess>
 * @param <TError>
 */
abstract class Result<TSuccess, TError> {

    private Result() {
    }

    public static final class Ok<TSuccess, TError> extends Result<TSuccess, TError> {
        private final TSuccess value;

        public Ok(TSuccess value) {
            this.value = value;
        }

        public TSuccess getValue() {
            return value;
        }
    }

    public static final class Error<TSuccess, TError> extends Result<TSuccess, TError> {
        private final TError error;

        public Error(TError error) {
            this.error = error;
        }

        public TError getError() {
            return error;
        }
    }
}

public class ResultExample {

    public static void main(String[] args) {
        Mono<Result<String, String>> successfulMono = Mono.just(new Result.Ok<String, String>("Success"));
        Mono<Result<String, String>> errorMono = Mono.just(new Result.Error<String, String>("Error"));

        successfulMono.subscribe(result -> {
            if (result instanceof Result.Ok) {
                System.out.println("Success: " + ((Result.Ok<String, String>) result).getValue());
            } else if (result instanceof Result.Error) {
                System.out.println("Error: " + ((Result.Error<String, String>) result).getError());
            }
        });

        errorMono.subscribe(result -> {
            if (result instanceof Result.Ok) {
                System.out.println("Success: " + ((Result.Ok<String, String>) result).getValue());
            } else if (result instanceof Result.Error) {
                System.out.println("Error: " + ((Result.Error<String, String>) result).getError());
            }
        });
    }
}
