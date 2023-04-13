package poly.tantros.content.Blocks;

import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.SeaBush;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.meta.*;
import poly.tantros.content.TItems;

public class Environment {
    public static Block

    // floor & walls
    calciteFloor, calciteGrains, calciteHill, copperMat,
    // ores
    oreCobalt, oreNickel, oreCopper, oreZinc,
    // underwater
    purbushBig, yellowCoralBig

    ;

    public static void load() {
        // floor & walls
        calciteFloor = new Floor("calcite-floor"){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 3;
            size = 1;
            itemDrop = TItems.calcite;
            playerUnmineable = true;
            wall = calciteHill;
        }};
        calciteGrains = new Floor("calcite-grains"){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 3;
            size = 1;
            itemDrop = TItems.calcite;
            playerUnmineable = false;
        }};
        calciteHill = new StaticWall("calcite-hill"){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 3;
            size = 1;
        }};
        copperMat = new Floor("copper-mat"){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 4;
            size = 1;
        }};

        // ores
        oreCobalt = new OreBlock("ore-cobalt", TItems.cobalt){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 3;
            size = 1;
            wallOre = true;
        }};
        oreNickel = new OreBlock("ore-nickel", TItems.nickel){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 5;
            size = 1;
            itemDrop = TItems.nickel;
        }};
        oreCopper = new OreBlock("ore-tantros-copper", TItems.tCopper){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 3;
            size = 1;
            itemDrop = TItems.tCopper;
        }};
        oreZinc = new OreBlock("ore-zinc", TItems.zinc){{
            envEnabled = Env.terrestrial | Env.underwater;
            variants = 5;
            size = 1;
            itemDrop = TItems.zinc;
        }};

        // underwater
        purbushBig = new SeaBush("pur-bush-big"){{
            lobesMin = 8;
            lobesMax = 10;
            botAngle = 60f;
            origin = 0.1f;
            timeRange = 55f;
            spread = 10f;
        }};
        yellowCoralBig = new SeaBush("yellow-coral-big"){{
            lobesMin = 3;
            lobesMax = 5;
            magMin = 1f;
            magMax = 7f;
            origin = 0.4f;
            spread = 50f;
            sclMin = 60f;
            sclMax = 90f;
        }};
    }
}
