package tantros.content;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block

    // region Misc

    // storage
    corePod;
    // endregion

    public static void load() {
        //region storage

        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(Items.copper, 1));
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