package com.example.coffee_not_coughee.Model;

import java.util.ArrayList;
import java.util.List;

public class CartModel {
    private List<CoffeeModel> items;

    public CartModel() {
        this.items = new ArrayList<>();
    }

    public List<CoffeeModel> getItems() {
        return items;
    }

    public void addItem(CoffeeModel item) {
        items.add(item);
    }

    public void removeItem(CoffeeModel item) {
        items.remove(item);
    }

    public void clearCart() {
        items.clear();
    }
}
