/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

import org.docksidestage.bizfw.basic.buyticket.Ticket.TicketType;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_MAX_QUANTITY = 10;
    private static final int TWO_DAY_MAX_QUANTITY = 10;
    private static final int FOUR_DAY_MAX_QUANTITY = 10;
    private static final int NIGHT_ONLY_TWO_DAY_MAX_QUANTITY = 10;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private Integer salesProceeds;

    public final Quantity oneDayQuantity = new Quantity(ONE_DAY_MAX_QUANTITY);
    public final Quantity twoDayQuantity = new Quantity(TWO_DAY_MAX_QUANTITY);
    public final Quantity fourDayQuantity = new Quantity(FOUR_DAY_MAX_QUANTITY);
    public final Quantity nightOnlyTwoDayQuantity = new Quantity(NIGHT_ONLY_TWO_DAY_MAX_QUANTITY);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public Ticket buyOneDayPassport(int handedMoney) {
        return doBuyPassportFlow(oneDayQuantity, handedMoney, TicketType.ONEDAY);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        Ticket ticket = doBuyPassportFlow(twoDayQuantity, handedMoney, TicketType.TWODAY);
        int change = calculateChange(handedMoney, ticket);
        return new TicketBuyResult(handedMoney, change, ticket);
    }

    public TicketBuyResult buyFourDayPassport(int handedMoney) {
        Ticket ticket = doBuyPassportFlow(fourDayQuantity, handedMoney, TicketType.FOURDAY);
        int change = calculateChange(handedMoney, ticket);
        return new TicketBuyResult(handedMoney, change, ticket);
    }

    public TicketBuyResult buyNightOnlyTwoDayPassport(int handedMoney) {
        Ticket ticket = doBuyPassportFlow(nightOnlyTwoDayQuantity, handedMoney, TicketType.NIGHT_ONLY_TWODAY);
        int change = calculateChange(handedMoney, ticket);
        return new TicketBuyResult(handedMoney, change, ticket);
    }

    private int calculateChange(int handedMoney, Ticket ticket) {
        return handedMoney - ticket.getTicketPrice();
    }

    // ===================================================================================
    //                                                                                Flow
    //                                                                          ==========
    private Ticket doBuyPassportFlow(Quantity quantity, int handedMoney, TicketType ticketType) {
        assertTicketInStock(quantity.getValue());
        assertTicketEnoughMoney(handedMoney, ticketType.getPrice());
        calculateSalesProceeds(ticketType.getPrice());
        quantity.decrement();
        return new Ticket(ticketType);
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    private void assertTicketInStock(int quantity) {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
    }

    //assert＋正常系
    private void assertTicketEnoughMoney(int handedMoney, int salesPrice) {
        if (handedMoney < salesPrice) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void calculateSalesProceeds(int price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //
    public Integer getSalesProceeds() {
        return salesProceeds;
    }

    public Quantity getOneDayQuantity() {
        return oneDayQuantity;
    }
}
