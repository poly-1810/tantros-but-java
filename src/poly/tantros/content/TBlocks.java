package poly.tantros.content;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.payloads.PayloadDeconstructor;
import mindustry.world.blocks.payloads.PayloadMassDriver;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import poly.tantros.world.blocks.resources.ResourceBlock;
import poly.tantros.world.blocks.payloads.CoveredPayloadConveyor;
import poly.tantros.world.blocks.payloads.CoveredPayloadRouter;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block


    // misc
    corePod,
    // walls
    brassWall, calciteWall, cobaltWall, rubedoWall, nickelWall, tcopperWall, zincWall,
    // blocks
    brassBlock, calciteBlock, cobaltBlock, rubedoBlock, nickelBlock, tcopperBlock, zincBlock,
    // distribution
    payloadBelt, payloadDistributor, fabricator, defabricator, payloadLauncher
    ;

    public static void load() {
        //region misc

        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, with(TItems.tCopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250));
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
            requirements(Category.units, with(TItems.tCopper, 2, TItems.nickel, 4));
            canOverdrive = false;
            scaledHealth = 50;
            moveTime = 24;
            interp = Interp.pow2Out;
            underBullets = true;
            size = 2;
            payloadLimit = 1;
            category = Category.distribution;
        }};

        fabricator = new Constructor("fabricator") {{
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

        defabricator = new PayloadDeconstructor("defabricator") {{
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

        payloadLauncher = new PayloadMassDriver("payload-launcher") {{
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
            requirements(Category.defense, with(TItems.tCopper, 6));
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
        brassWall = new Wall("brass-wall"){{
            requirements(Category.defense, with(TItems.brass, 6));
            armor = 4;
            size = 1;
            scaledHealth = 140;
            researchCostMultiplier = 0.5f;
        }};
        calciteWall = new Wall("calcite-wall"){{
            requirements(Category.defense, with(TItems.calcite, 6));
            scaledHealth = 145;
            armor = 2;
            size = 1;
            buildCostMultiplier = 7.2f;
            researchCostMultiplier = 0.25f;
        }};

        cobaltWall = new Wall("cobalt-wall"){{
            requirements(Category.defense, with(TItems.cobalt, 6));
            armor = 4;
            scaledHealth = 110;
            size = 1;
            destroyBullet = new BulletType(){{
                lifetime = 0;
                despawnEffect = Fx.none;
                hitEffect = Fx.none;
                damage = 0;
                spawnBullets.add(new LightningBulletType(){{
                    damage = 15f;
                    lightningLength = 5;
                    lightningLengthRand = 2;
                    lightningColor = Color.valueOf("8ca9e8");
                }}, new LightningBulletType(){{
                    damage = 15f;
                    lightningLength = 5;
                    lightningLengthRand = 2;
                    lightningColor = Color.valueOf("8ca9e8");
                }});
            }};
        }};
        nickelWall = new Wall("nickel-wall"){{
            requirements(Category.defense, with(TItems.nickel, 6));
            scaledHealth = 110;
            armor = 3;
            size = 1;
            researchCostMultiplier = 0.25f;
        }};
        zincWall = new Wall("zinc-wall"){{
            requirements(Category.defense, with(TItems.zinc, 6));
            scaledHealth = 90;
            armor = 3;
            size = 1;
            buildCostMultiplier = 7.2f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 4;
                    lifetime = 60;
                    sizeFrom = 3;
                    sizeTo = 0;
                    colorFrom = Color.valueOf("9A9DBF");
                    colorTo = Color.valueOf("9A9DBF");
                    length = 16;
                    offset = 45;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.linear;
                }};
                splashDamage = 0;
                splashDamageRadius = 0;
                collides = false;
                killShooter = false;
            }};
            researchCostMultiplier = 0.5f;
        }};

        //endregion
        //region itemwalls
        tcopperBlock = new ResourceBlock("tcopper-block"){{
            requirements(null, BuildVisibility.editorOnly, with(TItems.tCopper, 24));
            scaledHealth = 10;
            armor = 0;
            size = 1;
            buildCostMultiplier = 0.2f;
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
                category = Category.distribution;
            }};
        }};
        brassBlock = new ResourceBlock("brass-block"){{
            requirements(null, BuildVisibility.editorOnly, with(TItems.brass, 24));
            scaledHealth = 30;
            armor = 4;
            size = 1;
            buildCostMultiplier = 2.0f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 8;
                    lifetime = 60f;
                    sizeFrom = 3.5f;
                    sizeTo = 0f;
                    colorFrom = Color.valueOf("EDC687");
                    colorTo = Color.valueOf("EDC68700");
                    length = 16f;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.pow2In;
                }};
                splashDamage = 35f;
                splashDamageRadius = 16f;
                killShooter = false;
                category = Category.distribution;
            }};
        }};
        rubedoBlock = new ResourceBlock("rubedo-block"){{
            requirements(null, BuildVisibility.editorOnly,  with(TItems.rubedo, 24));
            scaledHealth = 0;
            breakOnPlace = true;
            armor = 4;
            size = 1;
            buildCostMultiplier = 4.8f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 64;
                    lifetime = 240;
                    sizeFrom = 16.5f;
                    sizeTo = 0f;
                    colorFrom = Color.valueOf("CE735EFF");
                    colorTo = Color.valueOf("7E2642FF");
                    length = 64f;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.pow2In;
                }};
                splashDamage = 300f;
                splashDamageRadius = 64f;
                killShooter = false;
                category = Category.distribution;
            }};
        }};
    }
}