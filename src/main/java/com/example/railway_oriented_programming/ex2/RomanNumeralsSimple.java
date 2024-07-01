package com.example.railway_oriented_programming.ex2;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
public class RomanNumeralsSimple {
    public static String replace(String oldValue, String newValue, String inputStr) {
        return inputStr.replace(oldValue, newValue);
    }

    public static String toRomanNumerals(int number) {
        String result = new String(new char[number]).replace("\0", "I");
//        String result = "I".repeat(number);
        result = replace("IIIII", "V", result);
        result = replace("VV", "X", result);
        result = replace("XXXXX", "L", result);
        result = replace("LL", "C", result);
        result = replace("CCCCC", "D", result);
        result = replace("DD", "M", result);
        // TIP: additional special forms should be
        // done highest to lowest
        result = replace("DCCCC", "CM", result);
        result = replace("CCCC", "CD", result);
        result = replace("LXXXX", "XC", result);
        result = replace("XXXX", "XL", result);
        result = replace("VIIII", "IX", result);
        result = replace("IIII", "IV", result);
        return result;
    }

    public static String toRomanNumerals_withLog(int number) {
//        String result = "I".repeat(number);
        String result = new String(new char[number]).replace("\0", "I");
        result = logger("at beginning", result);
        result = replace("IIIII", "V", result);
        result = replace("VV", "X", result);
        result = replace("XXXXX", "L", result);
        result = replace("LL", "C", result);
        result = replace("CCCCC", "D", result);
        result = replace("DD", "M", result);
        result = logger("before special cases", result);
        // additional special forms
        result = replace("DCCCC", "CM", result);
        result = replace("CCCC", "CD", result);
        result = replace("LXXXX", "XC", result);
        result = replace("XXXX", "XL", result);
        result = replace("VIIII", "IX", result);
        result = replace("IIII", "IV", result);
        return logger("final result", result);
    }

    private static String logger(String step, String output) {
        System.out.printf("The output at step '%s' was %s%n", step, output);
        return output;
    }

    public static void main(String[] args) {
        System.out.println(toRomanNumerals(12));
        System.out.println(toRomanNumerals(14));
        System.out.println(toRomanNumerals(1947));

        // piping equivalent
        String result = IntStream.rangeClosed(1, 30)
                .mapToObj(RomanNumeralsSimple::toRomanNumerals)
                .collect(Collectors.joining(","));
        System.out.println(result);

        toRomanNumerals_withLog(4);
        toRomanNumerals_withLog(14);
        toRomanNumerals_withLog(19);
    }
}



