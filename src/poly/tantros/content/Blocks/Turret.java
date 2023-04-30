package poly.tantros.content.Blocks;

import arc.graphics.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;

import static mindustry.type.ItemStack.*;

public class Turret {
    public static Block remnant, snap, spark, splice, swipe, wail;

    public static void load() {
        remnant = new ItemTurret("remnant"){{
            requirements(Category.turret, with(TItems.tCopper, 10, TItems.calcite, 15, TItems.nickel, 5));

            envEnabled |= Env.underwater;
            scaledHealth = 120f;
            size = 1;
            reload = 50f;
            recoil = 1.5f;
            range = 80f;
            shootY = 1f;
            shootSound = Sounds.mediumCannon;
            outlineColor = Color.valueOf("2d2f39");
            drawer = new DrawTurret("plated-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            mirror = true;
                            moveX = 1f;
                            progress = PartProgress.recoil;
                        }}
                );
            }};
            shoot = new ShootSummon(0f, 1f, 3f, 0f){{
                shots = 6;
            }};
            inaccuracy = 10f;
            velocityRnd = 0.45f;
            rotateSpeed = 5f;

            ammo(
                    TItems.calcite, new BasicBulletType(){{
                        speed = 10f;
                        lifetime = 20f;
                        damage = 10.25f;
                        drag = 0.05f;
                        width = 12f;
                        height = 20f;
                        knockback = 1f;
                        impact = true;
                        shootEffect = Fx.shootSmokeSquareSparse;
                        smokeEffect = Fx.none;
                        despawnEffect = new MultiEffect(
                                Fx.shootSmokeSquareSparse,
                                Fx.shootSmokeSquareSparse
                        );
                        hitEffect = Fx.shootSmokeSquareSparse;
                        hitColor = Color.valueOf("d0d8e7");
                        backColor = Color.valueOf("d0d8e7");
                        trailColor = Color.valueOf("d0d8e7");
                        trailWidth = 3.4f;
                        trailLength = 3;
                        frontColor = Color.valueOf("ffffff");
                        collideTerrain = true;
                    }}
            );

            researchCostMultiplier = 0.5f;
        }};
        snap = new ItemTurret("snap"){{
            requirements(Category.turret, with(TItems.tCopper, 50, TItems.calcite, 20, TItems.nickel, 20, TItems.carbon, 15));

            envEnabled |= Env.underwater;
            scaledHealth = 120f;
            size = 2;
            reload = 90f;
            recoil = 1.5f;
            range = 240f;
            shootY = -2.5f;
            ammoPerShot = 3;
            outlineColor = Color.valueOf("2d2f39");
            drawer = new DrawTurret("plated-"){{
                parts.addAll(
                        new RegionPart("-side"){{
                            mirror = true;
                        }}
                );
            }};
            shoot = new ShootPattern(){{
                shots = 3;
                shotDelay = 5f;
            }};
            inaccuracy = 10f;
            rotateSpeed = 2f;

            ammo(
                    TItems.tCopper, new BulletType(){{
                        speed = 0f;
                        lifetime = 0f;
                        damage = 0f;
                        spawnUnit = TUnitTypes.snapPiranha;
                        shootEffect = new MultiEffect(
                                Fx.colorSparkBig
                        );
                        smokeEffect = Fx.none;
                        despawnEffect = Fx.none;
                        hitEffect = Fx.none;
                        hitColor = Color.valueOf("ea8878");
                    }}
            );
        }};
        spark = new PowerTurret("spark"){{
            requirements(Category.turret, with(TItems.tCopper, 10, TItems.calcite, 10, TItems.zinc, 5, TItems.nickel, 5));

            envEnabled |= Env.underwater;
            scaledHealth = 140f;
            size = 1;
            reload = 150f;
            recoil = 0.75f;
            range = 90f;
            shootY = 0.75f;
            velocityRnd = 0.35f;
            inaccuracy = 10f;

            consumePower(0.75f);

            shootSound = Sounds.cannon;
            outlineColor = Color.valueOf("2d2f39");
            drawer = new DrawTurret("plated-");
            shoot = new ShootPattern(){{
                shots = 3;
                shotDelay = 15f;
            }};
            rotateSpeed = 4f;

            shootType = new BombBulletType(){{
                collides = false;
                lifetime = 180;
                speed = 6;
                drag = 0.05f;
                splashDamage = 5f;
                splashDamageRadius = 8f;
                sprite = "large-bomb";
                spin = 2;
                width = 16;
                height = 16;
                shrinkX = 0.2f;
                shrinkY = 0.2f;
                backColor = Color.valueOf("84a94b");
                hitColor = Color.valueOf("84a94b");
                trailColor = Color.valueOf("84a94b");
                trailWidth = 2.5f;
                trailLength = 16;
                collideTerrain = true;
            }};
        }};
        splice = new PowerTurret("splice"){{
            requirements(Category.turret, with(TItems.tCopper, 80, TItems.brass, 120, TItems.calcite, 60, TItems.zinc, 60, TItems.carbon, 40));

            envEnabled |= Env.underwater;
            scaledHealth = 120f;
            size = 3;
            reload = 90f;
            recoil = 2f;
            range = 80f;
            shootY = 4f;
            shootWarmupSpeed = 0.24f;
            minWarmup = 0.99f;
            accurateDelay = false;
            shootSound = Sounds.shootSmite;
            outlineColor = Color.valueOf("2d2f39");
            shoot = new ShootPattern(){{
                firstShotDelay = 40f;
            }};
            drawer = new DrawTurret("plated-"){{
                parts.addAll(
                        new RegionPart("-blade") {{
                            moveX = 2f;
                            moveY = -1f;
                            moveRot = -22.5f;
                            mirror = true;

                            progress = PartProgress.charge;
                            heatProgress = PartProgress.charge;
                        }}
                );
            }};
            consumePower(1.5f);

            coolantMultiplier = 6f;
            rotateSpeed = 2.5f;
            shootType = new LaserBulletType(102.5f){{
                length = 80f;
                width = 18f;
                hitEffect = Fx.hitLancer;
                sideAngle = 160f;
                sideWidth = 0.7f;
                sideLength = 24f;
                lifetime = 30f;
                knockback = 9f;
                status = TStatuses.stunned;
                statusDuration = 20f;
                shootEffect = Fx.shootSmokeSmite;
                smokeEffect = Fx.shootBigSmoke;
                hitColor = Color.valueOf("feb380");
                colors = new Color[]{Color.valueOf("feb38066"), Color.valueOf("feb380")};
            }};
        }};
        swipe = new ItemTurret("swipe"){{
            requirements(Category.turret, with(TItems.tCopper, 20, TItems.cobalt, 40, TItems.nickel, 50, TItems.carbon, 15));

            envEnabled |= Env.underwater;
            scaledHealth = 130f;
            size = 2;
            reload = 60f;
            recoil = 2f;
            range = 160f;
            shootSound = Sounds.shootSmite;
            minWarmup = 0.99f;
            shootWarmupSpeed = 0.24f;
            shootY = -0.5f;
            ammoPerShot = 2;
            heatColor = Color.valueOf("8ca9e8");

            ammo(
                    TItems.cobalt, new BasicBulletType(7f, 25){{
                        sprite = "large-orb";
                        width = 12f;
                        height = 16f;
                        hitSize = 8f;

                        shootEffect = new MultiEffect(Fx.shootTitan, Fx.colorSparkBig, new WaveEffect(){{
                            colorFrom = colorTo = Color.valueOf("8ca9e8");
                            lifetime = 12f;
                            sizeTo = 20f;
                            strokeFrom = 3f;
                            strokeTo = 0.3f;
                        }});
                        smokeEffect = Fx.shootSmokeSmite;
                        ammoMultiplier = 1;
                        pierceCap = 4;
                        pierce = true;
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Color.valueOf("8CA9E8");
                        frontColor = Color.white;
                        trailWidth = 2.8f;
                        trailLength = 9;
                        hitEffect = Fx.hitBulletColor;
                        buildingDamageMultiplier = 0.3f;

                        despawnEffect = new MultiEffect(Fx.hitBulletColor, new WaveEffect(){{
                            sizeTo = 30f;
                            colorFrom = colorTo = Color.valueOf("8ca9e8");
                            lifetime = 12f;
                        }});

                        trailRotation = true;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 3f;

                        intervalBullet = new LightningBulletType(){{
                            damage = 5;
                            collidesAir = false;
                            ammoMultiplier = 1f;
                            lightningColor = Color.valueOf("8ca9e8");
                            lightningLength = 5;
                            lightningLengthRand = 10;

                            buildingDamageMultiplier = 0.25f;

                            lightningType = new BulletType(0.0001f, 0f){{
                                lifetime = Fx.lightning.lifetime;
                                hitEffect = Fx.hitLancer;
                                despawnEffect = Fx.none;
                                status = StatusEffects.shocked;
                                statusDuration = 10f;
                                hittable = false;
                                lightColor = Color.white;
                                buildingDamageMultiplier = 0.25f;
                            }};
                        }};

                        bulletInterval = 3f;
                    }}
            );

            drawer = new DrawTurret("plated-");
        }};
        wail = new PowerTurret("wail"){{
            requirements(Category.turret, with(TItems.tCopper, 15, TItems.zinc, 10, TItems.carbon, 5));

            envEnabled |= Env.underwater;
            scaledHealth = 140f;
            size = 1;
            reload = 50f;
            recoil = 0.75f;
            range = 180f;
            shootY = 0.75f;
            velocityRnd = 0f;
            researchCostMultiplier = 0.25f;

            consumePower(0.5f);

            shootSound = Sounds.malignShoot;
            outlineColor = Color.valueOf("2d2f39");
            drawer = new DrawTurret("plated-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            mirror = false;
                            moveY = -0.75f;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("8ca9e8");
                        }},
                        new RegionPart("-side"){{
                            mirror = true;
                            moveY = 0.25f;
                            moveRot = -15f;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart(){{
                            shapes = 1;
                            tri = true;
                            mirror = true;
                            x = 3.5f;
                            moveY = -2f;
                            haloRadius = 0f;
                            haloRadiusTo = 0f;
                            haloRotation = 210f;
                            color = Color.valueOf("8ca9e8");
                            radius = 0f;
                            radiusTo = 3f;
                            triLength = 0f;
                            triLengthTo = 6f;
                            progress = PartProgress.warmup;
                            layer = 110f;
                        }},
                        new HaloPart(){{
                            shapes = 1;
                            tri = true;
                            mirror = true;
                            x = 3.5f;
                            moveY = -2f;
                            haloRadius = 0f;
                            haloRadiusTo = 0f;
                            haloRotation = 210f;
                            shapeRotation = 180f;
                            color = Color.valueOf("8ca9e8");
                            radius = 0f;
                            radiusTo = 3f;
                            triLength = 0f;
                            triLengthTo = 3f;
                            progress = PartProgress.warmup;
                            layer = 110f;
                        }}
                );
                shoot = new ShootPattern(){{
                    shots = 3;
                    shotDelay = 6f;
                }};
                rotateSpeed = 4f;
                shootType = new BasicBulletType(){{
                    sprite = "poly-tantros-bullet-wave";
                    speed = 3.5f;
                    drag = -0.01f;
                    lifetime = 45f;
                    damage = 2.25f;
                    pierceArmor = true;
                    width = 12f;
                    hitSize = 16f;
                    height = 12f;
                    shrinkX = -0.75f;
                    shrinkY = -0.5f;
                    shootEffect = Fx.none;
                    smokeEffect = Fx.none;
                    despawnEffect = new ParticleEffect(){{
                        region = "poly-tantros-bullet-wave-back";
                        particles = 1;
                        cone = 0f;
                        length = 16f;
                        baseLength = 16f;
                        lifetime = 32f;
                        offset = 90f;
                        sizeFrom = 6f;
                        sizeTo = 12f;
                        colorFrom = Color.valueOf("8ca9e8");
                        colorTo = Color.valueOf("8ca9e800");
                        interp = Interp.pow3Out;
                        offsetX = -12f;
                    }};
                    hitEffect = Fx.none;
                    pierce = true;
                    hitColor = Color.valueOf("8ca9e8");
                    backColor = Color.valueOf("8ca9e8");
                    trailColor = Color.valueOf("8ca9e8");
                    trailEffect = new ParticleEffect(){{
                        region = "poly-tantros-bullet-wave-back";
                        particles = 1;
                        cone = 0f;
                        length = 4f;
                        baseLength = 4f;
                        lifetime = 90f;
                        offset = 90f;
                        sizeFrom = 8f;
                        sizeTo = 4f;
                        colorFrom = Color.valueOf("8ca9e899");
                        colorTo = Color.valueOf("8ca9e800");
                    }};
                    trailRotation = true;
                    trailInterval = 5f;
                    frontColor = Color.valueOf("ffffff");
                    buildingDamageMultiplier = 0.15f;
                }};
            }};
        }};
    }
}
