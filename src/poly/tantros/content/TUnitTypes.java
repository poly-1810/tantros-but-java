package poly.tantros.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.abilities.ArmorPlateAbility;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;

public class TUnitTypes {
    public static UnitType trident;

    public static void load() {
        trident = new UnitType("trident"){{
            constructor = UnitEntity::create;
            coreUnitDock = true;

            EntityMapping.nameMap.put(name, constructor);

            envEnabled = 4;
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
            parts.add(new RegionPart("-glow"){{
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
            abilities.add(new MoveEffectAbility(){{
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
    }
}
