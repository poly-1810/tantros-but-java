package tantros.content;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.payloads.PayloadDeconstructor;
import mindustry.world.blocks.payloads.PayloadMassDriver;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import tantros.world.blocks.payloads.CoveredPayloadConveyor;
import tantros.world.blocks.payloads.CoveredPayloadRouter;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block


    // misc
    corePod,
    // walls
    tcopperWall,
    // itemwalls
    tcopperBlock,
    // distribution
    payloadBelt, payloadDistributor, constructor, deconstructor, payloadDriver
    ;

    public static void load() {
        //region misc

        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(TItems.Tcopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250));
            alwaysUnlocked = true;
            armor = 15;
            incinerateNonBuildable = false;
            isFirstTier = true;
            unitType = UnitTypes.alpha;
            health = 1000;
            itemCapacity = 500;
            size = 2;
            unitCapModifier = 5;
        }};

        //endregion
        //region distribution
        payloadBelt = new CoveredPayloadConveyor("payload-belt") {{
            requirements(Category.units, with(TItems.nickel, 2));
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
            requirements(Category.units, with(TItems.Tcopper, 2, TItems.nickel, 4));
            canOverdrive = false;
            scaledHealth = 50;
            moveTime = 24;
            interp = Interp.pow2Out;
            underBullets = true;
            size = 2;
            payloadLimit = 1;
            category = Category.distribution;
        }};

        constructor = new Constructor("constructor") {{
            requirements(Category.units, with(TItems.nickel, 12));
            regionSuffix = "-dark";
            scaledHealth = 90;
            hasPower = false;
            buildSpeed = 0.5f;
            maxBlockSize = 1;
            size = 2;
            researchCostMultiplier = 0.25f;
            category = Category.distribution;
        }};

        deconstructor = new PayloadDeconstructor("deconstructor") {{
            requirements(Category.units, with(TItems.nickel, 12));
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

        payloadDriver = new PayloadMassDriver("payload-launcher") {{
            requirements(Category.units, with(Items.copper,1));
            regionSuffix ="-dark";
            size = 2;
            reload = 4f;
            chargeTime = 20f;
            maxPayloadSize = 1f;
            range = 56;
            grabWidth = 3f;
            grabHeight = 1.6f;
            length = 5.75f;
            rotateSpeed = 3.5f;
            knockback = 2;
            shake = 0.5f;
            shootEffect = Fx.none;
            category = Category.distribution;
        }};
        //endregion
        //region walls
        tcopperWall = new Wall("tcopper-wall"){{
            requirements(Category.defense, with(TItems.Tcopper, 6));
            scaledHealth = 70;
            armor = 2;
            size = 1;
            buildCostMultiplier = 4.8f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
               hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 8;
                    lifetime = 60f;
                    sizeFrom = 3.5f;
                    sizeTo = 0f;
                    colorFrom = Color.valueOf("29a88b");
                    colorTo = Color.valueOf("29a88b00");
                    length = 16f;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.pow2In;
                }};
               splashDamage = 12.5f;
               splashDamageRadius = 16f;
               killShooter = false;
            }};
        }};
        //endregion
        //region itemwalls
        tcopperBlock = new Wall("tcopper-block"){{
            requirements(Category.defense, with(TItems.Tcopper, 24));
            scaledHealth = 10;
            armor = 0;
            size = 1;
            buildCostMultiplier = 4.8f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 16;
                    lifetime = 120f;
                    sizeFrom = 6.5f;
                    sizeTo = 0f;
                    colorFrom = Color.valueOf("29a88b");
                    colorTo = Color.valueOf("29a88b00");
                    length = 32f;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.pow2In;
                }};
                splashDamage = 24f;
                splashDamageRadius = 32f;
                killShooter = false;
            }};
        }};


    }
}