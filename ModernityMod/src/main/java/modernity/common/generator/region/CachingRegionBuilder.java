/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.generator.region;

import net.redgalaxy.util.ILongScrambler;
import net.redgalaxy.util.Scrambler;

import java.util.function.Function;

/**
 * A builder for {@link CachingRegion}s, managed by a {@link CachingRegionContext}.
 */
public class CachingRegionBuilder implements IRegionBuilder<CachingRegion, CachingRegionBuilder> {
    /** A default seed scrambler to compute a series of seeds for each applied transformation. */
    private static final ILongScrambler DEFAULT_SCRAMBLER = s -> Scrambler.xorshift( s, 13, 17, 5, 23, 19, 7 ) & 0xFFFFL;

    /** The managing region context. */
    private final CachingRegionContext context;

    /** The built {@link IRegionFactory} chain. */
    private IRegionFactory<CachingRegion> factory;

    /** The current seed, which is scrambled by {@link #DEFAULT_SCRAMBLER} to create a series of random seeds. */
    private long seed;

    /**
     * Creates a caching region builder. This constructor must be used via one of:
     * <ul>
     *     <li>{@link CachingRegionContext#extend}</li>
     *     <li>{@link CachingRegionContext#generate}</li>
     *     <li>{@link CachingRegionContext#transform}</li>
     *     <li>{@link CachingRegionContext#merge}</li>
     * </ul>
     *
     * @param context  The managing region context that is used to create {@link CachingRegion}s.
     * @param root     The root factory. This is needed to build a factory chain and must not be null.
     * @param rootSeed The root seed to be scrambled.
     */
    CachingRegionBuilder( CachingRegionContext context, IRegionFactory<CachingRegion> root, long rootSeed ) {
        this.context = context;
        this.factory = root;
        this.seed = DEFAULT_SCRAMBLER.scramble( rootSeed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionContext getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRegionFactory<CachingRegion> getFactory() {
        return factory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder apply( Function<IRegionFactory<CachingRegion>, IRegionFactory<CachingRegion>> function ) {
        factory = function.apply( factory );
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CachingRegionBuilder setSeed( long seed ) {
        this.seed = seed;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long nextSeed() {
        long out = seed;
        seed = DEFAULT_SCRAMBLER.scramble( seed );
        return out;
    }
}
