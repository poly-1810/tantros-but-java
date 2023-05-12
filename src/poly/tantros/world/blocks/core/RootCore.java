package poly.tantros.world.blocks.core;

import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.*;
import poly.tantros.world.blocks.core.expansions.*;
import poly.tantros.world.blocks.core.expansions.CoreExpansion.*;
import poly.tantros.world.blocks.core.expansions.CoreStorage.*;
import poly.tantros.world.modules.*;

import static mindustry.Vars.*;

public class RootCore extends CoreBlock{
    protected final static Queue<Building> expansionQueue = new Queue<>();
    protected static int itemCount = 0;

    public float bundleMoveSpeed = 0.5f;

    public RootCore(String name){
        super(name);
    }

    public class RootCoreBuild extends CoreBuild implements ItemBundleMover{
        public Seq<CoreExpansionBuild> chained = new Seq<>();
        public ItemBundleModule itemBundles = new ItemBundleModule();

        @Override
        public void updateTile(){
            super.updateTile();
            itemBundles.update(self(), bundleMoveSpeed * timeScale);
        }

        @Override
        public void draw(){
            super.draw();

            Draw.z(Layer.blockOver);
            itemBundles.draw();
        }

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

                for(Building b : next.proximity){
                    if(b instanceof CoreExpansionBuild e && e.rootCore != self()){
                        e.rootCore = self();
                        e.nextLink = next;
                        e.linked();
                        if(e instanceof CoreStorageBuild) storageCapacity += e.block.itemCapacity;
                        expansionQueue.addFirst(e);
                    }
                }
            }
            pre.each(CoreExpansionBuild::unlinked);

            if(!world.isGenerating()){
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

        @Override
        public ItemBundleModule itemBundleModule(){
            return itemBundles;
        }

        public int itemCount(Item item){
            itemCount = items.get(item);
            for(CoreExpansionBuild build : chained){
                if(build.block.hasItems) itemCount += build.items.get(item);
                build.itemBundles.getItemBundles().each(b -> b.stack.item == item, b -> itemCount += b.stack.amount);
            }
            return itemCount;
        }

        @Override
        public void write(Writes write){
            super.write(write);

            itemBundles.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 1) itemBundles.read(read);
        }

        @Override
        public byte version(){
            return 1;
        }
    }
}
