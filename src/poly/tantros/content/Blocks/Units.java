package poly.tantros.content.Blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.units.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;

import static mindustry.type.ItemStack.*;

public class Units {
    public static Block quadAssembler, submarineAssembler;

    public static void load() {
        quadAssembler = new UnitAssembler("quad-assembler"){{
            requirements(Category.units, with(TItems.cobalt, 50, TItems.nickel, 70, TItems.tCopper, 60));

            envEnabled = Env.terrestrial | Env.underwater;
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
        submarineAssembler = new UnitAssembler("submarine-assembler"){{
            requirements(Category.units, with(TItems.cobalt, 40, TItems.nickel, 80, TItems.tCopper, 80));

            envEnabled = Env.terrestrial | Env.underwater;
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
