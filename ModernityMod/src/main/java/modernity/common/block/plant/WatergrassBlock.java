/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.block.plant;

import modernity.common.block.MDBlockTags;
import modernity.common.block.plant.growing.WaterGrassGrowLogic;
import modernity.generic.block.IColoredBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class WatergrassBlock extends TallDirectionalPlantBlock implements IWaterPlant, IColoredBlock {
    public WatergrassBlock( Properties properties ) {
        super( properties, Direction.UP );
        setGrowLogic( new WaterGrassGrowLogic( this ) );
    }

    @Override
    @OnlyIn( Dist.CLIENT )
    public int colorMultiplier( BlockState state, @Nullable ILightReader reader, @Nullable BlockPos pos, int tintIndex ) {
        return 0;
//        return ModernityClient.get().getWatergrassColors().getColor( reader, pos );
    }

    @Override
    @OnlyIn( Dist.CLIENT )
    public int colorMultiplier( ItemStack stack, int tintIndex ) {
        return 0;
//        return ModernityClient.get().getWatergrassColors().getItemColor();
    }

    @Override
    public boolean canBlockSustain( IWorldReader world, BlockPos pos, BlockState state ) {
        return state.isIn( MDBlockTags.SOIL );
    }
}
