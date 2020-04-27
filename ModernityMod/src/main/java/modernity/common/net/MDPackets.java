/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.net;

import modernity.network.ESide;
import modernity.network.PacketChannel;

/**
 * Class that handles registry of Modernity packets.
 */
public final class MDPackets {
    private MDPackets() {
    }

    public static void register( PacketChannel channel ) {
        channel.register( ESide.SERVER, SSpawnEntityPacket.class );
        channel.register( ESide.SERVER, SEventPacket.class );
        channel.register( ESide.SERVER, SSeedPacket.class );
        channel.register( ESide.SERVER, SSatellitePacket.class );
        channel.register( ESide.SERVER, SEnvironmentPacket.class );
        channel.register( ESide.SERVER, SAreaUntrackPacket.class );
        channel.register( ESide.SERVER, SAreaUpdatePacket.class );
        channel.register( ESide.SERVER, SAreaMessagePacket.class );
    }
}
