package org.docksidestage.bizfw.basic.buyticket;

// TODO harumi javadocお願いします by jflute (2023/07/17)
// TODO harumi 細かいですが、javatry全体的にclass直下は一行空行を空けているので、統一するようにお願いします by jflute (2023/07/17)
public class Quantity {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO harumi [いいね]valueというシンプルな名前良いですね！ by jflute (2023/07/17)
    // よくここでは int quantity となる人も多いです。それも悪くはないですが...
    // クラスもQuantity、変数もquantityとちょっと紛らわしくなるので、内部の値ということでvalueで良いと思います。
    private int value;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Quantity(int value) {
        this.value = value;
    }

    // TODO harumi タグコメント、せっかく付けてもらっているので、このAccessor領域にも付けましょう by jflute (2023/07/17)
    // (このままだと、Constructor領域にgetメソッドなどが置かれてることになってしまいます)
    public int getValue() {
        return value;
    }

    public void decrement() {
        // TODO harumi 細かいですが、this.は付けなくてもOKです。必要でなければ付けない人が多いかもです by jflute (2023/07/17)
        // でも、あえて付けたいというのであれば付けてもOKですが、こだわりがなければ削除でも良いです。
        // (getValue()の方ではthis.付けてないですもんね)
        this.value--;
    }
}
