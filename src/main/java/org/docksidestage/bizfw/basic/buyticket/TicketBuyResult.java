package org.docksidestage.bizfw.basic.buyticket;

public class TicketBuyResult {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int handedMoney;
    private final int change;
    private final Ticket boughtTicket;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int handedMoney, int change, Ticket ticket) {
        if (handedMoney < 0) {
            throw new IllegalArgumentException("The argument 'handedMoney' should not be minus");
        }
        if (change < 0) {
            throw new IllegalArgumentException("The argument 'change' should not be minus");
        }
        if (ticket == null) {
            throw new IllegalArgumentException("The argument 'ticket' should not be null");
        }
        this.handedMoney = handedMoney;
        this.change = change;
        this.boughtTicket = ticket;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHandedMoney() {
        return handedMoney;
    }

    public int getPaidPrice() {
        return boughtTicket.getTicketPrice();
    }

    public Ticket getTicket() {
        return boughtTicket;
    }

    public int getChange() {
        return change;
    }
}
