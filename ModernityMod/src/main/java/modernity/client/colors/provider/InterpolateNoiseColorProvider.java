/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.client.colors.provider;

// TODO Re-evaluate
//public abstract class InterpolateNoiseColorProvider extends NoiseColorProvider {
//    private final IColorProvider providerA;
//    private final IColorProvider providerB;
//
//    public InterpolateNoiseColorProvider( IColorProvider providerA, IColorProvider providerB ) {
//        this.providerA = providerA;
//        this.providerB = providerB;
//    }
//
//    @Override
//    protected int computeColor( IEnviromentBlockReader world, BlockPos pos, double noise ) {
//        return ColorUtil.interpolate(
//            providerA.getColor( world, pos ),
//            providerB.getColor( world, pos ),
//            MathUtil.unlerp( - 1, 1, noise )
//        );
//    }
//
//    @Override
//    public void initForSeed( long seed ) {
//        super.initForSeed( seed );
//        providerA.initForSeed( seed );
//        providerB.initForSeed( seed );
//    }
//}
