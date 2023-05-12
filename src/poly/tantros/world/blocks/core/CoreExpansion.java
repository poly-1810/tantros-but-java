package poly.tantros.world.blocks.core;

import arc.*;
import arc.struct.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.meta.*;
import mindustry.world.modules.*;
import poly.tantros.world.blocks.core.RootCore.*;

import static mindustry.Vars.*;

public class CoreExpansion extends Block{
    public boolean linkAdjacent = true;
    public int linkedUnitCapModifier = 0;

    public CoreExpansion(String name){
        super(name);
        hasItems = true;
        solid = true;
        update = false;
        destructible = true;
        acceptsItems = false;
        unloadable = false;
        separateItemCapacity = true;
        group = BlockGroup.transportation;
        flags = EnumSet.of(BlockFlag.storage);
        allowResupply = true;
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

    public class CoreExpansionBuild extends Building{
        public RootCoreBuild rootCore;
        public Building nextLink;

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
        public boolean acceptItem(Building source, Item item){
            return acceptsItems && rootCore != null && rootCore.acceptItem(source, item);
        }

        @Override
        public void handleItem(Building source, Item item){
            if(rootCore != null){
                if(rootCore.items.get(item) >= rootCore.storageCapacity){
                    StorageBlock.incinerateEffect(this, source);
                }
                rootCore.noEffect = true;
                rootCore.handleItem(source, item);
            }
        }

        @Override
        public void itemTaken(Item item){
            if(rootCore != null) rootCore.itemTaken(item);
        }

        @Override
        public int removeStack(Item item, int amount){
            int result = super.removeStack(item, amount);

            if(rootCore != null && team == state.rules.defaultTeam && state.isCampaign()){
                state.rules.sector.info.handleCoreItem(item, -result);
            }

            return result;
        }

        @Override
        public int getMaximumAccepted(Item item){
            return rootCore != null ? rootCore.getMaximumAccepted(item) : 0;
        }

        @Override
        public int explosionItemCap(){
            //when linked to a core, containers/vaults are made significantly less explosive.
            return rootCore != null ? Math.min(itemCapacity/60, 6) : itemCapacity;
        }

        @Override
        public void drawSelect(){
            if(rootCore != null) rootCore.drawSelect();
        }

        @Override
        public boolean canPickup(){
            return rootCore == null;
        }
    }
}
