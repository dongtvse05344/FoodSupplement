/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.contanst;

/**
 *
 * @author Tran Dong
 */
public enum ProductStatus {
    NEW(0),
    VALIDATED(1),
    CONVERTED(2),
    CANCELED(3);
    private final int value;
    private ProductStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}