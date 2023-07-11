package io.bitsmart.examples.calculator;

import io.bitsmart.bdd.report.junit5.results.extension.SmartReport;
import io.cucumber.examples.calculator.ShoppingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.bitsmart.examples.calculator.ShoppingTest.Grocery.item;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SmartReport.class)
public class ShoppingTest {
    private final ShoppingService shoppingService = new ShoppingService();
    private PayBuilder payBuilder = new PayBuilder();

    @Test
    void giveCorrectChange() {
        givenTheFollowingGroceries(List.of(
            item("milk", 9),
            item("bread", 7),
            item("soap", 5)));
        whenIPay(25);
        myChangeShouldBe(4);
    }

    @Test
    void giveCorrectChangeWhenCurrencyIsDollars() {
        givenTheFollowingGroceries(List.of(
            item("milk", 9),
            item("bread", 7),
            item("soap", 5)));
        whenIPay(25).withCurrency("Dollars");
        myChangeShouldBe(29);
    }

    public PayBuilder whenIPay(int amount) {
        return payBuilder.withAmount(amount);
    }

    public void myChangeShouldBe(int change) {
        pay();
        assertEquals(-shoppingService.calculatorValue().intValue(), change);
    }

    public void givenTheFollowingGroceries(List<Grocery> groceries) {
        for (Grocery grocery : groceries) {
            shoppingService.calculatorPush(grocery.getPrice());
            shoppingService.calculatorPush("+");
        }
    }

    private void pay() {
        final Pay pay = payBuilder.build();
        shoppingService.calculatorPushWithCurrency(pay.getAmount(), pay.getCurrency());
        shoppingService.calculatorPush("-");
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
    }

    class Pay {
        private final Integer amount;
        private final String currency;

        public Pay(Integer amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public String getCurrency() {
            return currency;
        }
    }

    class PayBuilder {
        private int amount = 0;
        private String currency = null;

        public PayBuilder() {}

        public Pay build() {
            return new Pay(amount, currency);
        }

        public PayBuilder withAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public PayBuilder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }
    }
}
