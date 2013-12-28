/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaxrs;

import java.io.Serializable;

/**
 *
 * @author tom
 */
public class Addition {

    private Integer zahl1;
    private Integer zahl2;
    private Integer ergebnis;

    public Addition() {

    }

    public Addition(Integer z1, Integer z2, Integer e) {
        zahl1 = z1;
        zahl2 = z2;
        ergebnis = e;
    }

    public Integer getZahl1() {
        return zahl1;
    }

    public Integer getZahl2() {
        return zahl2;
    }

    public Integer getErgebnis() {
        return ergebnis;
    }

    public void setZahl1(Integer zahl1) {
        this.zahl1 = zahl1;
    }

    public void setZahl2(Integer zahl2) {
        this.zahl2 = zahl2;
    }

    public void setErgebnis(Integer ergebnis) {
        this.ergebnis = ergebnis;
    }
}
