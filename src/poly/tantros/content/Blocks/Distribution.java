package poly.tantros.content.Blocks;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.payloads.PayloadDeconstructor;
import mindustry.world.blocks.payloads.PayloadMassDriver;
import poly.tantros.content.TItems;
import poly.tantros.world.blocks.payloads.CoveredPayloadConveyor;
import poly.tantros.world.blocks.payloads.CoveredPayloadRouter;

import static mindustry.type.ItemStack.with;

public class Distribution {
    public static Block defabricator, fabricator, payloadBelt, payloadDistributor, payloadLauncher;

    public static void load() {
        defabricator = new PayloadDeconstructor("defabricator") {{
            requirements(Category.units, with(TItems.nickel, 12));

            envEnabled = 4;
            regionSuffix = "-dark";
            scaledHealth = 90;
            researchCostMultiplier = 0.25f;
            hasPower = false;
            itemCapacity = 60;
            size = 2;
            maxPayloadSize = 1;
            deconstructSpeed = 2.8f;
            category = Category.distribution;
        }};
        fabricator = new Constructor("fabricator") {{
            requirements(Category.units, with(TItems.nickel, 12));

            envEnabled = 4;
            regionSuffix = "-dark";
            scaledHealth = 90;
            hasPower = false;
            buildSpeed = 0.5f;
            maxBlockSize = 1;
            size = 2;
            researchCostMultiplier = 0.25f;
            category = Category.distribution;
        }};
        payloadBelt = new CoveredPayloadConveyor("payload-belt") {{
            requirements(Category.units, with(TItems.nickel, 2));

            envEnabled = 4;
            canOverdrive = false;
            scaledHealth = 50;
            moveTime = 24;
            underBullets = true;
            size = 2;
            payloadLimit = 1;
            interp = Interp.pow2Out;
            researchCostMultiplier = 0.25f;
            category = Category.distribution;
        }};
        payloadDistributor = new CoveredPayloadRouter("payload-distributor") {{
            requirements(Category.units, with(TItems.tCopper, 2, TItems.nickel, 4));

            envEnabled = 4;
            canOverdrive = false;
            scaledHealth = 50;
            moveTime = 24;
            interp = Interp.pow2Out;
            underBullets = true;
            size = 2;
            payloadLimit = 1;
            category = Category.distribution;
        }};
        payloadLauncher = new PayloadMassDriver("payload-launcher") {{
            requirements(Category.distribution, with(TItems.nickel, 8, TItems.tCopper, 8, TItems.calcite, 4, TItems.zinc, 4));

            envEnabled = 4;
            regionSuffix = "-dark";
            size = 2;
            reload = 4f;
            chargeTime = 20f;
            maxPayloadSize = 1f;
            range = 56f;
            grabWidth = 3f;
            grabHeight = 1.6f;
            length = 5.75f;
            rotateSpeed = 3.5f;
            knockback = 2;
            shake = 0.5f;
            shootEffect = Fx.none;
            smokeEffect = new ParticleEffect(){{
                colorFrom = Color.valueOf("efe3ff");
                colorTo = Color.valueOf("efe3ff");
                interp = Interp.pow3Out;
                particles = 8;
                cone = 30f;
                length = 32f;
                sizeFrom = 6f;
                sizeTo = 0f;
                lifetime = 70f;
                layer = 45f;
            }};
            receiveEffect = new WaveEffect(){{
                colorFrom = Color.valueOf("efe3ff");
                colorTo = Color.valueOf("efe3ff");
                strokeFrom = 4f;
                strokeTo = 0f;
                sizeFrom = 0f;
                sizeTo = 12f;
                lifetime = 25f;
                layer = 75f;
            }};
        }};
    }
}
