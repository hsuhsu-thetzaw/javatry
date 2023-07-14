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

/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final TicketType ticketType;
    private boolean alreadyIn;
    private int useTicketCount = 0;

    public enum TicketType {
        ONEDAY("一日利用", 1, 7400), NIGHT_ONLY_TWODAY("夜間限定2日利用", 2, 7400), TWODAY("二日利用", 2, 13200), FOURDAY("四日利用", 4, 22400);
        private final String title;
        private final int days;
        private final int price;

        private TicketType(String title, int days, int price) {
            this.title = title;
            this.days = days;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public int getDays() {
            return days;
        }

        public int getPrice() {
            return price;
        }
    }

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (useTicketCount >= ticketType.getDays()) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + getTicketPrice());
        }
        useTicketCount++;
        alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @return 一回でも入場したらtrue
     */
    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public String getTicketTitle() {
        return ticketType.getTitle();
    }

    public int getTicketDays() {
        return ticketType.getDays();
    }

    public int getTicketPrice() {
        return ticketType.getPrice();
    }
}
