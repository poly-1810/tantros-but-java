package poly.tantros.world.blocks.core;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.storage.*;

public class CoreStorage extends CoreExpansion{
    public int maxSend = 10;

    public CoreStorage(String name){
        super(name);
        hasItems = true;
    }

    public class CoreStorageBuild extends CoreExpansionBuild{
        @Override
        public void updateTile(){
            super.updateTile();

            if(rootCore != null && !items.empty() && timer(timerDump, dumpTime / timeScale)){
                Item item = items.first();
                int amount = Math.min(items.get(item), maxSend);
                ItemStack stack = new ItemStack(item, amount);
                items.remove(stack);

                itemBundles.addBundle(x, y, stack, path(false));
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return rootCore != null && rootCore.itemCount(item) < rootCore.storageCapacity;
        }

        @Override
        public void handleItem(Building source, Item item){
            if(rootCore != null && rootCore.itemCount(item) >= rootCore.storageCapacity){
                StorageBlock.incinerateEffect(self(), source);
                return;
            }
            super.handleItem(source, item);
        }

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            if(acceptItem(self(), item) && block.hasItems && (source == null || source.team() == team)){
                return Math.min(getMaximumAccepted(item) - rootCore.itemCount(item), amount);
            }else{
                return 0;
            }
        }

        @Override
        public int getMaximumAccepted(Item item){
            return rootCore != null ? rootCore.storageCapacity : 0;
        }
    }
}
