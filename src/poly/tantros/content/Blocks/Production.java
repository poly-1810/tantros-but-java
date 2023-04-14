package poly.tantros.content.Blocks;

import arc.graphics.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.effect.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class Production {
    public static Block siftDrill;

    public static void load() {
        siftDrill = new SiftDrill("sift-drill"){{
            requirements(Category.production, with(TItems.tCopper, 20));

            envEnabled = Env.terrestrial | Env.underwater;
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
            updateEffect = Fx.none;
            updateEffectChance = 0f;
            tier = 2;
            drillEffect = new ParticleEffect(){{
                layer = 20f;
                colorFrom = Color.valueOf("d3ae8d99");
                colorTo = Color.valueOf("d3ae8d00");
                interp = Interp.pow3Out;
                particles = 3;
                length = 12f;
                sizeFrom = 2f;
                sizeTo = 0f;
                lifetime = 120f;
            }};
            drillEffectRnd = 8f;
            rotateSpeed = 0.5f;
            researchCostMultiplier = 0.125f;
        }};
    }
}
