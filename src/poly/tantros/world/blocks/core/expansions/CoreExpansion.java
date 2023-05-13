package poly.tantros.world.blocks.core.expansions;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import poly.tantros.world.blocks.core.*;
import poly.tantros.world.blocks.core.RootCore.*;
import poly.tantros.world.blocks.core.expansions.CoreStorage.*;
import poly.tantros.world.modules.*;
import poly.tantros.world.modules.ItemBundleModule.*;

import static mindustry.Vars.*;

public class CoreExpansion extends Block{
    public boolean linkAdjacent = true;
    public int linkedUnitCapModifier = 0;
    public float bundleMoveSpeed = 0.5f;
    public Color selectionColor = Color.lightGray;

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
        return other.replaceable && size >= other.size && other != this && other instanceof CoreExpansion;
    }

    protected TextureRegion[] icons(){
        TextureRegion r = variants > 0 ? Core.atlas.find(name + "1") : region;
        return teamRegion.found() ? new TextureRegion[]{r, teamRegions[Team.sharded.id]} : new TextureRegion[]{r};
    }

    public class CoreExpansionBuild extends Building implements ItemBundleMover{
        public RootCoreBuild rootCore;
        public Building nextLink;
        public boolean alreadyLinked;
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
            if(!alreadyLinked) team.data().unitCap += linkedUnitCapModifier;
            alreadyLinked = true;
        }

        public void unlinked(){
            Log.info(tile);

            rootCore = null;
            nextLink = null;
            alreadyLinked = false;
            if(linkedUnitCapModifier > 0){
                team.data().unitCap -= linkedUnitCapModifier;
                if(!state.rules.editor){
                    int cap = team.data().unitCap;
                    for(UnitType type : content.units()){
                        if(!type.useUnitCap) continue;

                        Seq<Unit> units = team.data().getUnits(type);
                        if(units != null){
                            Seq<Unit> cull = units.select(u -> !u.spawnedByCore && !u.dead);
                            if(cull.size <= cap) continue;

                            int killed = 0;
                            for(int i = cull.size - 1; i >= cap; i--){
                                Call.unitCapDeath(cull.get(i));
                                units.remove(cull.get(i));
                                killed++;
                            }
                            team.data().updateCount(type, -killed);
                        }
                    }
                }
            }
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

                if(cur instanceof CoreStorageBuild s && s.storageLinked){
                    cur = null; //Reached a storage
                }else if(cur instanceof CoreExpansionBuild e){
                    cur = e.nextLink; //Continue
                }else{
                    cur = null; //Reached core
                }
            }
            return path;
        }

        public Color selectionColor(){
            return selectionColor;
        }

        @Override
        public Building building(){
            return self();
        }

        @Override
        public ItemBundleModule itemBundleModule(){
            return itemBundles;
        }

        @Override
        public void bundleArrived(ItemBundle bundle){
            bundle.destroyed();
        }

        @Override
        public void onRemoved(){
            unlinked();
            itemBundles.clear();
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
