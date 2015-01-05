/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insa.swim.consumer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pdlsoa
 */
public class C1 extends SuperConsumer {
    private final static String name = "C1";

    public static void main(String[] args) {
        System.out.println("debut");
        C1 c1 = new C1();
        //c1.sendPing();
        System.out.println("fin");
    }

    private C1() {
        super(name);
        super.start();
    }

}
