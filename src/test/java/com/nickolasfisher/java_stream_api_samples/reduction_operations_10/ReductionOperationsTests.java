package com.nickolasfisher.java_stream_api_samples.reduction_operations_10;

import com.nickolasfisher.java_stream_api_samples.SimplePair;
import com.nickolasfisher.java_stream_api_samples.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

public class ReductionOperationsTests {

    List<SimplePair> pairs;

    @Before
    public void setupSimplePairs() {
        pairs = TestUtils.generateSimplePairs(5);
    }

    @Test
    public void reduce_sumAllIds() {
        // sums like x_0 + x_1 + ... + x_n:
        Optional<Integer> idsSummed = pairs.stream()
                .map(SimplePair::getId)
                .reduce((firstId, secondId) -> firstId + secondId);

        assertEquals(15, idsSummed.orElseThrow(RuntimeException::new).intValue());
    }

    @Test
    public void reduce_multiplyAllIds() {
        Optional<Integer> idsMultiplied = pairs.stream()
                .map(SimplePair::getId)
                .reduce((firstId, secondId) -> firstId * secondId);

        assertEquals(1 * 2 * 3 * 4 * 5, idsMultiplied.orElseThrow(RuntimeException::new).intValue());
    }

    @Test
    public void reduce_usingIdentityValue() {
        Integer idsSummedWithIdentity = pairs.stream()
                .map(SimplePair::getId)
                .reduce(10, (firstId, secondId) -> firstId + secondId);

        assertEquals(25, idsSummedWithIdentity.intValue());
    }
}
