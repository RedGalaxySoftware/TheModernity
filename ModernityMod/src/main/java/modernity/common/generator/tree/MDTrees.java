/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.generator.tree;

import modernity.common.block.MDTreeBlocks;
import modernity.common.block.base.AxisBlock;
import net.minecraft.util.Direction;

public final class MDTrees {

    public static final TinyBlackwoodTree BLACKWOOD_TINY = new TinyBlackwoodTree();

    public static final BlackwoodTree BLACKWOOD = new BlackwoodTree();

    public static final TallBlackwoodTree BLACKWOOD_TALL = new TallBlackwoodTree();

    public static final SphereTree INVER = new SphereTree(
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.X ),
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.Y ),
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.Z ),
        MDTreeBlocks.INVER_LEAVES.getDefaultState()
    );

    public static final SphereTree RED_INVER = new SphereTree(
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.X ),
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.Y ),
        MDTreeBlocks.INVER_LOG.getDefaultState().with( AxisBlock.AXIS, Direction.Axis.Z ),
        MDTreeBlocks.RED_INVER_LEAVES.getDefaultState()
    );

    private MDTrees() {
    }
}
