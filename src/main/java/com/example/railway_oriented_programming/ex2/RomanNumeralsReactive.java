package com.example.railway_oriented_programming.ex2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * <pre><code>
 * let replace (oldValue:string) (newValue:string) (inputStr:string) = inputStr.Replace(oldValue, newValue)
 * let toRomanNumerals number =
 *     String.replicate number "I"
 *     |> replace "IIIII" "V"
 *     |> replace "VV" "X"
 *     |> replace "XXXXX" "L"
 *     |> replace "LL" "C"
 *     |> replace "CCCCC" "D"
 *     |> replace "DD" "M"
 *     // TIP: additional special forms should be
 *     // done highest to lowest
 *     |> replace "DCCCC" "CM"
 *     |> replace "CCCC" "CD"
 *     |> replace "LXXXX" "XC"
 *     |> replace "XXXX" "XL"
 *     |> replace "VIIII" "IX"
 *     |> replace "IIII" "IV"
 *
 * toRomanNumerals 12
 * toRomanNumerals 14
 * toRomanNumerals 1947
 *
 * // piping
 * [1..30] |> List.map toRomanNumerals |> String.concat ","
 *
 * // add logs
 * let toRomanNumerals_withLog number =
 *
 *     let logger step output =
 *         printfn "The output at step '%s' was %s" step output
 *         output  // return the output
 *
 *     String.replicate number "I"
 *     |> logger "at beginning"
 *     |> replace "IIIII" "V"
 *     |> replace "VV" "X"
 *     |> replace "XXXXX" "L"
 *     |> replace "LL" "C"
 *     |> replace "CCCCC" "D"
 *     |> replace "DD" "M"
 *     |> logger "before special cases"
 *
 *     // additional special forms
 *     |> replace "DCCCC" "CM"
 *     |> replace "CCCC" "CD"
 *     |> replace "LXXXX" "XC"
 *     |> replace "XXXX" "XL"
 *     |> replace "VIIII" "IX"
 *     |> replace "IIII" "IV"
 *     |> logger "final result"
 *
 * toRomanNumerals_withLog 4
 * toRomanNumerals_withLog 14
 * toRomanNumerals_withLog 19
 * </code></pre>
 */
public class RomanNumeralsReactive {

    private static String replace(String oldValue, String newValue, String inputStr) {
        return inputStr.replace(oldValue, newValue);
    }

    static Mono<String> toRomanNumerals(int number) {
        return Mono.just(String.join("", Collections.nCopies(number, "I")))
                .map(s -> replace("IIIII", "V", s))
                .map(s -> replace("VV", "X", s))
                .map(s -> replace("XXXXX", "L", s))
                .map(s -> replace("LL", "C", s))
                .map(s -> replace("CCCCC", "D", s))
                .map(s -> replace("DD", "M", s))
                .map(s -> replace("DCCCC", "CM", s))
                .map(s -> replace("CCCC", "CD", s))
                .map(s -> replace("LXXXX", "XC", s))
                .map(s -> replace("XXXX", "XL", s))
                .map(s -> replace("VIIII", "IX", s))
                .map(s -> replace("IIII", "IV", s));
    }

    // with logs
    static Mono<String> toRomanNumeralsWithLog(int number) {
        return Mono.just(String.join("", Collections.nCopies(number, "I")))
                .doOnNext(s -> System.out.println("The output at step 'at beginning' was " + s))
                .map(s -> replace("IIIII", "V", s))
                .map(s -> replace("VV", "X", s))
                .map(s -> replace("XXXXX", "L", s))
                .map(s -> replace("LL", "C", s))
                .map(s -> replace("CCCCC", "D", s))
                .map(s -> replace("DD", "M", s))
                .doOnNext(s -> System.out.println("The output at step 'before special cases' was " + s))
                .map(s -> replace("DCCCC", "CM", s))
                .map(s -> replace("CCCC", "CD", s))
                .map(s -> replace("LXXXX", "XC", s))
                .map(s -> replace("XXXX", "XL", s))
                .map(s -> replace("VIIII", "IX", s))
                .map(s -> replace("IIII", "IV", s))
                .doOnNext(s -> System.out.println("The output at step 'final result' was " + s));
    }

    public static void main(String[] args) {
        toRomanNumerals(12).subscribe(result -> System.out.println("Result: " + result)); // XII
        toRomanNumerals(14).subscribe(result -> System.out.println("Result: " + result)); // XIV
        toRomanNumerals(1947).subscribe(result -> System.out.println("Result: " + result)); // MCMXLVII

        System.out.println();

        // piping
        Flux.range(1, 30)
                .flatMap(RomanNumeralsReactive::toRomanNumerals)
                .collectList()
                .map(list -> String.join(",", list))
                .subscribe(result -> System.out.println("Piped Result: " + result));

        System.out.println();

        // with logging
        toRomanNumeralsWithLog(4).subscribe(result -> System.out.println("Final Result with Log: " + result)); // IV
        toRomanNumeralsWithLog(14).subscribe(result -> System.out.println("Final Result with Log: " + result)); // XIV
        toRomanNumeralsWithLog(19).subscribe(result -> System.out.println("Final Result with Log: " + result)); // XIX
    }
}