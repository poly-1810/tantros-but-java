package poly.tantros.content.Blocks;

import mindustry.type.Category;
import mindustry.type.PayloadStack;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitAssembler;
import poly.tantros.content.TItems;
import poly.tantros.content.TUnitTypes;

import static mindustry.type.ItemStack.with;

public class Units {
    public static Block submarineConstructor;

    public static void load() {
        submarineConstructor = new UnitAssembler("submarine-constructor"){{
            requirements(Category.units, with(TItems.cobalt, 40, TItems.nickel, 80, TItems.tCopper, 80));

            envEnabled = 4;
            size = 4;
            configurable = false;
            areaSize = 4;
            dronesCreated = 4;
            droneConstructTime = 120f;
            fogRadius = 4;
            droneType = TUnitTypes.assemblySub;
            plans.add(
                    new AssemblerUnitPlan(TUnitTypes.requiem, 600f, PayloadStack.list(Resources.nickelBlock, 3))
            );

            consumePower(2.5f);
        }};
    }
}
