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

    // TODO harumi [質問]TicketTypeだけインナークラスで作った理由は？ (QuantityとかTicketBuyResultは独立クラス) by jflute (2023/07/17)
    public enum TicketType {
        // TODO harumi 改行を入れて、もうちょい見やすく工夫してみて下さい by jflute (2023/07/17)
        // TODO harumi NIGHT_ONLYだけ "2日" と漢字の数字なくて普通の数字になってます(^^ by jflute (2023/07/17)
        ONEDAY("一日利用", 1, 7400), NIGHT_ONLY_TWODAY("夜間限定2日利用", 2, 7400), TWODAY("二日利用", 2, 13200), FOURDAY("四日利用", 4, 22400);

        // TODO harumi [質問]titleという概念を入れた理由は？ by jflute (2023/07/17)
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
            // TODO harumi AlreadyInという概念はすでに "1回以上入ったら" なので、ここの例外メッセージには合わなくなっています by jflute (2023/07/17)
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + getTicketPrice());
        }
        useTicketCount++;
        alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // TODO harumi [いいね]このjavadocとても良いです！AlreadyInの定義の説明があると使う側は助かりますね by jflute (2023/07/17)
    /**
     * @return 一回でも入場したらtrue
     */
    public boolean isAlreadyIn() {
        return alreadyIn;
    }
    // TODO harumi あと残り何回使えるチケットなのか？がユーザーもわかるようにすると良いと思いました by jflute (2023/07/17)

    public TicketType getTicketType() {
        return ticketType;
    }

    // TODO harumi [質問]getTicketType()があれば、以下のgetterたちは無くても大丈夫ですが、作った理由はなんでしょう？ by jflute (2023/07/17)
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
