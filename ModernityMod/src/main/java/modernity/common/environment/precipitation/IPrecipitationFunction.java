/*
 * Copyright (c) 2020 RedGalaxy & contributors
 * All rights reserved. Do not distribute.
 *
 * For a full license, see LICENSE.txt
 */

package modernity.common.environment.precipitation;

import net.redgalaxy.util.MathUtil;

@FunctionalInterface
public interface IPrecipitationFunction {
    IPrecipitation computePrecipitation( int level );

    static IPrecipitationFunction selectFrom( IPrecipitation... types ) {
        return level -> types[ MathUtil.clamp( level, 0, types.length - 1 ) ];
    }

    static IPrecipitationFunction none() {
        return level -> EDefaultPrecipitation.NONE;
    }

    static IPrecipitationFunction standard() {
        return selectFrom(
            EDefaultPrecipitation.NONE,
            EDefaultPrecipitation.DRIZZLE,
            EDefaultPrecipitation.SHOWERS,
            EDefaultPrecipitation.RAIN,
            EDefaultPrecipitation.HAIL
        );
    }

    static IPrecipitationFunction dryish() {
        return selectFrom(
            EDefaultPrecipitation.NONE,
            EDefaultPrecipitation.NONE,
            EDefaultPrecipitation.DRIZZLE,
            EDefaultPrecipitation.SHOWERS,
            EDefaultPrecipitation.RAIN
        );
    }

    static IPrecipitationFunction cold() {
        return selectFrom(
            EDefaultPrecipitation.NONE,
            EDefaultPrecipitation.DRIZZLE,
            EDefaultPrecipitation.WET_SNOW,
            EDefaultPrecipitation.SNOW,
            EDefaultPrecipitation.HEAVY_SNOW
        );
    }

    static IPrecipitationFunction swampy() {
        return selectFrom(
            EDefaultPrecipitation.NONE,
            EDefaultPrecipitation.SHOWERS,
            EDefaultPrecipitation.RAIN,
            EDefaultPrecipitation.RAIN
        );
    }
}
