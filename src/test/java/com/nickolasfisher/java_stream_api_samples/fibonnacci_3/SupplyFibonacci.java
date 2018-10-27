package com.nickolasfisher.java_stream_api_samples.fibonnacci_3;

import java.util.function.Supplier;

public class SupplyFibonacci implements Supplier<Integer> {

    private int[] lastTwoFibs = {0, 1};

    private boolean returnedFirstFib;
    private boolean returnedSecondFib;

    @Override
    public Integer get() {
        if (!returnedFirstFib) {
            returnedFirstFib = true;
            return 0;
        }

        if (!returnedSecondFib) {
            returnedSecondFib = true;
            return 1;
        }

        int nextFib = lastTwoFibs[0] + lastTwoFibs[1];
        lastTwoFibs[0] = lastTwoFibs[1];
        lastTwoFibs[1] = nextFib;
        return nextFib;
    }
}
