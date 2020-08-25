/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package natures.debris.p2.common.fluid;

import natures.debris.p2.common.block.MDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * The Modernity variant on water. Murky water generates almost everywhere in the Modernity.
 */
public abstract class MurkyWaterFluid extends RegularFluid implements ICustomRenderFluid { // , IAluminiumBucketTakeable
    @Override
    public Fluid getFlowingFluid() {
        return MDFluids.FLOWING_MURKY_WATER;
    }

    @Override
    public Fluid getStillFluid() {
        return MDFluids.MURKY_WATER;
    }

    @Override
    public Item getFilledBucket() {
        return Items.WATER_BUCKET;
    }

//    @Override
//    public Item getFilledAluminiumBucket() {
//        return MDItems.ALUMINIUM_WATER_BUCKET;
//    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(World worldIn, BlockPos pos, IFluidState state, Random random) {
        if (!state.isSource() && !state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            worldIn.addParticle(ParticleTypes.UNDERWATER, (float) pos.getX() + random.nextFloat(), (float) pos.getY() + random.nextFloat(), (float) pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }

    }

    @Override
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean canSourcesMultiply() {
        return true;
    }

    @Override
    protected void beforeReplacingBlock(IWorld world, BlockPos pos, BlockState state) {
        TileEntity te = state.getBlock().hasTileEntity(world.getBlockState(pos))
                        ? world.getTileEntity(pos)
                        : null;
        Block.spawnDrops(state, world.getWorld(), pos, te);
    }

    @Override
    public int getSlopeFindDistance(IWorld world) {
        return 4;
    }

    @Override
    public BlockState getBlockState(IFluidState state) {
        return MDBlocks.MURKY_WATER.getDefaultState().with(blockLevel, getLevelFromState(state));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid instanceof MurkyWaterFluid;
    }

    @Override
    public int getLevelDecreasePerBlock(IWorld world) {
        return 1;
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 5;
    }

    public boolean canOtherFlowInto(IFluidState state, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public ResourceLocation getStill() {
        return new ResourceLocation("minecraft:block/water_still");
    }

    @Override
    public ResourceLocation getFlowing() {
        return new ResourceLocation("minecraft:block/water_flow");
    }

    @Override
    public ResourceLocation getOverlay() {
        return new ResourceLocation("minecraft:block/water_overlay");
    }

    @Override
    public int getColor(IFluidState state, BlockPos pos, ILightReader world) {
        return 0xff253db8; // TODO Colors
//        return ModernityClient.get().getWaterColors().getColor( world, pos );
    }

    @Override
    public int getDefaultColor() {
        return 0xff253db8; // TODO Colors
//        return ModernityClient.get().getWaterColors().getItemColor();
    }

    @Override
    protected boolean canDisplace(IFluidState state, IBlockReader world, BlockPos pos, Fluid fluid, Direction facing) {
        return facing == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    public static class Flowing extends MurkyWaterFluid {
        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(BlockStateProperties.LEVEL_1_8);
        }

        @Override
        public int getLevel(IFluidState state) {
            return state.get(BlockStateProperties.LEVEL_1_8);
        }

        @Override
        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static class Source extends MurkyWaterFluid {
        @Override
        public int getLevel(IFluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }
    }
}
