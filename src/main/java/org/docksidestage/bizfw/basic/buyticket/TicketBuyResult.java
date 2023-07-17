package org.docksidestage.bizfw.basic.buyticket;

// TODO harumi javadoc by jflute (2023/07/17)
public class TicketBuyResult {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int handedMoney;
    private final int change;
    private final Ticket boughtTicket;
    // TODO harumi [質問]インスタンス変数だけ boughtTicket で、getterやConstructorの引数は単なるticketの理由は？ by jflute (2023/07/17)

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBuyResult(int handedMoney, int change, Ticket ticket) {
        // TODO jflute [質問]TicketのConstructorでは引数チェックしてないけど、ここで引数チェックしてる理由は？ (2023/07/17)
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
    // TODO harumi getterの並び、もうちょいインスタンス変数と合わせたいですね by jflute (2023/07/17)
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
