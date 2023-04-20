package poly.tantros.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.type.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.type.*;

public class TItems {
    public static ResourceItem cobalt, calcite, brass, carbon, nickel, rubedo, sheetMetal, zinc, tCopper;

    public static final Seq<Item> tantrosItems = new Seq<>();

    public static void load() {
        cobalt = new ResourceItem("cobalt", Color.valueOf("8ca9e8")){{
            hardness = 3;
            cost = 1.8f;
            charge = 0.3f;
            healthScaling = 1.2f;
            block = Resources.cobaltBlock;
        }};
        calcite = new ResourceItem("calcite", Color.valueOf("d0d8e7")){{
            hardness = 1;
            cost = 1.5f;
            healthScaling = 0.7f;
            // block = Resources.calciteBlock;
        }};
        brass = new ResourceItem("brass", Color.valueOf("edc687")){{
            cost = 1;
            healthScaling = 0.7f;
            block = Resources.brassBlock;
        }};
        carbon = new ResourceItem("carbon", Color.valueOf("404040")){{
            hardness = 3;
            cost = 0.3f;
            flammability = 0.7f;
            // block = Resources.carbonBlock;
        }};
        nickel = new ResourceItem("nickel", Color.valueOf("66787d")){{
            hardness = 2;
            cost = 0.8f;
            healthScaling = 0.8f;
            block = Resources.nickelBlock;
        }};
        rubedo = new ResourceItem("rubedo", Color.valueOf("af4753")){{
            hardness = 2;
            cost = 0.6f;
            explosiveness = 7.5f;
            flammability = 0.5f;
            healthScaling = 0.5f;
            block = Resources.rubedoBlock;
        }};
        sheetMetal = new ResourceItem("sheet-metal", Color.valueOf("9b928b")){{
            hardness = 3;
            cost = 0.6f;
            healthScaling = 0.65f;
            // block = Resources.sheetMetalBlock;
        }};
        tCopper = new ResourceItem("tcopper", Color.valueOf("29a88b")){{
            hardness = 1;
            cost = 0.3f;
            healthScaling = 0.5f;
            block = Resources.tCopperBlock;
        }};
        zinc = new ResourceItem("zinc", Color.valueOf("9a9dbf")){{
            hardness = 2;
            cost = 0.7f;
            healthScaling = 0.7f;
            // block = Resources.zincBlock;
        }};

        tantrosItems.addAll(brass, calcite, carbon, cobalt, Items.graphite, rubedo, Items.sand, sheetMetal, tCopper, zinc);
    }
}
