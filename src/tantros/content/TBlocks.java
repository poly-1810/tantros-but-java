package tantros.content;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.payloads.PayloadDeconstructor;
import mindustry.world.blocks.payloads.PayloadMassDriver;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import tantros.world.blocks.payloads.CoveredPayloadConveyor;
import tantros.world.blocks.payloads.CoveredPayloadRouter;

import static mindustry.type.ItemStack.with;

public class TBlocks {
    public static Block


    // misc
    corePod,
    //endregion

    // distribution
    payloadBelt, payloadDistributor, constructor, deconstructor, payloadDriver
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
        payloadBelt = new CoveredPayloadConveyor("payload-belt") {{
            requirements(Category.units, with(Items.copper, 1));
            moveTime = 25f;
            canOverdrive = false;
            health = 100;
            researchCostMultiplier = 2f;
            underBullets = true;
            size = 2;
        }};

        payloadDistributor = new CoveredPayloadRouter("payload-distributor") {{
            requirements(Category.units, with(Items.copper, 1));
            moveTime = 25f;
            canOverdrive = false;
            health = 100;
            researchCostMultiplier = 2f;
            underBullets = true;
            size = 2;
        }};

        constructor = new Constructor("constructor") {{
            requirements(Category.units, with(Items.copper, 1));
            regionSuffix = "-dark";
            hasPower = false;
            buildSpeed = 0.5f;
            maxBlockSize = 1;
            size = 2;
        }};

        deconstructor = new PayloadDeconstructor("deconstructor") {{
            requirements(Category.units, with(Items.copper, 1));
            regionSuffix = "-dark";
            hasPower = false;
            itemCapacity = 60;
            size = 2;
            maxPayloadSize = 1;
            deconstructSpeed = 2.8f;
        }};

        payloadDriver = new PayloadMassDriver("payload-launcher") {{
            requirements(Category.units, with(Items.copper,1));
            regionSuffix ="-dark";
            size = 2;
            reload = 4f;
            chargeTime = 20f;
            maxPayloadSize = 1f;
        }};



    }
}