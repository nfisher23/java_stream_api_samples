package com.nickolasfisher.java_stream_api_samples.creating_optionals_6;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Corresponding tutorial at https://nickolasfisher.com/blog/The-Java-Stream-API-Creating-Optional-Types
 */
public class CreatingOptionalsTests {

    private Optional<Double> sqrt(Double num) {
        if (num == null) {
            return Optional.ofNullable(num);
        } else if (num >= 0) {
            return Optional.of(Math.sqrt(num));
        } else {
            return Optional.empty();
        }
    }

    @Test
    public void optional_createPositive_works() {
        Double positive = 8.8;
        Optional<Double> sqrtPositive = sqrt(positive);

        assertTrue(sqrtPositive.isPresent());
    }

    @Test
    public void optional_createNegative_stillWorks() {
        Double negative = -6.5;
        Optional<Double> sqrtNegative = sqrt(negative);

        assertFalse(sqrtNegative.isPresent());
    }

    @Test
    public void optional_createNull_stillWorks() {
        Optional<Double> optionalNull = sqrt(null);
        assertFalse(optionalNull.isPresent());
    }

    private Optional<Double> log(Double num) {
        if (num != null && num > 0) {
            return Optional.of(Math.log(num));
        } else {
            return Optional.empty();
        }
    }

    @Test
    public void optional_getsRealValue() {
        Double positive = 1.0;
        Optional<Double> sqrtPositive = sqrt(positive);
        Optional<Double> realVal = sqrtPositive.flatMap(this::log);

        assertEquals(0, realVal.orElseThrow(RuntimeException::new).intValue());
    }

    @Test
    public void optional_composingMultipleCalls() {
        Double negative = -1.0;
        Optional<Double> emptySqrt = sqrt(negative);
        Optional<Double> doesntBlowUp = emptySqrt.flatMap(this::log);

        assertFalse(emptySqrt.isPresent());
        assertFalse(doesntBlowUp.isPresent());
    }

}
