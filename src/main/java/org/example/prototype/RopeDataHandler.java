package org.example.prototype;

import java.time.LocalDate;
import java.util.ArrayList;

public class RopeDataHandler {
    //responsible for 1. parsing inputs, 2. "getting" volatile environmental elements -> button signals for now
    private ArrayList<Purchase> purchases;
    RopeDataHandler(){
        purchases=new ArrayList<>();
    }
    public ArrayList<Purchase> load_test_purchases(){
        purchases.clear();
        purchases.add(new Purchase("https://shopee.vn/product/2109859/1902164281",7,LocalDate.of(2026,1,16),LocalDate.of(2026,1,19),2));
        purchases.add(new Purchase("https://shopee.vn/product/2109859/1902164281",7,LocalDate.of(2026,1,23),LocalDate.of(2026,1,26),2));
        purchases.add(new Purchase("https://shopee.vn/product/2109859/1902164281",7,LocalDate.of(2026,1,30),LocalDate.of(2026,2,2),2));
        purchases.add(new Purchase("https://shopee.vn/product/2109859/1902164281",7,LocalDate.of(2026,2,6),LocalDate.of(2026,2,9),2));
        purchases.add(new Purchase("https://shopee.vn/product/2109859/1902164281",7,LocalDate.of(2026,2,13),LocalDate.of(2026,2,16),2));
        return purchases;
    }
    public ArrayList<Purchase> getPurchases(){
        return purchases;
    }
    public void setPurchases(ArrayList<Purchase>p){
        purchases=p;
    }
}
