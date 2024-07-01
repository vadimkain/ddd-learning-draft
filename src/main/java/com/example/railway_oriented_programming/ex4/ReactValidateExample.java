package com.example.railway_oriented_programming.ex4;

import reactor.core.publisher.Mono;

/**
 * <pre><code>
 * // Some data to validate
 * type Input = {
 *    Name : string
 *    Email : string
 * }
 *
 * //-------------------------------------
 * // Here are the validation functions
 * //-------------------------------------
 *
 * let checkNameNotBlank input =
 *   if input.Name = "" then
 *     Error "Name must not be blank"
 *   else
 *     Ok input
 *
 * let checkName50 input =
 *   if input.Name.Length > 50 then
 *     Error "Name must not be longer than 50 chars"
 *   else
 *     Ok input
 *
 * let checkEmailNotBlank input =
 *   if input.Email = "" then
 *     Error "Email must not be blank"
 *   else
 *     Ok input
 *
 * //-------------------------------------
 * // Chain these functions together - the nice way
 * //-------------------------------------
 *
 * let validateInput input =
 *     input
 *     |> checkNameNotBlank
 *     |> Result.bind checkName50
 *     |> Result.bind checkEmailNotBlank
 *
 *
 * // -------------------------------
 * // test that the validation works
 * // -------------------------------
 *
 * let goodInput = {Name="Scott";Email="x@example.com"}
 * validateInput goodInput
 *
 * let blankName = {Name="";Email="x@example.com"}
 * validateInput blankName
 *
 * let blankEmail = {Name="Scott";Email=""}
 * validateInput blankEmail
 * </code></pre>
 */
class Input {
    private String name;
    private String email;

    public Input(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

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

class Validator {

    public static Mono<Result<Input, String>> checkNameNotBlank(Input input) {
        if (input.getName().isEmpty()) {
            return Mono.just(new Result.Error<>("Name must not be blank"));
        } else {
            return Mono.just(new Result.Ok<>(input));
        }
    }

    public static Mono<Result<Input, String>> checkName50(Input input) {
        if (input.getName().length() > 50) {
            return Mono.just(new Result.Error<>("Name must not be longer than 50 chars"));
        } else {
            return Mono.just(new Result.Ok<>(input));
        }
    }

    public static Mono<Result<Input, String>> checkEmailNotBlank(Input input) {
        if (input.getEmail().isEmpty()) {
            return Mono.just(new Result.Error<>("Email must not be blank"));
        } else {
            return Mono.just(new Result.Ok<>(input));
        }
    }

    public static Mono<Result<Input, String>> validateInput(Input input) {
        return checkNameNotBlank(input)
                .flatMap(result -> {
                    if (result instanceof Result.Ok) {
                        return checkName50(((Result.Ok<Input, String>) result).getValue());
                    } else {
                        return Mono.just(result);
                    }
                })
                .flatMap(result -> {
                    if (result instanceof Result.Ok) {
                        return checkEmailNotBlank(((Result.Ok<Input, String>) result).getValue());
                    } else {
                        return Mono.just(result);
                    }
                });
    }

}

public class ReactValidateExample {
    public static void main(String[] args) {
        Input goodInput = new Input("Scott", "x@example.com");
        Validator.validateInput(goodInput)
                .subscribe(result -> {
                    if (result instanceof Result.Ok) {
                        System.out.println("Validation passed: " + ((Result.Ok<Input, String>) result).getValue());
                    } else if (result instanceof Result.Error) {
                        System.out.println("Validation failed: " + ((Result.Error<Input, String>) result).getError());
                    }
                });

        Input blankName = new Input("", "x@example.com");
        Validator.validateInput(blankName)
                .subscribe(result -> {
                    if (result instanceof Result.Ok) {
                        System.out.println("Validation passed: " + ((Result.Ok<Input, String>) result).getValue());
                    } else if (result instanceof Result.Error) {
                        System.out.println("Validation failed: " + ((Result.Error<Input, String>) result).getError());
                    }
                });

        Input blankEmail = new Input("Scott", "");
        Validator.validateInput(blankEmail)
                .subscribe(result -> {
                    if (result instanceof Result.Ok) {
                        System.out.println("Validation passed: " + ((Result.Ok<Input, String>) result).getValue());
                    } else if (result instanceof Result.Error) {
                        System.out.println("Validation failed: " + ((Result.Error<Input, String>) result).getError());
                    }
                });

    }
}
