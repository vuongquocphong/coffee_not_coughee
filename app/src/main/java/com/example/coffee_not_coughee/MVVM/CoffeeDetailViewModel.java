package com.example.coffee_not_coughee.MVVM;

import androidx.lifecycle.ViewModel;

import com.example.coffee_not_coughee.Model.CoffeeModel;

public class CoffeeDetailViewModel extends ViewModel {
    private CoffeeModel coffee;

    public CoffeeModel getCoffee() {
        return coffee;
    }

    public void setCoffee(CoffeeModel coffee) {
        this.coffee = coffee;
    }
}
