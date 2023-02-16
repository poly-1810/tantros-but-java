package poly.tantros.content.Blocks;

import mindustry.type.Category;
import mindustry.type.PayloadStack;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitAssembler;
import poly.tantros.content.TItems;
import poly.tantros.content.TUnitTypes;

import static mindustry.type.ItemStack.with;

public class Units {
    public static Block quadConstructor, submarineConstructor;

    public static void load() {
        quadConstructor = new UnitAssembler("quad-constructor"){{
            requirements(Category.units, with(TItems.cobalt, 50, TItems.nickel, 70, TItems.tCopper, 60));

            envEnabled = 4;
            size = 4;
            configurable = false;
            areaSize = 6;
            dronesCreated = 4;
            droneConstructTime = 120f;
            fogRadius = 4;
            droneType = TUnitTypes.assemblySub;
            plans.add(
                    new AssemblerUnitPlan(TUnitTypes.chasm, 8f * 60f, PayloadStack.list(Resources.tCopperBlock, 2, Effect.partNozzle, 3, Effect.partProcessor, 2))
            );

            consumePower(2.5f);
        }};
        submarineConstructor = new UnitAssembler("submarine-constructor"){{
            requirements(Category.units, with(TItems.cobalt, 40, TItems.nickel, 80, TItems.tCopper, 80));

            envEnabled = 4;
            size = 4;
            configurable = false;
            areaSize = 6;
            dronesCreated = 4;
            droneConstructTime = 120f;
            fogRadius = 4;
            droneType = TUnitTypes.assemblySub;
            plans.add(
                    new AssemblerUnitPlan(TUnitTypes.requiem, 10f * 60f, PayloadStack.list(Resources.nickelBlock, 3, Effect.partNozzle, 4, Effect.partProcessor, 2))
            );

            consumePower(2.5f);
        }};
    }
}
