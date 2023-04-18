package poly.tantros.content;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.world.meta.*;

public class TUnitTypes {
    public static UnitType

    // core
    trident,

    // assembler
    assemblySub,

    // submarine
    requiem,

    // quad
    chasm,

    // isopod
    tendril,

    // crustacean (maybe)
    chain,
    chainDrone,

    // bullet
    snapPiranha

    ;

    public static void load() {
        trident = new UnitType("trident"){{
            constructor = UnitEntity::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            health = 400f;
            flying = true;
            speed = 2.75f;
            accel = 0.1f;
            drag = 0.05f;
            hitSize = 17f;
            lowAltitude = true;
            ammoCapacity = 50;
            ammoType = new ItemAmmoType(){{
                range = 32f;
                ammoPerItem = 3;
                item = TItems.zinc;
            }};
            rotateSpeed = 8f;
            mineWalls = true;
            mineFloor = true;
            mineHardnessScaling = true;
            mineSpeed = 5f;
            mineTier = 2;
            buildSpeed = 0.5f;
            buildRange = 96f;
            itemCapacity = 100;
            outlineColor = Color.valueOf("3B3C43");
            healColor = Color.valueOf("ffd37f");
            engineOffset = 0f;
            engineSize = 0f;
            armor = 6f;
            parts.addAll(new RegionPart("-glow"){{
                color = Color.valueOf("4a4b5300");
                colorTo = Color.valueOf("4a4b5366");
                layer = -1;
                outline = false;
                blending = Blending.additive;
            }}, new RegionPart("-armor"){{
                color = Color.valueOf("ffd37f00");
                colorTo = Color.valueOf("ffd37f66");
                layer = 110;
                outline = false;
            }});
            weapons.add(new Weapon(){{
                reload = 12f;
                rotate = false;
                shootCone = 360;
                mirror = false;
                predictTarget = false;
                minShootVelocity = 0.5f;
                inaccuracy = 90f;
                velocityRnd = 0.5f;
                x = 0;
                y = 0;
                shoot = new ShootPattern(){{
                   shots = 5;
                   shotDelay = 0f;
                }};
                bullet = new BombBulletType(){{
                   speed = 0.5f;
                   width = 9f;
                   height = 11f;
                   shrinkY = 0.4f;
                   splashDamage = 17.5f;
                   splashDamageRadius = 24f;
                   ammoMultiplier = 1f;
                   shootEffect = Fx.none;
                   backColor = Color.valueOf("ffd37f");
                   frontColor = Color.valueOf("ffffff");
                   hitColor = Color.valueOf("ffd37f");
                   rangeOverride = 48f;
                }};
            }});
            abilities.addAll(new MoveEffectAbility(){{
                x = 0;
                y = -5f;
                rotateEffect = true;
                interval = 3f;
                teamColor = true;
                effect = Fx.disperseTrail;
                parentizeEffects = false;
            }}, new ArmorPlateAbility(){{
                healthMultiplier = 0.5f;
                color = Color.valueOf("FFBA367F");
            }});
        }};

        assemblySub = new UnitType("assembly-sub"){{
            constructor = BuildingTetherPayloadUnit::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            aiController = AssemblerAI::new;
            createWreck = false;
            createScorch = false;
            logicControllable = false;
            playerControllable = false;
            isEnemy = false;
            useUnitCap = false;
            allowedInPayloads = false;
            flying = true;
            hidden = true;
            outlineColor = Color.valueOf("4a4b53");
            targetPriority = -1f;
            speed = 0.9f;
            accel = 0.08f;
            drag = 0.015f;
            health = 100f;
            armor = 6f;
            maxRange = 0f;
            lowAltitude = false;
            hitSize = 9f;
            targetable = false;
            rotateSpeed = 3f;
            fogRadius = 8f;
            engineSize = 0;
            parts.addAll(
                    new RegionPart("-glow"){{
                        color = Color.valueOf("d4816b66");
                        layer = -1f;
                        outline = false;
                        blending = Blending.additive;
                    }},
                    new HaloPart(){{
                        y = 3.5f;
                        tri = true;
                        color = Color.valueOf("ffd37f");
                        haloRadius = 2.5f;
                        haloRotateSpeed = 1f;
                        rotateSpeed = 0f;
                        layerOffset = -1f;
                        shapes = 2;
                        radius = 4f;
                        triLength = 4f;
                    }},
                    new ShapePart(){{
                        y = 3.5f;
                        circle = true;
                        hollow = true;
                        color = Color.valueOf("ffd37f");
                        radius = 3f;
                        stroke = 1.5f;
                        layerOffset = -1f;
                    }}
            );
            abilities.addAll(
                    new MoveEffectAbility(){{
                        x = 0f;
                        y = -3.5f;
                        rotateEffect = true;
                        interval = 5f;
                        teamColor = true;
                        effect = Fx.disperseTrail;
                        parentizeEffects = false;
                    }},
                    new MoveEffectAbility(){{
                        x = 3f;
                        y = 1.5f;
                        rotation = 45f;
                        rotateEffect = true;
                        interval = 5f;
                        teamColor = true;
                        effect = Fx.disperseTrail;
                        parentizeEffects = true;
                    }},
                    new MoveEffectAbility(){{
                        x = -3f;
                        y = 1.5f;
                        rotation = -45f;
                        rotateEffect = true;
                        interval = 5f;
                        teamColor = true;
                        effect = Fx.disperseTrail;
                        parentizeEffects = true;
                    }}
            );
        }};

        requiem = new UnitType("requiem"){{
            constructor = UnitEntity::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            health = 350f;
            flying = true;
            speed = 1.1f;
            accel = 0.08f;
            drag = 0.025f;
            hitSize = 17f;
            lowAltitude = true;
            rotateSpeed = 5f;
            outlineColor = Color.valueOf("3B3C43");
            healColor = Color.valueOf("8ca9e8");
            engineOffset = 0f;
            engineSize = 0f;
            armor = 7f;
            weapons.add(new Weapon("poly-tantros-requiem-silo"){{
                reload = 50f;
                recoil = 0;
                rotate = false;
                mirror = true;
                alternate = false;
                x = 8.75f;
                y = -1.5f;
                shootY = 0.5f;
                layerOffset = -5f;
                baseRotation = -70f;
                shootCone = 360f;
                shootWarmupSpeed = 0.2f;
                minWarmup = 0.8f;
                parts.addAll(new RegionPart("-side"){{
                    mirror = true;
                    moveX = 3.75f;
                    moveY = -1f;
                    layerOffset = 1f;
                    progress = PartProgress.warmup;
                }});
                shoot = new ShootAlternate(){{
                   shots = 2;
                   barrels = 2;
                   spread = 3.5f;
                }};
                bullet = new BasicBulletType(){{
                   damage = 8.5f;
                   splashDamage = 15f;
                   splashDamageRadius = 24f;
                   speed = 2f;
                   drag = -0.03f;
                   width = 11f;
                   height = 16f;
                   lifetime = 40f;
                   frontColor = Color.valueOf("ffffff");
                   hitColor = Color.valueOf("8ca9e8");
                   backColor = Color.valueOf("8ca9e8");
                   trailColor = Color.valueOf("8ca9e8");
                   trailWidth = 2f;
                   trailLength = 6;
                   homingPower = 0.2f;
                   homingRange = 80f;
                   homingDelay = 10f;
                   despawnEffect = new ParticleEffect(){{
                       colorFrom = Color.valueOf("8ca9e8");
                       colorTo = Color.valueOf("8ca9e800");
                       interp = Interp.pow4Out;
                       particles = 6;
                       length = 24f;
                       sizeFrom = 6f;
                       sizeTo = 0f;
                       lifetime = 70f;
                   }};
                   hitEffect = new ParticleEffect(){{
                       colorFrom = Color.valueOf("8ca9e8");
                       colorTo = Color.valueOf("8ca9e800");
                       interp = Interp.pow4Out;
                       particles = 6;
                       length = 24f;
                       sizeFrom = 6f;
                       sizeTo = 0f;
                       lifetime = 70f;
                   }};
                   rangeOverride = 68f;
                }};
            }});
            abilities.addAll(new MoveEffectAbility(){{
                x = 6;
                y = -9;
                rotateEffect = true;
                interval = 4;
                teamColor = true;
                effect = Fx.disperseTrail;
                parentizeEffects = false;
            }}, new MoveEffectAbility(){{
                x = -6;
                y = -9;
                rotateEffect = true;
                interval = 4;
                teamColor = true;
                effect = Fx.disperseTrail;
                parentizeEffects = false;
            }});
        }};

        chasm = new TankUnitType("chasm"){{
            constructor = TankUnit::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            health = 270f;
            speed = 2.2f;
            accel = 0.045f;
            drag = 0.02f;
            rotateMoveFirst = true;
            omniMovement = false;
            treadRects = new Rect[]{new Rect(16, 17, 22, 25), new Rect(21, -34, 15, 22)};
            treadFrames = 5;
            rotateSpeed = 4f;
            hitSize = 19f;
            armor = 5f;
            outlineColor = Color.valueOf("4a4b53");
            healColor = Color.valueOf("feb380");
            weapons.addAll(
                    new Weapon("poly-tantros-chasm-weapon"){{
                        reload = 12f;
                        rotate = true;
                        rotationLimit = 135f;
                        rotateSpeed = 2f;
                        mirror = true;
                        predictTarget = false;
                        recoil = 2f;
                        x = 4.5f;
                        y = 0;
                        parts.addAll(
                                new RegionPart("-top"){{
                                    under = true;
                                    layerOffset = 0.001f;
                                    moveY = -1.25f;
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.warmup;
                                }}
                        );
                        bullet = new SapBulletType(){{
                            length = 64f;
                            width = 0.5f;
                            damage = 12.5f;
                            sapStrength = 0.5f;
                            color = Color.valueOf("feb380");
                        }};
                    }}
            );
        }};

        tendril = new UnitType("tendril"){{
            constructor = CrawlUnit::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            health = 450f;
            speed = 0.8f;
            omniMovement = false;
            segments = 2;
            drawBody = false;
            drawCell = false;
            crushDamage = 2f;
            segmentPhase = 5f;
            segmentRotSpeed = 2f;
            segmentMag = 1.5f;
            segmentScl = 1f;
            aiController = HugAI::new;
            rotateSpeed = 3f;
            hitSize = 22f;
            armor = 8f;
            outlineColor = Color.valueOf("4a4b53");
            healColor = Color.valueOf("feb380");
        }};

        chain = new UnitType("chain"){{
            constructor = LegsUnit::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            groundLayer = 75f;
            health = 270f;
            armor = 6f;
            speed = 1.45f;
            drag = 0.2f;
            hitSize = 9f;
            buildRange = 96f;
            fogRadius = 8f;
            ammoCapacity = 20;
            ammoType = new ItemAmmoType(){{
                range = 32f;
                ammoPerItem = 5;
                item = TItems.cobalt;
            }};
            rotateSpeed = 5f;
            lockLegBase = true;
            legContinuousMove = true;
            legGroupSize = 3;
            legStraightness = 0.2f;
            baseLegStraightness = 0.3f;
            legStraightLength = 0.8f;
            legCount = 6;
            legLength = 14f;
            legExtension = -3f;
            legBaseOffset = 2f;
            legMaxLength = 1.3f;
            legMinLength = 0.15f;
            legMoveSpace = 1.2f;
            legForwardScl = 1.1f;
            rippleScale = 0.2f;
            drownTimeMultiplier = 150f;
            mineWalls = true;
            mineFloor = true;
            mineHardnessScaling = true;
            mineSpeed = 5f;
            mineTier = 2;
            buildSpeed = 0.2f;
            itemCapacity = 100;
            outlineColor = Color.valueOf("4a4b53");
            healColor = Color.valueOf("ffd37f");
            weapons.addAll(
                    new Weapon("poly-tantros-chain-claw"){{
                        reload = 25f;
                        rotate = true;
                        rotationLimit = 45f;
                        rotateSpeed = 2f;
                        mirror = true;
                        predictTarget = false;
                        recoil = -3.5f;
                        top = false;
                        x = 6.5f;
                        y = 2f;
                        parts.addAll(
                                new RegionPart("-mandible-l"){{
                                    under = true;
                                    moveX = 1.25f;
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.warmup;
                                }},
                                new RegionPart("-mandible-r"){{
                                    under = true;
                                    moveX = -1.25f;
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.warmup;
                                }}
                        );
                        bullet = new LaserBulletType(){{
                            lifetime = 20f;
                            length = 48f;
                            damage = 72.5f;
                            colors = new Color[]{Color.valueOf("ffd37f66"), Color.valueOf("ffd37f")};
                        }};
                    }}
            );
            abilities.addAll(
                    new UnitSpawnAbility(){{
                        spawnX = 0f;
                        spawnY = -3.5f;
                        unit = chainDrone;
                        spawnTime = 1800f;
                        spawnEffect = Fx.shootSmokeTitan;
                        parentizeEffects = true;
                    }}
            );
        }};
        chainDrone = new UnitType("chain-drone"){{
            constructor = UnitEntity::create;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled |= Env.underwater;
            aiController = DefenderAI::new;
            defaultCommand = UnitCommand.rebuildCommand;
            isEnemy = false;
            flying = true;
            hidden = true;
            outlineColor = Color.valueOf("4a4b53");
            targetPriority = -1f;
            speed = 2f;
            accel = 0.1f;
            drag = 0.05f;
            lifetime = 450f;
            health = 75f;
            armor = 3f;
            maxRange = 0f;
            drawCell = false;
            lowAltitude = true;
            hitSize = 4f;
            targetable = true;
            trailLength = 12;
            engineSize = 2.5f;
            rotateSpeed = 5f;
            buildSpeed = 0.1f;
        }};

        snapPiranha = new MissileUnitType("snap-piranha"){{
            envEnabled |= Env.underwater;
            createWreck = false;
            createScorch = false;
            aiController = MissileAI::new;
            logicControllable = false;
            playerControllable = false;
            isEnemy = false;
            useUnitCap = false;
            allowedInPayloads = false;
            flying = true;
            physics = true;
            hidden = true;
            outlineColor = Color.valueOf("4a4b53");
            targetPriority = -1f;
            speed = 3.5f;
            lifetime = 140f;
            health = 15f;
            armor = 4f;
            maxRange = 0f;
            drawCell = false;
            lowAltitude = true;
            hitSize = 7f;
            targetable = true;
            trailLength = 5;
            rotateSpeed = 11f;
            fogRadius = 0f;
            missileAccelTime = 5f;
            homingDelay = 15f;
            engineColor = Color.valueOf("ea8878");
            trailColor = Color.valueOf("ea8878");
            parts.addAll(
                    new RegionPart("-jaw"){{
                        mirror = true;
                        under = true;
                        x = -1f;
                        y = 2f;
                        rotation = 30f;
                        moveRot = -20f;
                        progress = PartProgress.recoil;
                        layerOffset = -1f;
                    }}
            );
            weapons.addAll(
                    new Weapon(){{
                        x = 0f;
                        y = 0f;
                        shootY = 2f;
                        reload = 15f;
                        mirror = false;
                        rotate = true;
                        rotationLimit = 80;
                        rotateSpeed = 360f;
                        autoTarget = true;
                        predictTarget = false;
                        controllable = false;
                        shootCone = 20f;
                        targetInterval = 0f;
                        targetSwitchInterval = 0f;
                        recoilTime = 40f;
                        bullet = new LaserBulletType(){{
                            damage = 5f;
                            length = 16f;
                            width = 24f;
                            lengthFalloff = 0.35f;
                            sideLength = 16f;
                            sideWidth = 1.5f;
                            sideAngle = 35f;
                            colors = new Color[]{Color.valueOf("ea8878"), Color.valueOf("ea8878")};
                            shootEffect = Fx.none;
                            smokeEffect = Fx.none;
                            hitColor = Color.valueOf("ea8878");
                        }};
                    }}
            );
        }};
    }
}
