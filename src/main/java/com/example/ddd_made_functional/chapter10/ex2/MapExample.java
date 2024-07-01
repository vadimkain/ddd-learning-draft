package com.example.ddd_made_functional.chapter10.ex2;

import java.util.function.Function;

abstract class Result<T, E> {
    public abstract <U> Result<U, E> map(Function<? super T, ? extends U> mapper);

    public static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    public static <T, E> Result<T, E> error(E error) {
        return new Error<>(error);
    }

    public static final class Ok<T, E> extends Result<T, E> {
        private final T value;

        private Ok(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        @Override
        public <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
            return Result.ok(mapper.apply(value));
        }

        @Override
        public String toString() {
            return "Ok(" + value + ")";
        }
    }

    public static final class Error<T, E> extends Result<T, E> {
        private final E error;

        private Error(E error) {
            this.error = error;
        }

        public E getError() {
            return error;
        }

        @Override
        public <U> Result<U, E> map(Function<? super T, ? extends U> mapper) {
            return Result.error(error);
        }

        @Override
        public String toString() {
            return "Error(" + error + ")";
        }
    }
}


public class MapExample {
    public static void main(String[] args) {
        // Пример успешного результата
        Result<Integer, String> resultOk = Result.ok(42);

        // Применяем map функцию
        Result<String, String> mappedOk = resultOk.map(value -> "The answer is " + value);

        System.out.println(mappedOk);  // Ожидается: Ok(The answer is 42)

        // Пример ошибки
        Result<Integer, String> resultError = Result.error("Something went wrong");

        // Применяем map функцию
        Result<String, String> mappedError = resultError.map(value -> "The answer is " + value);

        System.out.println(mappedError);  // Ожидается: Error(Something went wrong)
    }
}
