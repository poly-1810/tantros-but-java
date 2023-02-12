package poly.tantros.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.entities.abilities.ArmorPlateAbility;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;

public class TUnitTypes {
    public static UnitType

    // core
    trident,

    // submarines
    requiem
    ;

    public static void load() {
        trident = new UnitType("trident"){{
            constructor = UnitEntity::create;

            EntityMapping.nameMap.put(name, constructor);

            //envEnabled = 4;
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
            outlineColor = Color.valueOf("4a4b53");
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
                color = Color.valueOf("ffd37f");
            }});
        }};

        requiem = new UnitType("requiem"){{
            constructor = UnitEntity::create;

            EntityMapping.nameMap.put(name, constructor);

            //envEnabled = 4;
            health = 350f;
            flying = true;
            speed = 1.1f;
            accel = 0.08f;
            drag = 0.025f;
            hitSize = 17f;
            lowAltitude = true;
            rotateSpeed = 5f;
            outlineColor = Color.valueOf("4a4b53");
            healColor = Color.valueOf("8ca9e8");
            engineOffset = 0f;
            engineSize = 0f;
            armor = 7f;
            weapons.add(new Weapon("poly-tantros-requiem-silo"){{
                reload = 50f;
                recoil = 0f;
                outlineColor = Color.valueOf("4a4b53");
                rotate = false;
                mirror = true;
                alternate = false;
                x = 5.75f;
                y = -1.5f;
                shootY = 0.5f;
                layerOffset = -5f;
                baseRotation = -90f;
                shootCone = 360f;
                shootWarmupSpeed = 0.2f;
                minWarmup = 0.8f;
                parts.addAll(new RegionPart("-side"){{
                    mirror = true;
                    moveX = 3.75f;
                    moveY = -1f;
                    outlineColor = Color.valueOf("4a4b53");
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
    }
}
