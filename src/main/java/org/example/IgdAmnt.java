package org.example;

public class IgdAmnt {
    double amount;
    String unit;

    public IgdAmnt(double amnt, String u) {
        setAmount(amnt);
        setUnit(u);
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
