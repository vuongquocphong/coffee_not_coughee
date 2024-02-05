package com.example.coffee_not_coughee.Model;

public class CoffeeModel {
    public int coffeeImage;
    public String coffeeName;
    public float coffeePrice;
    public int exchangePoints;
    public float coffeeBasePrice;
    public String address;

    public enum ShotType {
        SINGLE, DOUBLE
    }

    public enum CoffeeSize {
        SMALL, MEDIUM, LARGE;

    }

    public enum IceType {
        LESS, NORMAL, MORE
    }

    public ShotType shotType = ShotType.SINGLE;
    public CoffeeSize coffeeSize;
    public IceType iceType;

    public boolean isTakeAway = false;

    public int quantity = 1;

    public CoffeeModel() {

    }

    public CoffeeModel(int coffeeImage, String coffeeName, float coffeePrice, int exchangePoints) {
        this.coffeeImage = coffeeImage;
        this.coffeeName = coffeeName;
        this.coffeePrice = coffeePrice;
        this.coffeeBasePrice = coffeePrice;
        this.exchangePoints = exchangePoints;
        this.shotType = ShotType.SINGLE;
        this.coffeeSize = CoffeeSize.MEDIUM;
        this.iceType = IceType.NORMAL;
        this.isTakeAway = false;
    }

    public String getCoffeeDetails() {
        String details = "";
        details += shotType == ShotType.SINGLE ? "single" : "double";
        details += " | ";
        details += isTakeAway ? "away" : "dine-in";
        details += " | ";
        details += coffeeSize == CoffeeSize.SMALL ? "small" : coffeeSize == CoffeeSize.MEDIUM ? "medium" : "large";
        details += " | ";
        details += iceType == IceType.LESS ? "less" : iceType == IceType.NORMAL ? "normal" : "much";
        return details;
    }
}

