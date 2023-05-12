package poly.tantros.world.blocks.core;

import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;

public class CoreInput extends CoreExpansion{
    protected final int sendTimer = timers++;

    public int maxSend = 20;
    public float sendDelay = 60f;

    public CoreInput(String name){
        super(name);
        hasItems = true;
        acceptsItems = true;
    }

    public class CoreInputBuild extends CoreExpansionBuild{
        public int sendItem;

        @Override
        public void updateTile(){
            super.updateTile();

            if(rootCore != null && !items.empty() && timer(sendTimer, sendDelay / timeScale)){
                sendItem = items.nextIndex(sendItem);
                Item item = Vars.content.item(sendItem);
                int amount = Math.min(items.get(item), maxSend);
                ItemStack stack = new ItemStack(item, amount);
                items.remove(stack);

                itemBundles.addBundle(x, y, stack, path(false));
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < getMaximumAccepted(item);
        }
    }
}
