package tantros.content;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class TantrosBlocks {
    public static Block

    // region Misc

    //cores
    corePod,
    // endregion

    public static void load(){


        // region Misc
        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(Items.copper, 1));

        }};


    }
}