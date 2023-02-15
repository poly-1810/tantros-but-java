package poly.tantros.content.Blocks;

import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.logic.LogicBlock;
import poly.tantros.content.TItems;

import static mindustry.type.ItemStack.with;

public class Logic {
    public static Block processor;

    public static void load() {
        processor = new LogicBlock("tantros-processor"){{
            requirements(Category.logic, with(TItems.nickel, 40, TItems.tCopper, 20, TItems.zinc, 20));

            envEnabled = 4;
            scaledHealth = 65f;
            size = 2;
        }};
    }
}
