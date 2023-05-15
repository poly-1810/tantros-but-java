package poly.tantros.world.meta;

import arc.*;
import arc.graphics.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import poly.tantros.world.blocks.resources.*;

public class TStatValues{
    public static StatValue blockList(Seq<ResourceBlock> blocks){
        return table -> {
            table.row();
            table.table(c -> {
                int i = 0;
                for(ResourceBlock block : blocks){
                    c.table(Styles.grayPanel, b -> {
                        b.image(block.uiIcon).scaling(Scaling.fit).size(32).pad(14).left();
                        b.table(info -> {
                            info.left();
                            info.add(block.localizedName).left().row();
                            info.row();
                            info.add(block.drillTime + " " + Core.bundle.get("unit.seconds")).color(Color.lightGray).left().row();
                        }).grow();
                    }).growX().pad(5);
                    if(++i % 2 == 0) c.row();
                }
            }).growX().colspan(table.getColumns());
        };
    }
}
