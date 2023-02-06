package tantros.content;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.blocks.payloads.PayloadRouter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block


    // misc
    corePod,
    //endregion

    // distribution
    payloadBelt, payloadRouter
    ;

    public static void load() {
        //region misc

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

        //endregion
        //region distribution
        payloadBelt = new PayloadConveyor("payload-belt") {{
            requirements(Category.units, with(Items.copper, 1));
            moveTime = 25f;
            canOverdrive = false;
            health = 100;
            researchCostMultiplier = 2f;
            underBullets = true;
            size = 2;
        }};

        payloadRouter = new PayloadRouter("payload-router") {{
            requirements(Category.units, with(Items.copper, 1));
            moveTime = 25f;
            canOverdrive = false;
            health = 100;
            researchCostMultiplier = 2f;
            underBullets = true;
            size = 2;
        }};



    }
}