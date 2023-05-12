package poly.tantros.content.Blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.core.*;

import static mindustry.type.ItemStack.*;

public class Effect {
    public static Block
    //Core
    corePod, coreBranch, coreInput, coreCommand,

    //Parts
    partNozzle, partProcessor;

    public static void load() {
        corePod = new RootCore("core-pod"){{
            requirements(Category.effect, with(TItems.tCopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250));

            envEnabled |= Env.underwater;
            alwaysUnlocked = true;
            armor = 15;
            incinerateNonBuildable = false;
            isFirstTier = true;
            unitType = TUnitTypes.trident;
            health = 1000;
            itemCapacity = 500;
            size = 2;
            unitCapModifier = 5;
        }};

        coreBranch = new CoreExpansion("core-branch"){{
            requirements(Category.effect, with(TItems.tCopper, 100, TItems.calcite, 50)); //Placeholder build cost

            envEnabled |= Env.underwater;
            health = 200;
            size = 2;
            bundleMoveSpeed = 3f;
        }};

        coreInput = new CoreInput("core-input"){{
            requirements(Category.effect, with(TItems.tCopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250)); //Placeholder build cost

            envEnabled |= Env.underwater;
            health = 650;
            size = 2;
            itemCapacity = 50;
            acceptsItems = true;
            unloadable = true;
            linkAdjacent = false;
        }};

        coreCommand = new CoreExpansion("core-command"){{
            requirements(Category.effect, with(TItems.tCopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250)); //Placeholder build cost

            envEnabled |= Env.underwater;
            health = 650;
            size = 2;
            linkedUnitCapModifier = 5;
            linkAdjacent = false;
        }};

        partNozzle = new Wall("part-nozzle"){{
            requirements(Category.effect, with(TItems.nickel, 4, TItems.tCopper, 2));

            envEnabled |= Env.underwater;
            scaledHealth = 65f;
            armor = 2f;
            size = 1;
        }};

        partProcessor = new Wall("part-processor"){{
            requirements(Category.effect, with(TItems.nickel, 5, TItems.cobalt, 5));

            envEnabled |= Env.underwater;
            scaledHealth = 80f;
            armor = 2f;
            size = 1;
        }};
    }
}
