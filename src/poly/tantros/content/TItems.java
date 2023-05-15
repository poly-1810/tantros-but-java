package poly.tantros.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.type.*;

public class TItems{
    public static Item cobalt, calcite, brass, carbon, nickel, rubedo, sheetMetal, zinc, tCopper;

    public static final Seq<Item> tantrosItems = new Seq<>();
    public static final Seq<Item> onlyTantrosItems = new Seq<>();

    public static void load(){
        cobalt = new Item("cobalt", Color.valueOf("8ca9e8")){{
            hardness = 3;
            cost = 1.8f;
            charge = 0.3f;
            healthScaling = 1.2f;
        }};
        calcite = new Item("calcite", Color.valueOf("d0d8e7")){{
            hardness = 1;
            cost = 1.5f;
            healthScaling = 0.7f;
        }};
        brass = new Item("brass", Color.valueOf("edc687")){{
            cost = 1;
            healthScaling = 0.7f;
        }};
        carbon = new Item("carbon", Color.valueOf("404040")){{
            hardness = 3;
            cost = 0.3f;
            flammability = 0.7f;
        }};
        nickel = new Item("nickel", Color.valueOf("66787d")){{
            hardness = 2;
            cost = 0.8f;
            healthScaling = 0.8f;
        }};
        rubedo = new Item("rubedo", Color.valueOf("af4753")){{
            hardness = 2;
            cost = 0.6f;
            explosiveness = 7.5f;
            flammability = 0.5f;
            healthScaling = 0.5f;
        }};
        sheetMetal = new Item("sheet-metal", Color.valueOf("9b928b")){{
            hardness = 3;
            cost = 0.6f;
            healthScaling = 0.65f;
        }};
        tCopper = new Item("tcopper", Color.valueOf("29a88b")){{
            hardness = 1;
            cost = 0.3f;
            healthScaling = 0.5f;
        }};
        zinc = new Item("zinc", Color.valueOf("9a9dbf")){{
            hardness = 2;
            cost = 0.7f;
            healthScaling = 0.7f;
        }};

        tantrosItems.addAll(brass, calcite, carbon, cobalt, Items.graphite, rubedo, Items.sand, sheetMetal, tCopper, zinc);
        onlyTantrosItems.addAll(brass, calcite, carbon, cobalt, rubedo, sheetMetal, tCopper, zinc);
    }
}
