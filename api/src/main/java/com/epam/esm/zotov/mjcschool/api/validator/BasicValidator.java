package com.epam.esm.zotov.mjcschool.api.validator;

import java.math.BigDecimal;

public class BasicValidator {
    public static void validatePositiveNumber(BigDecimal... number) {
        for (BigDecimal temp : number) {
            if (-1 == temp.compareTo(new BigDecimal(0))) {
                throw new IllegalArgumentException();
            }
        }
    }
}