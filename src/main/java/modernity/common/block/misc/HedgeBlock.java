/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 26 - 2020
 * Author: rgsw
 */

package modernity.common.block.misc;

import modernity.common.block.MDBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

/**
 * A block that prevents entities from jumping over, looking like a hedge.
 */
@SuppressWarnings( "deprecation" )
public class HedgeBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    private static final VoxelShape[] HITBOX_SHAPES = new VoxelShape[ 16 ];
    private static final VoxelShape[] COLLISION_SHAPES = new VoxelShape[ 16 ];

    static {
        VoxelShape pole = makeCuboidShape( 4, 0, 4, 12, 16, 12 );
        VoxelShape north = makeCuboidShape( 4, 0, 0, 12, 16, 4 );
        VoxelShape south = makeCuboidShape( 4, 0, 12, 12, 16, 16 );
        VoxelShape west = makeCuboidShape( 0, 0, 4, 4, 16, 12 );
        VoxelShape east = makeCuboidShape( 12, 0, 4, 16, 16, 12 );

        VoxelShape cpole = makeCuboidShape( 4, 0, 4, 12, 24, 12 );
        VoxelShape cnorth = makeCuboidShape( 4, 0, 0, 12, 24, 4 );
        VoxelShape csouth = makeCuboidShape( 4, 0, 12, 12, 24, 16 );
        VoxelShape cwest = makeCuboidShape( 0, 0, 4, 4, 24, 12 );
        VoxelShape ceast = makeCuboidShape( 12, 0, 4, 16, 24, 12 );

        for( int i = 0; i < 16; i++ ) {
            VoxelShape hitbox = pole;
            if( ( i & 1 ) != 0 ) hitbox = VoxelShapes.or( hitbox, north );
            if( ( i & 2 ) != 0 ) hitbox = VoxelShapes.or( hitbox, south );
            if( ( i & 4 ) != 0 ) hitbox = VoxelShapes.or( hitbox, west );
            if( ( i & 8 ) != 0 ) hitbox = VoxelShapes.or( hitbox, east );
            HITBOX_SHAPES[ i ] = hitbox;

            VoxelShape collision = cpole;
            if( ( i & 1 ) != 0 ) collision = VoxelShapes.or( collision, cnorth );
            if( ( i & 2 ) != 0 ) collision = VoxelShapes.or( collision, csouth );
            if( ( i & 4 ) != 0 ) collision = VoxelShapes.or( collision, cwest );
            if( ( i & 8 ) != 0 ) collision = VoxelShapes.or( collision, ceast );
            COLLISION_SHAPES[ i ] = collision;
        }
    }

    public HedgeBlock( Properties properties ) {
        super( properties );

        setDefaultState( stateContainer.getBaseState()
                                       .with( NORTH, false )
                                       .with( EAST, false )
                                       .with( SOUTH, false )
                                       .with( WEST, false )
        );
    }

    @Override
    public boolean allowsMovement( BlockState state, IBlockReader worldIn, BlockPos pos, PathType type ) {
        return false;
    }

    @Override
    public BlockState updatePostPlacement( BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos ) {
        super.updatePostPlacement( state, facing, facingState, world, pos, facingPos );

        if( facing != Direction.DOWN ) {
            boolean north = facing == Direction.NORTH
                            ? attachesTo( facingState, facingState.isSolidSide( world, facingPos, facing.getOpposite() ), facing )
                            : state.get( NORTH );
            boolean east = facing == Direction.EAST
                           ? attachesTo( facingState, facingState.isSolidSide( world, facingPos, facing.getOpposite() ), facing )
                           : state.get( EAST );
            boolean south = facing == Direction.SOUTH
                            ? attachesTo( facingState, facingState.isSolidSide( world, facingPos, facing.getOpposite() ), facing )
                            : state.get( SOUTH );
            boolean west = facing == Direction.WEST
                           ? attachesTo( facingState, facingState.isSolidSide( world, facingPos, facing.getOpposite() ), facing )
                           : state.get( WEST );

            return state.with( NORTH, north )
                        .with( EAST, east )
                        .with( SOUTH, south )
                        .with( WEST, west );
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement( BlockItemUseContext context ) {
        IWorld world = context.getWorld();
        BlockPos pos = context.getPos();
        boolean north = canWallConnectTo( world, pos, Direction.NORTH );
        boolean east = canWallConnectTo( world, pos, Direction.EAST );
        boolean south = canWallConnectTo( world, pos, Direction.SOUTH );
        boolean west = canWallConnectTo( world, pos, Direction.WEST );

        return getDefaultState().with( NORTH, north )
                                .with( EAST, east )
                                .with( SOUTH, south )
                                .with( WEST, west );
    }

    /**
     * Checks whether an block causes a pole on the wall below.
     */
    public boolean doesBlockCausePole( IWorld world, BlockPos pos, BlockState state ) {
        return ! state.isAir( world, pos );
    }

    private boolean attachesTo( BlockState state, boolean solidSide, Direction dir ) {
        Block block = state.getBlock();
        boolean wallOrFenceGate = block.isIn( MDBlockTags.HEDGES ) || isFenceGate( block ) && net.minecraft.block.FenceGateBlock.isParallel( state, dir );
        return ! cannotAttach( block ) && solidSide || wallOrFenceGate;
    }

    /**
     * Can this wall connec to the specified block?
     */
    private boolean canWallConnectTo( IBlockReader world, BlockPos pos, Direction facing ) {
        BlockPos off = pos.offset( facing );
        BlockState other = world.getBlockState( off );
        return other.canBeConnectedTo( world, off, facing.getOpposite() ) || attachesTo( other, other.isSolidSide( world, off, facing.getOpposite() ), facing );
    }

    @Override
    protected void fillStateContainer( StateContainer.Builder<Block, BlockState> builder ) {
        super.fillStateContainer( builder );
        builder.add( NORTH, EAST, SOUTH, WEST );
    }

    /**
     * Special check for fence gate to match both modernity and vanilla fence gates.
     */
    private boolean isFenceGate( Block block ) {
        return block instanceof FenceGateBlock || block instanceof net.minecraft.block.FenceGateBlock;
    }

    @Override
    public VoxelShape getShape( BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx ) {
        int i = 0;
        if( state.get( NORTH ) ) i |= 1;
        if( state.get( SOUTH ) ) i |= 2;
        if( state.get( WEST ) ) i |= 4;
        if( state.get( EAST ) ) i |= 8;
        return HITBOX_SHAPES[ i ];
    }

    @Override
    public VoxelShape getCollisionShape( BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx ) {
        if( ctx.func_225581_b_() ) return VoxelShapes.empty();
        int i = 0;
        if( state.get( NORTH ) ) i |= 1;
        if( state.get( SOUTH ) ) i |= 2;
        if( state.get( WEST ) ) i |= 4;
        if( state.get( EAST ) ) i |= 8;
        return COLLISION_SHAPES[ i ];
    }

    @Override
    public BlockState rotate( BlockState state, Rotation rot ) {
        switch( rot ) {
            case CLOCKWISE_180:
                return state.with( NORTH, state.get( SOUTH ) ).with( EAST, state.get( WEST ) ).with( SOUTH, state.get( NORTH ) ).with( WEST, state.get( EAST ) );
            case COUNTERCLOCKWISE_90:
                return state.with( NORTH, state.get( EAST ) ).with( EAST, state.get( SOUTH ) ).with( SOUTH, state.get( WEST ) ).with( WEST, state.get( NORTH ) );
            case CLOCKWISE_90:
                return state.with( NORTH, state.get( WEST ) ).with( EAST, state.get( NORTH ) ).with( SOUTH, state.get( EAST ) ).with( WEST, state.get( SOUTH ) );
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror( BlockState state, Mirror mirr ) {
        switch( mirr ) {
            case LEFT_RIGHT:
                return state.with( NORTH, state.get( SOUTH ) ).with( SOUTH, state.get( NORTH ) );
            case FRONT_BACK:
                return state.with( EAST, state.get( WEST ) ).with( WEST, state.get( EAST ) );
            default:
                return super.mirror( state, mirr );
        }
    }
}
