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
public class C3 extends SuperConsumer {
    private final static String name = "C3";

    public static void main(String[] args) {
        C3 c3 = new C3();
    }

    private C3() {
        super(name);
        super.start();
    }

}
