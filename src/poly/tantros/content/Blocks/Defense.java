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
import mindustry.world.blocks.defense.Wall;
import poly.tantros.content.TItems;

import static mindustry.type.ItemStack.with;

public class Defense {
    public static Block brassWall, calciteWall, cobaltWall, nickelWall, rubedoWall, tCopperWall, zincWall;

    public static void load() {
        brassWall = new Wall("brass-wall"){{
            requirements(Category.defense, with(TItems.brass, 6));

            envEnabled = 4;
            scaledHealth = 140f;
            armor = 4f;
            size = 1;
            researchCostMultiplier = 0.5f;
        }};
        calciteWall = new Wall("calcite-wall"){{
            requirements(Category.defense, with(TItems.calcite, 6));

            envEnabled = 4;
            scaledHealth = 145f;
            armor = 2f;
            size = 1;
            buildCostMultiplier = 7.2f;
            researchCostMultiplier = 0.25f;
        }};
        cobaltWall = new Wall("cobalt-wall"){{
            requirements(Category.defense, with(TItems.cobalt, 6));

            envEnabled = 4;
            scaledHealth = 110f;
            armor = 4f;
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

            envEnabled = 4;
            scaledHealth = 110f;
            armor = 3f;
            size = 1;
            researchCostMultiplier = 0.25f;
        }};
        tCopperWall = new Wall("tcopper-wall"){{
            requirements(Category.defense, with(TItems.tCopper, 6));

            envEnabled = 4;
            scaledHealth = 70f;
            armor = 2f;
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
        zincWall = new Wall("zinc-wall"){{
            requirements(Category.defense, with(TItems.zinc, 6));

            envEnabled = 4;
            scaledHealth = 90f;
            armor = 3f;
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
    }
}
