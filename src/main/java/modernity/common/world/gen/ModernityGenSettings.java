/*
 * Copyright (c) 2019 RedGalaxy & contributors
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 8 - 24 - 2019
 */

package modernity.common.world.gen;

import net.minecraft.world.gen.ChunkGenSettings;

import modernity.common.block.MDBlocks;

public class ModernityGenSettings extends ChunkGenSettings implements IModernityGenSettings {
    private int seaLevel = 72;
    public ModernityGenSettings() {
        defaultBlock = MDBlocks.ROCK_SLAB.getDefaultState();
        defaultFluid = MDBlocks.MODERNIZED_WATER.getDefaultState();
    }

    public ModernityGenSettings( int seaLevel ) {
        this.seaLevel = seaLevel;
        defaultBlock = MDBlocks.ROCK_SLAB.getDefaultState();
        defaultFluid = MDBlocks.MODERNIZED_WATER.getDefaultState();
    }

    @Override
    public int getBiomeBlendRadius() {
        return 2;
    }

    @Override
    public double getMainNoiseSizeX() {
        return 684.412;
    }

    @Override
    public double getMainNoiseSizeY() {
        return 684.412;
    }

    @Override
    public double getMainNoiseSizeZ() {
        return 684.412;
    }

    @Override
    public int getMainNoiseSizeOctaves() {
        return 16;
    }

    @Override
    public double getMixNoiseSizeX() {
        return 684.412 / 80;
    }

    @Override
    public double getMixNoiseSizeY() {
        return 684.412 / 160;
    }

    @Override
    public double getMixNoiseSizeZ() {
        return 684.412 / 80;
    }

    @Override
    public int getMixNoiseSizeOctaves() {
        return 8;
    }

    @Override
    public double getDepthNoiseSizeX() {
        return 200;
    }

    @Override
    public double getDepthNoiseSizeZ() {
        return 200;
    }

    @Override
    public int getDepthNoiseSizeOctaves() {
        return 16;
    }

    @Override
    public double getDepthNoiseInfluence() {
        return 0.2;
    }


    @Override
    public double getBaseBiomeDepth() {
        return 0;
    }

    @Override
    public double getBaseBiomeScale() {
        return 0;
    }

    @Override
    public double getBiomeDepthMultiplier() {
        return 1;
    }

    @Override
    public double getBiomeScaleMultiplier() {
        return 1;
    }

    @Override
    public double getHeightScale() {
        return 12;
    }

    @Override
    public double getHeightStretch() {
        return 8.5;
    }

    @Override
    public int getWaterLevel() {
        return seaLevel;
    }

    @Override
    public double getSurfaceNoiseSizeX() {
        return 28.733918;
    }

    @Override
    public double getSurfaceNoiseSizeY() {
        return 1.4252741;
    }

    @Override
    public double getSurfaceNoiseSizeZ() {
        return 28.733918;
    }

    @Override
    public int getSurfaceNoiseSizeOctaves() {
        return 5;
    }
}
