package io.github.m1theus.simianapi.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final String REGEX = "[^A,T,G,C]";

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
        // ignore empty method
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (!isSquare(value)) {
            return false;
        }

        for (String str : value) {
            if (!hasValidSequence(str)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSquare(String[] arr) {
        List<char[]> square = new ArrayList<>();

        for (String a : arr) {
            square.add(a.toCharArray());
        }

        for (char[] a : square) {
            boolean isSquare = a.length % square.size() == 0;
            if (!isSquare) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasValidSequence(String sequence) {
        return !Pattern
                .compile(REGEX)
                .matcher(sequence)
                .find();
    }
}
