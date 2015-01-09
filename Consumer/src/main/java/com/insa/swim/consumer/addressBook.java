/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.insa.swim.consumer;

import java.util.HashMap;

/**
 *
 * @author pdlsoa
 */
public class addressBook {
    private static HashMap<Integer, String> providers;
    private static HashMap<Integer, String> consumers;

    public addressBook() {
        providers.put(1, "toto");
    }
}
