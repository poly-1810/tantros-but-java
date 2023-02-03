package tantros.content;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block

    // region Misc
    // environment
    oreCobalt,
    oreNickel,
    oreTantrosCopper,
    oreZinc,
    // crafting
    brassSmelter,
    // storage
    corePod;

    // endregion

    public static void load() {
        // region environment
        oreCobalt = new OreBlock("ore-cobalt", TItems.cobalt){{
           variants = 3;
        }};
        oreNickel = new OreBlock("ore-nickel", TItems.nickel){{
            variants = 5;
        }};
        oreTantrosCopper = new OreBlock("ore-tantros-copper", TItems.tantrosCopper){{
            variants = 3;
        }};
        oreZinc = new OreBlock("ore-zinc", TItems.zinc){{
            variants = 5;
        }};
        // region crafting
        brassSmelter = new HeatCrafter("brass-smelter"){{
            requirements(Category.crafting, with(TItems.tantrosCopper, 50, TItems.zinc, 30));

            outputItem = new ItemStack(TItems.brass, 1);
            craftEffect = Fx.smeltsmoke;
            size = 2;
            craftTime = 40f;
            heatRequirement = 5f;
            maxEfficiency = 2f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawArcSmelt(),
                    new DrawDefault(),
                    new DrawGlowRegion("-glow"),
                    new DrawGlowRegion("-heat-glow"),
                    new DrawHeatInput("-heat")
            );
            consumeItems(with(TItems.tantrosCopper, 2, TItems.zinc, 1));
        }};
        // region storage
        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, with(Items.copper, 1));

            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = UnitTypes.alpha;
            health = 1000;
            itemCapacity = 500;
            size = 2;
            unitCapModifier = 5;
        }};
    }
}