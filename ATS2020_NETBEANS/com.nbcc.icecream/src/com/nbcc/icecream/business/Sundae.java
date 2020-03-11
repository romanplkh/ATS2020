/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.icecream.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Roman Pelikh
 */
public class Sundae implements Serializable {

    private List<Topping> toppings = new ArrayList<>();

    private final double SALES_TAX_RATE = 0.15;

    private int id;
    private double subtotal;
    private double tax;

    public Sundae() {

    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return this.getSubtotal() * this.SALES_TAX_RATE;
    }

    public double calculateTotal() {
        
        double costOfToppings = 0.0;
           
        
       costOfToppings = this.toppings.stream().reduce(0, (t1, t2) -> t1 + t2);
          
        return 0.0;
    }

}
