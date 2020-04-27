/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.block.dirt.logic;

import modernity.common.block.dirt.DirtlikeBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.redgalaxy.util.Lazy;

import java.util.function.Supplier;

public class FarmlandableDirtLogic extends NormalDirtLogic {
    private final Lazy<FarmlandDirtLogic> withFarmland;

    public FarmlandableDirtLogic( Supplier<? extends DirtlikeBlock> block, IDirtLogicType type, Supplier<? extends FarmlandDirtLogic> withFarmland ) {
        super( block, type );

        this.withFarmland = Lazy.of( withFarmland );
    }

    public void makeFarmland( IWorld world, BlockPos pos, BlockState state ) {
        BlockState newState = withFarmland.get().switchState( world, pos, state );
        world.setBlockState( pos, newState, 3 );
    }
}
