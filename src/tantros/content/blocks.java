package tantros.content;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class blocks {
    public static Block

    // region Misc

    //storage
    corePod;
    // endregion

    public static void load(){
        //region storage

        corePod = new CoreBlock("core-pod"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(Items.copper, 1));

        }};


    }
}