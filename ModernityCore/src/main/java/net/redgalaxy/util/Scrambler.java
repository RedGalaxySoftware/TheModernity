/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package net.redgalaxy.util;

import net.redgalaxy.exc.InstanceOfUtilityClassException;

public final class Scrambler {
    private Scrambler() {
        throw new InstanceOfUtilityClassException();
    }

    public static long xorshift( long value, int a, int b, int c ) {
        value ^= value >> a;
        value ^= value << b;
        value ^= value >> c;
        return value;
    }

    public static long xorshift( long value, int... s ) {
        boolean dir = true;
        for( int a : s ) {
            value ^= ( dir = ! dir )
                     ? value << a
                     : value >> a;
        }
        return value;
    }
}
