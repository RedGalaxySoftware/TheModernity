/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.generator.decorate.condition;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;

public class And implements IDecorCondition {
    private final IDecorCondition a;
    private final IDecorCondition b;

    public And( IDecorCondition a, IDecorCondition b ) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean canGenerate( IWorld world, BlockPos pos, Random rand ) {
        return a.canGenerate( world, pos, rand ) && b.canGenerate( world, pos, rand );
    }
}
