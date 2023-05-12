package poly.tantros.world.blocks.core;

import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.*;
import poly.tantros.world.blocks.core.CoreExpansion.*;

import static mindustry.Vars.*;

public class RootCore extends CoreBlock{
    protected final static Queue<Building> expansionQueue = new Queue<>();

    public RootCore(String name){
        super(name);
    }

    public class RootCoreBuild extends CoreBuild{
        public Seq<CoreExpansionBuild> chained = new Seq<>();

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            updateChained();
        }

        /** Note: Does not properly calculate storage capacity if multiple cores exist on the map. */
        public void updateChained(){
            chained.each(c -> c.rootCore = null);
            storageCapacity = itemCapacity;
            Seq<CoreExpansionBuild> pre = chained;
            chained = new Seq<>();
            expansionQueue.clear();
            expansionQueue.add(this);

            while(!expansionQueue.isEmpty()){
                Building next = expansionQueue.removeLast();
                if(next instanceof CoreExpansionBuild e){
                    chained.add(e);
                    pre.remove(e, true);

                    if(!((CoreExpansion)e.block).linkAdjacent) continue;
                }

                for(var b : next.proximity){
                    if(b instanceof CoreExpansionBuild e && e.rootCore != self()){
                        e.rootCore = self();
                        e.nextLink = b;
                        if(e.block.hasItems){
                            if(e.items != items){
                                items.add(e.items);
                                e.items.clear();
                            }
                            e.items = items;
                            storageCapacity += e.block.itemCapacity;
                        }
                        e.linked();
                        expansionQueue.addFirst(e);
                    }
                }
            }
            pre.each(CoreExpansionBuild::unlinked);

            if(!world.isGenerating()){
                for(int i = 0; i < pre.size; i++){
                    CoreExpansionBuild b = pre.get(i);
                    if(b.items != null && b.block.itemCapacity > 0){
                        boolean empty = true;
                        for(Item item : content.items()){
                            int amount = Math.min(items.get(item) - storageCapacity, b.block.itemCapacity);
                            if(amount > 0){
                                b.items.add(item, amount);
                                items.remove(item, amount);
                            }
                            if(items.get(item) > storageCapacity) empty = false;
                        }
                        if(empty) break;
                    }
                }

                for(Item item : content.items()){
                    items.set(item, Math.min(items.get(item), storageCapacity));
                }
            }
        }

        @Override
        public void drawSelect(){
            //do not draw a pointless single outline when there's no storage
            if(team.cores().size <= 1 && chained.isEmpty()) return;

            Lines.stroke(1f, Pal.accent);
            Cons<Building> outline = b -> {
                for(int i = 0; i < 4; i++){
                    Point2 p = Geometry.d8edge[i];
                    float offset = -Math.max(b.block.size - 1, 0) / 2f * tilesize;
                    Draw.rect("block-select", b.x + offset * p.x, b.y + offset * p.y, i * 90);
                }
            };

            outline.get(self());
            chained.each(outline);
            Draw.reset();
        }
    }
}
