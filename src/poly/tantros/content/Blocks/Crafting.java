package poly.tantros.content.Blocks;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import poly.tantros.content.*;

import static mindustry.type.ItemStack.*;

public class Crafting{
    public static Block brassSmelter, electricHeater, heatDistributor;

    public static void load(){
        brassSmelter = new HeatCrafter("brass-smelter"){{
            requirements(Category.crafting, with(TItems.tCopper, 30, TItems.zinc, 20, TItems.calcite, 20));

            envEnabled = 2;
            scaledHealth = 90f;
            size = 2;
            craftTime = 90f;
            heatRequirement = 6f;
            maxEfficiency = 3f;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawGlowRegion("-heat-glow"){{
                    color = Color.valueOf("ffd37f");
                    glowScale = 8f;
                    alpha = 0.6f;
                }},
                new DrawBubbles(){{
                    color = Color.valueOf("ffd37f99");
                    amount = 4;
                    sides = 6;
                    spread = 0f;
                    timeScl = 50f;
                    recurrence = 8f;
                    radius = 4f;
                }},
                new DrawDefault(),
                new DrawGlowRegion(){{
                    color = Color.valueOf("ffd37f");
                    glowScale = 20f;
                    alpha = 0.5f;
                }},
                new DrawHeatInput()
            );

            consumeItems(with(TItems.tCopper, 3, TItems.zinc, 2));

            outputItem = new ItemStack(TItems.brass, 3);
        }};
        electricHeater = new HeatProducer("tantros-electric-heater"){{
            requirements(Category.crafting, with(TItems.tCopper, 40, TItems.nickel, 30, TItems.zinc, 15, TItems.calcite, 15));

            envEnabled = 2;
            scaledHealth = 90f;
            size = 2;
            heatOutput = 2f;
            warmupRate = 0.015f;

            consumePower(0.25f);

            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawHeatOutput()
            );
        }};
        heatDistributor = new HeatConductor("tantros-heat-distributor"){{
            requirements(Category.crafting, with(TItems.tCopper, 10, TItems.nickel, 10, TItems.zinc, 5, TItems.calcite, 5));

            envEnabled = 2;
            scaledHealth = 90f;
            size = 1;
            visualMaxHeat = 5f;
            drawer = new DrawMulti(
                new DrawDefault(),
                new DrawHeatOutput(),
                new DrawHeatInput()
            );
        }};
    }
}
