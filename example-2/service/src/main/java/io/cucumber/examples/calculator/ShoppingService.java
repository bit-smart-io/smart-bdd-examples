package io.cucumber.examples.calculator;

public class ShoppingService {
    private final RpnCalculator calc = new RpnCalculator();

    public void calculatorPush(Object arg) {
        calc.push(arg);
    }

    public void calculatorPushWithCurrency(Integer amount, String currency) {
        calc.push(amount * exchangeRate(currency));
    }

    public Number calculatorValue() {
        return calc.value();
    }

    private int exchangeRate(String currency) {
        if (currency == null) {
            return 1;
        }
        return 2;
    }
}
