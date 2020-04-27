/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.block.plant;

import modernity.common.block.MDBlockTags;
import modernity.common.block.MDNatureBlocks;
import modernity.common.block.fluid.IMurkyWaterloggedBlock;
import modernity.common.block.plant.growing.ReedGrowLogic;
import modernity.common.fluid.MDFluids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings( "deprecation" )
public class MurkReedBlock extends TallDirectionalPlantBlock implements IMurkyWaterloggedBlock {
    public static final VoxelShape REED_END_SHAPE = makePlantShape( 12, 14 );
    public static final VoxelShape REED_MIDDLE_SHAPE = makePlantShape( 12, 16 );

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;

    public MurkReedBlock( Properties properties ) {
        super( properties, Direction.UP );
        setGrowLogic( new ReedGrowLogic() );
    }

    @Override
    public SoundType getSoundType( BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity ) {
        return state.get( WATERLOGGED ) ? SoundType.WET_GRASS : SoundType.PLANT;
    }

    @Override
    protected void fillStateContainer( StateContainer.Builder<Block, BlockState> builder ) {
        super.fillStateContainer( builder );
        builder.add( AGE );
    }

    @Override
    public boolean canBlockSustain( IWorldReader reader, BlockPos pos, BlockState state ) {
        if( state.isIn( MDBlockTags.REEDS_GROWABLE ) ) {
            if( reader.getFluidState( pos.up() ).getFluid() == MDFluids.MURKY_WATER ) {
                return true;
            }
            for( Direction facing : Direction.Plane.HORIZONTAL ) {
                BlockPos pos1 = pos.offset( facing );
                if( reader.getFluidState( pos1 ).getFluid() == MDFluids.MURKY_WATER ) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public VoxelShape getShape( BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx ) {
        Vec3d off = state.getOffset( world, pos );
        return ( state.get( END ) ? REED_END_SHAPE : REED_MIDDLE_SHAPE ).withOffset( off.x, off.y, off.z );
    }

    @Override
    public boolean isReplaceable( BlockState state, BlockItemUseContext useContext ) {
        return false;
    }


//    @Override
//    public void tick( BlockState state, World world, BlockPos pos, Random random ) {
//        if( state.get( AGE ) < 15 ) {
//            world.setBlockState( pos, state.with( AGE, state.get( AGE ) + 1 ) );
//        } else if( canGrow( world, pos, state ) ) {
//            world.setBlockState( pos, state.with( AGE, 0 ) );
//            world.setBlockState( pos.up(), getDefaultState().with( WATERLOGGED, world.getFluidState( pos.up() ).getFluid() == MDFluids.MURKY_WATER ) );
//        }
//    }

    // TODO Where are we not using this?
//    private boolean canGrow( World world, BlockPos pos, BlockState state ) {
//        BlockPos upPos = pos.up();
//        BlockState upState = world.getBlockState( upPos );
//        if( ! upState.isAir( world, upPos ) && upState.getBlock() != MDNatureBlocks.MURKY_WATER ) {
//            return false;
//        }
//        int owHeight = 0, totHeight = 0;
//        MovingBlockPos mpos = new MovingBlockPos( pos );
//        boolean uw = false;
//        while( mpos.getY() >= 0 && state.getBlock() == this && totHeight < 10 ) {
//            if( state.get( WATERLOGGED ) ) {
//                uw = true;
//            }
//            if( ! uw ) {
//                owHeight++;
//            }
//            totHeight++;
//            mpos.moveDown();
//            state = world.getBlockState( mpos );
//        }
//        return totHeight < 10 && owHeight < 3;
//    }

    private boolean blocked( IWorldReader world, BlockPos pos, BlockState state ) {
        return state.getMaterial().blocksMovement() || state.getMaterial().isLiquid() && state.getFluidState().getFluid() != MDFluids.MURKY_WATER || isSelfState( world, pos, state );
    }

    @Override
    public boolean canGenerateAt( IWorld world, BlockPos pos, BlockState state ) {
        boolean air = state.isAir( world, pos ) || state.getBlock() == MDNatureBlocks.MURKY_WATER;
        return air && isValidPosition( state, world, pos ) && canBlockSustain( world, pos.down(), world.getBlockState( pos.down() ) );
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public BlockPos getRootPos( World world, BlockPos pos, BlockState state ) {
        return pos;
    }
}
