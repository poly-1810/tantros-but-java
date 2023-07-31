package poly.tantros.content.Blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class Production{
    public static Block oreScanner, oreFracker, siftDrill, deepDrill;

    public static void load(){
        oreScanner = new OreScanner("ore-scanner"){{
            requirements(Category.production, BuildVisibility.sandboxOnly, with()); //TODO Placeholder, no cost yet

            envEnabled |= Env.underwater;
            size = 3;

            consumePower(1f); //Placeholder
        }};

        oreFracker = new OreFracker("ore-fracker"){{
            requirements(Category.production, BuildVisibility.sandboxOnly, with()); //TODO Placeholder, no cost yet

            envEnabled |= Env.underwater;
            int useAmount = 5;
            itemCapacity = useAmount; //Don't hold extras when destroying yourself.
            size = 12;
            fillsTile = false;
            //customShadow = true; //Commented for visiblity. Uncomment when an actual sprite is added.

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

            consumePower(15f);
        }};
    }
}
