package poly.tantros.content.Blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.*;
import poly.tantros.content.TItems;
import poly.tantros.content.TUnitTypes;

import static mindustry.type.ItemStack.with;

public class Effect {
    public static Block corePod, partNozzle, partProcessor;

    public static void load() {
        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, with(TItems.tCopper, 400, TItems.calcite, 100, TItems.nickel, 250, TItems.zinc, 250));

            envEnabled = Env.terrestrial | Env.underwater;
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
        partNozzle = new Wall("part-nozzle"){{
            requirements(Category.effect, with(TItems.nickel, 4, TItems.tCopper, 2));

            envEnabled = Env.terrestrial | Env.underwater;
            scaledHealth = 65f;
            armor = 2f;
            size = 1;
        }};
        partProcessor = new Wall("part-processor"){{
            requirements(Category.effect, with(TItems.nickel, 5, TItems.cobalt, 5));

            envEnabled = Env.terrestrial | Env.underwater;
            scaledHealth = 80f;
            armor = 2f;
            size = 1;
        }};
    }
}
