/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.icecream.business;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Roman Pelikh
 */
public class Topping implements Serializable {

    private int id;
    private String name;
    private double price;

    private ArrayList<Topping> mockUpData = new ArrayList<>();

    public Topping() {

    }

    public Topping(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Topping> getToppings() {
        this.buildMockData();
        return mockUpData;

    }

    public Topping getTopping(int id) {
        buildMockData();

        Topping topping = null;
        topping = this.mockUpData.stream().filter(t -> t.getId() == id).findFirst().get();
        return topping;

    }

    private void buildMockData() {
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Bananas", 0.99));
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Strawberries", 1.09));
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Syrup", 1.99));
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Chopped Nuts", 1.59));
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Tuna", 1.29));
        mockUpData.add(new Topping(this.mockUpData.size() + 1, "Pizza Sauce", 1.29));
    }

}
