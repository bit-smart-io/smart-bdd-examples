package io.cucumber.examples.calculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingSteps {

    private final ShoppingService shoppingService = new ShoppingService();
    private PayBuilder payBuilder = new PayBuilder();

    @Given("the following groceries:")
    public void the_following_groceries(List<Grocery> groceries) {
        for (Grocery grocery : groceries) {
            shoppingService.calculatorPush(grocery.getPrice().getValue());
            shoppingService.calculatorPush("+");
        }
    }

    @When("I pay {int}")
    public void i_pay(int amount) {
        payBuilder.withAmount(amount);
    }

    @When("I pay with currency {int} {string}")
    public void i_pay_with_currency(Integer amount, String currency) {
        payBuilder.withAmount(amount).withCurrency(currency);
    }

    @When("with currency {string}")
    public void i_pay_with_currency(String currency) {
        payBuilder.withCurrency(currency);
    }

    @Then("my change should be {}")
    public void my_change_should_be_(int change) {
        pay();
        assertEquals(-shoppingService.calculatorValue().intValue(), change);
    }

    private void pay() {
        final Pay pay = payBuilder.build();
        shoppingService.calculatorPushWithCurrency(pay.getAmount(), pay.getCurrency());
        shoppingService.calculatorPush("-");
    }

    static class Grocery {
        private String name;
        private Price price;

        public Price getPrice() {
            return price;
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static final class Price {
        private final int value;

        Price(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
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

        static Price fromString(String value) {
            return new Price(Integer.parseInt(value));
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
