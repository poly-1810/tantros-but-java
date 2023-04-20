package poly.tantros.tools;

import arc.struct.*;
import arc.util.Log;
import mindustry.content.Items;
import mindustry.type.*;

public class CheckSeq {
    public static Seq<Item> check(Seq<Item> list) {
        Seq<Item> allItems = new Seq<>();

        allItems.addAll(Items.serpuloItems);
        allItems.addAll(Items.erekirItems);

        Seq<Item> finalList = new Seq<>();

        for (Item item : allItems) {
            if (!list.contains(item)) {
                finalList.add(item);
            }
        }

        return finalList;
    }
}
