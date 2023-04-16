package poly.tantros.content.Blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class Production {
    public static Block siftDrill;

    public static void load() {
        siftDrill = new SiftDrill("sift-drill"){{
            requirements(Category.production, with(TItems.tCopper, 20));

            envEnabled |= Env.underwater;
            scaledHealth = 65f;
            size = 3;
            squareSprite = false;
            fillsTile = false;
            customShadow = true;
            drillTime = 420f;
            liquidBoostIntensity = 1f;
            warmupSpeed = 1f / (2.5f * 60f);
            siftScl = 15f;
            siftMag = 6.25f;
            tier = 2;
            siftEffectMinDist = 15f / 4f;
            siftEffectMaxDist = 34f / 4f;
            researchCostMultiplier = 0.125f;
        }};
    }
}
