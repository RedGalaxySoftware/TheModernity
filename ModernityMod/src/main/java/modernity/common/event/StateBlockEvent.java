/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A block event that uses a block state as data
 */
public abstract class StateBlockEvent extends BlockEvent<BlockState> {

    protected StateBlockEvent( int range ) {
        super( range );
    }

    protected StateBlockEvent() {
        super();
    }

    @Override
    public final void writeData( BlockState data, PacketBuffer buffer ) {
        buffer.writeInt( Block.BLOCK_STATE_IDS.get( data ) );
    }

    @Override
    @OnlyIn( Dist.CLIENT )
    public final BlockState readData( PacketBuffer buffer ) {
        return Block.BLOCK_STATE_IDS.getByValue( buffer.readInt() );
    }
}
