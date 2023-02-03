package tantros.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class TItems {
    public static final Seq<Item> tantrosItems = new Seq<>();
    public static Item brass, calcite, carbon, cobalt, nickel, rubidium, sheetMetal, tantrosCopper, zinc;

    public static void load() {
        brass = new Item("brass", Color.valueOf("edc687"));
        calcite = new Item("calcite", Color.valueOf("ffffff"));
        carbon = new Item("carbon", Color.valueOf("404040"));
        cobalt = new Item("cobalt", Color.valueOf("d1efff"));
        nickel = new Item("nickel", Color.valueOf("92a4a8"));
        rubidium = new Item("rubidium", Color.valueOf("ce735e"));
        sheetMetal = new Item("sheet-metal", Color.valueOf("e0b28d"));
        tantrosCopper = new Item("tantros-copper", Color.valueOf("5edac2"));
        zinc = new Item("zinc", Color.valueOf("ededed"));

        tantrosItems.addAll(brass, calcite, carbon, cobalt, rubidium, sheetMetal, tantrosCopper, zinc);
    }
}
