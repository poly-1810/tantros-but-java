package poly.tantros.content.Blocks;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ExplosionBulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.type.Category;
import mindustry.world.Block;
import poly.tantros.content.TItems;
import poly.tantros.world.blocks.resources.ResourceBlock;

import static mindustry.type.ItemStack.with;

public class Resources {
    public static Block brassBlock, calciteBlock, cobaltBlock, nickelBlock, rubedoBlock, tCopperBlock, zincBlock;

    public static void load() {
        tCopperBlock = new ResourceBlock("tcopper-block"){{
            requirements(Category.distribution, with(TItems.tCopper, 24));

            envEnabled = 4;
            scaledHealth = 10;
            armor = 0;
            size = 1;
            buildCostMultiplier = 0.0f;
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
        brassBlock = new ResourceBlock("brass-block"){{
            requirements(Category.distribution, with(TItems.brass, 24));

            envEnabled = 4;
            scaledHealth = 10;
            armor = 4;
            size = 1;
            buildCostMultiplier = 0.0f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    line = true;
                    particles = 8;
                    lifetime = 60f;
                    sizeFrom = 6.5f;
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
            }};
        }};
        rubedoBlock = new ResourceBlock("rubedo-block"){{
            requirements(Category.distribution, with(TItems.rubedo, 24));

            envEnabled = 4;
            scaledHealth = 10;
            breakOnPlace = true;
            armor = 1;
            size = 1;
            buildCostMultiplier = 0.0f;
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
            }};
        }};
        cobaltBlock = new ResourceBlock("cobalt-block"){{
            requirements(Category.distribution, with(TItems.cobalt, 24));

            envEnabled = 4;
            scaledHealth = 10;
            armor = 4;
            size = 1;
            buildCostMultiplier = 0.0f;
            researchCostMultiplier = 0.25f;
            destroyBullet = new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = new ParticleEffect(){{
                    particles = 16;
                    lifetime = 120f;
                    sizeFrom = 6.5f;
                    sizeTo = 0f;
                    colorFrom = Color.valueOf("8ca9e8");
                    colorTo = Color.valueOf("8ca9e800");
                    length = 32f;
                    interp = Interp.pow3Out;
                    sizeInterp = Interp.pow2In;
                }};
                splashDamage = 6f;
                splashDamageRadius = 32f;
                killShooter = false;
                fragBullet = new BulletType(){{
                    lifetime = 0;
                    despawnEffect = Fx.none;
                    hitEffect = Fx.none;
                    damage = 0;
                    spawnBullets.add(new LightningBulletType(){{
                        damage = 3f;
                        lightningLength = 4;
                        lightningLengthRand = 2;
                        lightningColor = Color.valueOf("8ca9e8");
                    }}, new LightningBulletType(){{
                        damage = 3f;
                        lightningLength = 4;
                        lightningLengthRand = 2;
                        lightningColor = Color.valueOf("8ca9e8");
                    }});
                }};
            }};
        }};
        nickelBlock = new ResourceBlock("nickel-block"){{
            requirements(Category.distribution, with(TItems.nickel, 12));

            envEnabled = 4;
            scaledHealth = 10;
            armor = 3;
            size = 1;
            researchCostMultiplier = 0.25f;
        }};
    }
}
