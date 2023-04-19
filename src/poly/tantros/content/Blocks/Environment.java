package poly.tantros.content.Blocks;

import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.environment.*;

public class Environment {
    public static Block

    // floor & walls
    calciteFloor, calciteGrains, calciteHill, copperMat,
    // ores
    oreCobalt, oreNickel, oreCopper, oreZinc,
    oreRubedo, oreRubedoHidden,
    oreCarbon, oreCarbonDeep, oreCarbonHidden,
    // underwater
    purbushBig, yellowCoralBig

    ;

    public static void load() {
        // floor & walls
        calciteFloor = new Floor("calcite-floor"){{
            itemDrop = TItems.calcite;
            playerUnmineable = true;
            wall = calciteHill;
        }};

        calciteGrains = new Floor("calcite-grains"){{
            itemDrop = TItems.calcite;
            playerUnmineable = false;
        }};

        calciteHill = new StaticWall("calcite-hill"){{
            variants = 3;
        }};

        copperMat = new Floor("copper-mat"){{
            variants = 4;
        }};

        // ores
        oreCobalt = new OreBlock("ore-cobalt", TItems.cobalt){{
            wallOre = true;
        }};

        oreNickel = new OreBlock("ore-nickel", TItems.nickel){{
            variants = 5;
        }};

        oreCopper = new OreBlock("ore-tantros-copper", TItems.tCopper);

        oreZinc = new OreBlock("ore-zinc", TItems.zinc){{
            variants = 5;
        }};

        oreRubedo = new RevealedOre("ore-rubedo", TItems.rubedo);
        oreRubedoHidden = new HiddenOreBlock("ore-rubedo-hidden", (OverlayFloor)oreRubedo);

        oreCarbon = new OreBlock("ore-carbon", TItems.carbon);
        oreCarbonDeep = new HiddenOreBlock("ore-carbon-deep", (OverlayFloor)oreCarbon, TItems.carbon);
        oreCarbonHidden = new HiddenOreBlock("ore-carbon-hidden", (OverlayFloor)oreCarbonDeep){{
            drawGame = true;
            variants = 2;
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
