package poly.tantros.content.Blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;

import static mindustry.type.ItemStack.*;

public class Logic {
    public static Block processor;

    public static void load() {
        processor = new LogicBlock("tantros-processor"){{
            requirements(Category.logic, with(TItems.nickel, 40, TItems.tCopper, 20, TItems.zinc, 20));

            envEnabled |= Env.underwater;
            scaledHealth = 65f;
            size = 2;
        }};
    }
}
