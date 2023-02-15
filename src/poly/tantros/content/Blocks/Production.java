package poly.tantros.content.Blocks;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.Drill;
import poly.tantros.content.TItems;

import static mindustry.type.ItemStack.with;

public class Production {
    public static Block siftDrill;

    public static void load() {
        siftDrill = new Drill("sift-drill"){{
            requirements(Category.production, with(TItems.tCopper, 20));

            envEnabled = 4;
            scaledHealth = 65f;
            size = 3;
            squareSprite = false;
            fillsTile = false;
            customShadow = true;
            drillTime = 420f;
            liquidBoostIntensity = 1f;
            warmupSpeed = 0.003222222222f;
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
