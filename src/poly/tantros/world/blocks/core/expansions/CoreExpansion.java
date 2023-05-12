package poly.tantros.world.blocks.core.expansions;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.modules.*;
import poly.tantros.world.blocks.core.*;
import poly.tantros.world.blocks.core.RootCore.*;
import poly.tantros.world.blocks.core.expansions.CoreStorage.*;
import poly.tantros.world.modules.*;

import static mindustry.Vars.*;

public class CoreExpansion extends Block{
    public boolean linkAdjacent = true;
    public int linkedUnitCapModifier = 0;
    public float bundleMoveSpeed = 0.5f;

    public CoreExpansion(String name){
        super(name);
        itemCapacity = 0;
        solid = true;
        update = true;
        destructible = true;
        acceptsItems = false;
        unloadable = false;
        separateItemCapacity = true;
        group = BlockGroup.transportation;
        flags = EnumSet.of(BlockFlag.core, BlockFlag.storage);
        envEnabled = Env.any;
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("capacity", (CoreExpansionBuild e) -> new Bar(
            () -> e.rootCore == null ? Core.bundle.get("bar.pt-expansion-unlinked") : Core.bundle.format("bar.capacity", UI.formatAmount(e.rootCore.storageCapacity)),
            () -> Pal.items,
            () -> e.rootCore == null ? 0 : (e.rootCore.items.total() / ((float)e.rootCore.storageCapacity * content.items().count(UnlockableContent::unlockedNow)))
        ));
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    @Override
    public boolean canReplace(Block other){
        if(other.alwaysReplace) return true;
        if(other.privileged) return false;
        return other.replaceable && size >= other.size && other instanceof CoreExpansion;
    }

    public class CoreExpansionBuild extends Building implements ItemBundleMover{
        public RootCoreBuild rootCore;
        public Building nextLink;
        public ItemBundleModule itemBundles = new ItemBundleModule();

        @Override
        public void updateTile(){
            itemBundles.update(self(), bundleMoveSpeed * timeScale);
        }

        @Override
        public void draw(){
            super.draw();

            Draw.z(Layer.blockOver);
            itemBundles.draw();
        }

        public void linked(){
            team.data().unitCap += linkedUnitCapModifier;
        }

        public void unlinked(){
            rootCore = null;
            nextLink = null;
            if(hasItems) items = new ItemModule();
            team.data().unitCap -= linkedUnitCapModifier;
        }

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();
            if(rootCore != null) rootCore.updateChained();
        }

        @Override
        public void drawSelect(){
            if(rootCore != null) rootCore.drawSelect();
        }

        @Override
        public boolean canPickup(){
            return rootCore == null;
        }

        public IntQueue path(boolean request){
            Building cur = nextLink;
            IntQueue path = new IntQueue();
            while(cur != null){
                if(request){
                    path.addLast(cur.pos());
                }else{
                    path.addFirst(cur.pos());
                }

                if(cur instanceof CoreStorageBuild){
                    cur = null;
                }else if(cur instanceof CoreExpansionBuild e){
                    cur = e.nextLink;
                }else{
                    cur = null; //Reached core
                }
            }
            return path;
        }

        @Override
        public ItemBundleModule itemBundleModule(){
            return itemBundles;
        }

        @Override
        public void write(Writes write){
            super.write(write);

            itemBundles.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            itemBundles.read(read);
        }
    }
}
