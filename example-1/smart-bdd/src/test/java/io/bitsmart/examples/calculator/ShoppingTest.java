package io.bitsmart.examples.calculator;

import io.bitsmart.bdd.report.junit5.results.extension.SmartReport;
import io.cucumber.examples.calculator.RpnCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.bitsmart.examples.calculator.ShoppingTest.Grocery.item;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SmartReport.class)
public class ShoppingTest {
    private final RpnCalculator calculator = new RpnCalculator();

    @Test
    void giveCorrectChange() {
        givenTheFollowingGroceries(List.of(
            item("milk", 9),
            item("bread", 7),
            item("soap", 5)
        ));
        whenIPay(25);
        myChangeShouldBe(4);
    }

    public void whenIPay(int amount) {
        calculator.push(amount);
        calculator.push("-");
    }

    public void myChangeShouldBe(int change) {
        assertThat(-calculator.value().intValue()).isEqualTo(change);
    }

    public void givenTheFollowingGroceries(List<Grocery> groceries) {
        for (Grocery grocery : groceries) {
            calculator.push(grocery.getPrice());
            calculator.push("+");
        }
    }

    static class Grocery {
        private final String name;
        private final Integer price;

        public Grocery(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public static Grocery item(String name, Integer price) {
            return new Grocery(name, price);
        }

        public Integer getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }
    }
}
