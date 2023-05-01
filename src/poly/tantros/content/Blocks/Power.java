package poly.tantros.content.Blocks;

import arc.graphics.*;
import arc.math.*;
import mindustry.entities.effect.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.power.*;

import static mindustry.type.ItemStack.*;

public class Power {
    public static Block

    // transfer
    powerConduit, powerPipe, powerCable,

    // generation
    surfaceSolarGenerator, hydraulicGenerator, steamGenerator

    ;

    public static void load() {
        powerConduit = new PowerConduit("power-conduit"){{
            requirements(Category.power, with(TItems.nickel, 5, TItems.tCopper, 10));

            envEnabled |= Env.underwater;
            scaledHealth = 25f;
            size = 1;
        }};
        
        powerPipe = new DirectionalBeamNode("power-pipe"){{
            requirements(Category.power, with(TItems.nickel, 2, TItems.tCopper, 2, TItems.zinc, 2));

            envEnabled |= Env.underwater;
            scaledHealth = 25f;
            size = 1;
            range = 4;
            laserColor2 = Color.valueOf("ffffff");
            pulseMag = 0f;
            laserWidth = 0.5f;
        }};

        powerCable = new PowerNode("power-cable"){{
            requirements(Category.power, with(TItems.nickel, 5, TItems.tCopper, 15, TItems.zinc, 5));

            envEnabled |= Env.underwater;
            scaledHealth = 30f;
            size = 2;
            laserRange = 16f;
            maxNodes = 1;
            laserScale = 0.75f;
            autolink = false;
            laserColor2 = Color.valueOf("ffffff");
        }};

        surfaceSolarGenerator = new FloatingSolarGenerator("floating-solar-panel"){{
            requirements(Category.power, with(TItems.nickel, 10));

            envEnabled |= Env.underwater;
            scaledHealth = 5f; // very fragile
            powerProduction = 90f / 60f;
            riseSpeed = 1f / (200f * 60f);
        }};

        hydraulicGenerator = new ConsumeGenerator("hydraulic-generator"){{
            requirements(Category.power, with(TItems.nickel, 30));

            envEnabled |= Env.underwater;
            scaledHealth = 90f;
            size = 2;
            powerProduction = 0.48f;
            itemDuration = 450f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawPistons(){{
                        sinMag = 1.5f;
                        sides = 4;
                        lenOffset = 0f;
                    }},
                    new DrawRegion("-mid"),
                    new DrawPistons(){{
                        sinMag = 1.5f;
                        sides = 2;
                        sinScl = 12f;
                        sinOffset = 75f;
                        lenOffset = 0f;
                    }},
                    new DrawDefault()
            );
        }};

        steamGenerator = new SteamGenerator("steam-generator"){{
            requirements(Category.power, with(TItems.tCopper, 30, TItems.nickel, 20, TItems.calcite, 20));

            envEnabled |= Env.underwater;
            scaledHealth = 90f;
            size = 3;
            powerProduction = 7.5f;
            maxHeat = 15f;
            effect = new ParticleEffect(){{
                layer = 50f;
                colorFrom = Color.valueOf("d4f0ff00");
                colorTo = Color.valueOf("d4f0ff66");
                interp = Interp.pow3Out;
                sizeInterp = Interp.pow2;
                particles = 2;
                length = 8f;
                sizeFrom = 4f;
                sizeTo = 0f;
                lifetime = 300f;
            }};
            effectChance = 0.025f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawParticles(){{
                        color = Color.valueOf("d4f0ff");
                        alpha = 0.35f;
                        particleSize = 4f;
                        particles = 12;
                        particleLife = 90f;
                        particleRad = 12f;
                        reverse = false;
                        particleSizeInterp = Interp.pow3Out;
                    }},
                    new DrawBlurSpin("-rotator", 8f),
                    new DrawDefault()
            );
        }};
    }
}
