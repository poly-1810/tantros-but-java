package poly.tantros.content.Blocks;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.pattern.ShootSummon;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.draw.DrawTurret;
import poly.tantros.content.TItems;
import poly.tantros.content.TUnitTypes;

import static mindustry.type.ItemStack.with;

public class Turret {
    public static Block remnant, snap, spark, splice, wail;

    public static void load() {
        remnant = new ItemTurret("remnant"){{
            requirements(Category.turret, with(TItems.tCopper, 10, TItems.calcite, 15, TItems.nickel, 5));

            envEnabled = 4;
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
            requirements(Category.turret, with(TItems.tCopper, 50, TItems.calcite, 20, TItems.nickel, 20));

            envEnabled = 4;
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
        // TODO fix this
        /* spark = new PowerTurret("spark"){{
            requirements(Category.turret, with(TItems.tCopper, 10, TItems.calcite, 10, TItems.zinc, 5, TItems.nickel, 5));

            envEnabled = 4;
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
        }}; */
        splice = new PowerTurret("splice"){{
            requirements(Category.turret, with(TItems.tCopper, 80, TItems.brass, 120, TItems.calcite, 60, TItems.zinc, 60));

            envEnabled = 4;
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
                shootEffect = Fx.shootSmokeSmite;
                smokeEffect = Fx.shootBigSmoke;
                hitColor = Color.valueOf("feb380");
                colors = new Color[]{Color.valueOf("feb38066"), Color.valueOf("feb380")};
            }};
        }};
        wail = new PowerTurret("wail"){{
            requirements(Category.turret, with(TItems.tCopper, 15, TItems.zinc, 10));

            envEnabled = 4;
            scaledHealth = 140f;
            size = 1;
            reload = 50f;
            recoil = 0.75f;
            range = 180f;
            shootY = 0.75f;
            velocityRnd = 0f;

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
                }};
            }};
        }};
    }
}
