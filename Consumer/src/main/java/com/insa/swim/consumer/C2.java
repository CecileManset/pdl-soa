/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

/**
 *
 * @author pdlsoa
 */
public class C2 extends SuperConsumer {
    private final static String name = "C2";
/*
    public static void main(String[] args) {
        C2 c2 = new C2();
    }
*/
    private C2() {
        super(name);
        super.start();
    }

}
