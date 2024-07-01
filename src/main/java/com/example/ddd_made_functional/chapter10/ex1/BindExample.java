package com.example.ddd_made_functional.chapter10.ex1;

import java.util.function.Function;

@FunctionalInterface
interface SwitchFunction<T, R> {
    R apply(T input);
}

public class BindExample {

    public static <T, R, E> Either<R, E> bind(SwitchFunction<T, Either<R, E>> switchFn, Either<T, E> twoTrackInput) {
        if (twoTrackInput.isLeft()) {
            return switchFn.apply(twoTrackInput.getLeft());
        } else {
            return Either.right(twoTrackInput.getRight());
        }
    }

    public static void main(String[] args) {
        Either<String, Integer> input1 = Either.left("string argument 1");
        Either<String, Integer> input2 = Either.right(123);
        Either<String, Integer> input3 = Either.left("123");
        Either<String, Integer> input4 = Either.right(12345);
        Either<String, Integer> input5 = Either.right(123456);

        SwitchFunction<String, Either<String, Integer>> switchFn = str -> {
            try {
                int number = Integer.parseInt(str);
                return Either.right(number);
            } catch (NumberFormatException e) {
                return Either.left("Failed to parse as integer");
            }
        };

        Either<String, Integer> result1 = bind(switchFn, input1);
        Either<String, Integer> result2 = bind(switchFn, input2);
        Either<String, Integer> result3 = bind(switchFn, input3);
        Either<String, Integer> result4 = bind(switchFn, input4);
        Either<String, Integer> result5 = bind(switchFn, input5);

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
        System.out.println("Result 3: " + result3);
        System.out.println("Result 4: " + result4);
        System.out.println("Result 5: " + result5);
    }
}


class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(value, null);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(null, value);
    }

    public <U> Either<U, R> mapLeft(Function<? super L, ? extends U> mapper) {
        if (left != null) {
            return Either.left(mapper.apply(left));
        } else {
            return Either.right(right);
        }
    }

    public <U> Either<L, U> mapRight(Function<? super R, ? extends U> mapper) {
        if (right != null) {
            return Either.right(mapper.apply(right));
        } else {
            return Either.left(left);
        }
    }

    public boolean isLeft() {
        return left != null;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public String toString() {
        if (left != null) {
            return "Left(" + left + ")";
        } else {
            return "Right(" + right + ")";
        }
    }
}
