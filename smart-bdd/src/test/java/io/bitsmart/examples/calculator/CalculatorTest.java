package io.bitsmart.examples.calculator;

import io.bitsmart.bdd.report.junit5.results.extension.SmartReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SmartReport.class)
public class CalculatorTest {

    @Test
    void firstTest() {
        assertThat(true).isTrue();
    }
}
