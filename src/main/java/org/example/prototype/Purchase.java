package org.example.prototype;

import java.time.LocalDate;

public class Purchase {
    String itemLink;
    double amt;
    LocalDate bt;
    LocalDate rt;
    int tw;
    Purchase(String itemLink, double amt, LocalDate bt, LocalDate rt, int tw){
        this.itemLink=itemLink;
        this.amt=amt;
        this.bt=bt;
        this.rt=rt;
        this.tw=tw;
    }
}
