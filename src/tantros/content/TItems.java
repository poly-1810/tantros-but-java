package tantros.content;

import arc.graphics.Color;
import mindustry.type.Item;

public class TItems {
    public static Item
    Tcopper, cobalt, calcite, brass, carbon, nickel, rubedo, zinc, sheetMetal;

    public static void load() {
        cobalt = new Item("cobalt", Color.valueOf("8CA9E8")){{
            hardness = 3;
            cost = 1.8f;
            charge = 0.3f;
            healthScaling = 1.2f;
        }};
        Tcopper = new Item("copper", Color.valueOf("29A88B")){{
            hardness = 1;
            cost = 0.3f;
            healthScaling = 0.5f;
        }};
        calcite = new Item("calcite", Color.valueOf("D0D8E7")){{
            hardness = 1;
            cost = 1.5f;
            healthScaling = 0.7f;
        }};
        brass = new Item("brass", Color.valueOf("EDC687")){{
            cost = 1;
            healthScaling = 0.7f;
        }};
        carbon = new Item("carbon", Color.valueOf("404040")){{
            hardness = 3;
            cost = 0.3f;
            flammability = 0.7f;
        }};
        nickel = new Item("nickel", Color.valueOf("66787D")){{
            hardness = 2;
            cost = 0.8f;
            healthScaling = 0.8f;
        }};
        rubedo = new Item("rubedo", Color.valueOf("AF4753")){{
            hardness = 2;
            cost = 0.6f;
            explosiveness = 7.5f;
            flammability = 0.5f;
            healthScaling = 0.5f;
        }};
        zinc = new Item("zinc", Color.valueOf("9A9DBF")){{
            hardness = 2;
            cost = 0.7f;
            healthScaling = 0.7f;
        }};
        sheetMetal = new Item("sheet-metal", Color.valueOf("9B928B")){{
            hardness = 3;
            cost = 0.6f;
            healthScaling = 0.65f;
        }};
    }
}
