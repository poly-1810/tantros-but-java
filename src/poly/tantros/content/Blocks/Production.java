package poly.tantros.content.Blocks;

import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.graphics.*;
import poly.tantros.world.blocks.production.*;
import poly.tantros.world.draw.*;

import static mindustry.type.ItemStack.*;

public class Production {
    public static Block oreScanner, oreFracker, siftDrill, deepDrill;

    public static void load() {
        oreScanner = new OreScanner("ore-scanner"){{
            requirements(Category.production, BuildVisibility.sandboxOnly, with()); //TODO Placeholder, no cost yet

            envEnabled |= Env.underwater;
            size = 3;

            consumePower(1f); //Placeholder
        }};

        oreFracker = new OreFracker("ore-fracker"){{
            requirements(Category.production, BuildVisibility.sandboxOnly, with()); //TODO Placeholder, no cost yet

            envEnabled |= Env.underwater;
            int useAmount = 10;
            itemCapacity = useAmount;
            size = 12;
            fillsTile = false;
            customShadow = true;
            drawCracks = false;

            blastEffect = new MultiEffect(
                Fx.dynamicSpikes.wrap(TPal.rubedoLight, 30f),
                Fx.mineImpactWave.wrap(TPal.rubedoLight, 45f),
                TFx.frackFlame
            );

            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawSlammers(70f / 4f){{
                    shadowOffset = 4f;
                    baseOffset = 80f / 4f;
                    layer = Layer.blockOver;
                }},
                new DrawRegion("-top"){{
                    layer = Layer.blockOver + 0.1f;
                }}
            );

            consumeItem(TItems.rubedo, useAmount); //Placeholder
        }};

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

        deepDrill = new DeepDrill("deep-drill"){{
            requirements(Category.production, with(TItems.carbon, 1));

            envEnabled |= Env.underwater;
            scaledHealth = 65f;
            allowedBlocks.addAll(
                    Resources.rubedoBlock,
                    Resources.tCopperBlock
            );
            size = 4;

            consumePower(20f);
        }};
    }
}
