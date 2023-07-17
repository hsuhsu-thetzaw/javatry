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

// TODO harumi authorをお願いします、javatryでは自分が追加/修正したクラスには追加するようにお願いします by jflute (2023/07/17)
// 「ハンズオンのコーディングポリシー」の「3. 最低限のクラスJavaDoc」を参考にしてください。
// http://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
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

    // TODO harumi これらの変数をpublicにする必要はないと思うので、privateにしましょう by jflute (2023/07/17)
    // 変数のスコープは基本的に「最低限のスコープ」を意識しましょう。
    // TODO harumi [いいね] 在庫をチケット種別ごとに分けたの良いですね。自然な仕様だと思います by jflute (2023/07/17)
    // TODO harumi [いいね] Quantityという概念のオブジェクトを作ったの良いですね。1on1で説明しましたが... by jflute (2023/07/17)
    //  A. すべてのint型の値をオブジェクトにする
    //  B. すべてのint型の値をオブジェクトにしない
    //  C. 重要なものだけオブジェクトにする
    // "C" は線引きが難しいですが、現実的ではあります。
    // 「なぜ、Quantityだけオブジェクトにしているのか？」
    // こういうときしっかり堂々と答えられるように意識しておきましょう。
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
    // TODO harumi (少なくとも)publicのbuyにはjavadocを書いてみましょう。(呼び出す人が助かるjavadocを) by jflute (2023/07/17)
    public Ticket buyOneDayPassport(int handedMoney) {
        return doBuyPassportFlow(oneDayQuantity, handedMoney, TicketType.ONEDAY);
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        // TODO harumi buyOneDayPassport()だけが戻り値が違うことで、TwoDay以降に冗長なフローがあります by jflute (2023/07/17)
        // (calculateChange() して new TicketBuyResult() するところ)
        // TwoDay以降も一行 (1 statement)で済むように工夫してみましょう。
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

    // TODO harumi [いいね]お釣りを計算する処理をメソッドに切り出してるので良いですね！ by jflute (2023/07/17)
    // 単に引き算をするだけの小さなものですが、将来お釣りの計算に特殊な処理を入れる可能性もなきにしもあらず。
    // また、メソッド名でお釣り(change)を表現することで可読性も良くなります。
    private int calculateChange(int handedMoney, Ticket ticket) {
        return handedMoney - ticket.getTicketPrice();
    }

    // TODO harumi タグコメントありがとうございます。このコメントをタグコメントと呼んでいます by jflute (2023/07/17)
    // クラスの中身をカテゴライズするためのものです。DBFluteオリジナルな手法ですが、わりと現場でも使ってもらっています。
    // クラスの中身は放っておくと順番ぐちゃぐちゃになりがちです。こういうコメントで意識してもらうという狙いもあります。
    // ===================================================================================
    //                                                                                Flow
    //                                                                          ==========
    // TODO harumi [いいね]メソッドにdoを付けてるの良いですね！ by jflute (2023/07/17)
    // もし先頭単語が一緒だと「publicのbuy」と「privateのbuy」と紛れて視認しづらくなったりもするので、
    // こういう風に private 側に do を付けることがよくあります。(他にもinternalなど色々なパターンあり)
    private Ticket doBuyPassportFlow(Quantity quantity, int handedMoney, TicketType ticketType) {
        assertTicketInStock(quantity.getValue());
        assertTicketEnoughMoney(handedMoney, ticketType.getPrice());
        calculateSalesProceeds(ticketType.getPrice());
        // TODO harumi [いいね]Quantityを導入したメリットがここに活きてますね！ by jflute (2023/07/17)
        // 1on1で話したが、int型のまんまだとここでdecrementができないので、doBuyのまるごと再利用がしづらくなります。
        // その解決方法の一つとしてQuantityクラスを使って業務の値をオブジェクト化するというのがあります。
        // 作ったきっかけは違うとのことでしたが、これを作ることで自然で解決していたことになります。
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

    // TODO harumi [いいね]assertの目的語は正常系になりますので、EnoughMoneyはとても良いです！ by jflute (2023/07/17)
    // よく迷う代表例がcheckです。checkShortMoney()/checkEnoughMoney()どっちにも使えて、
    // どっちのときに例外になるのか？ちょっと迷いがちです。もっと複雑な業務だととてもややこしくなります。
    // 曖昧になる動詞はあまり使わないほうが良いので、assert＋正常系はよく使われます。(verifyも時々使うかな)
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

    // TODO harumi Accessorの下のラインが消えてます？ by jflute (2023/07/17)
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
