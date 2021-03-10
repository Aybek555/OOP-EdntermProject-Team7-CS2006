package com.endterm.entities;

import java.math.BigDecimal;

public class Card {
    private BigDecimal cvv;
    private String card_number;
    private int id;
    private int mik = 0;
    private int mir = 0;
    private int miu = 0;


    public Card(int id, String card_number, BigDecimal cvv){
        this.cvv=cvv;
        this.card_number=card_number;
        this.id=id;
        mik = 0;
        mir = 0;
        miu = 0;
    }

    public Card(int id, BigDecimal cvv) {
        this.cvv = cvv;
        this.id = id;
        mik = 0;
        mir = 0;
        miu = 0;
    }
    public Card(String card_number, BigDecimal cvv) {
        this.cvv = cvv;
        this.card_number=card_number;
        mik = 0;
        mir = 0;
        miu = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCvv(BigDecimal cvv) {
        this.cvv = cvv;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public void setMik(int mik) {
        this.mik = mik;
    }

    public void setMir(int mir) {
        this.mir = mir;
    }

    public void setMiu(int miu) {
        this.miu = miu;
    }

    public int getMik() {
        return mik;
    }

    public int getMir() {
        return mir;
    }

    public int getMiu() {
        return miu;
    }

    public String getCard_number() {
        return card_number;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getCvv() {
        return cvv;
    }

    @Override
    public String toString() {
        return "Card{" + ", id=" + id + " CVV=" + cvv + ", card_number=" + card_number + ", KZT=" + mik + ", RUB=" + mir + ", USD=" + miu + '}';
    }
}
