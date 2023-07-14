package org.docksidestage.bizfw.basic.buyticket;

public class Quantity {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int value;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Quantity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void decrement() {
        this.value--;
    }
}
