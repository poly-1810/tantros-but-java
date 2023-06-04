package poly.tantros.world.blocks.core.expansions;

import arc.graphics.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import poly.tantros.world.blocks.core.RootCore.*;
import poly.tantros.world.modules.ItemBundleModule.*;

public class CoreStorage extends CoreExpansion{
    public Color storageLinkedColor = Pal.accent;

    public CoreStorage(String name){
        super(name);
        selectionColor = Pal.remove;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for(int i = 0; i < 4; i++){ //Rotation
            for(int j = 0; j < size; j++){
                nearbySide(tile.x, tile.y, i, j, Tmp.p1);
                Building build = Vars.world.build(Tmp.p1.x, Tmp.p1.y);
                if(build instanceof RootCoreBuild || build instanceof CoreStorageBuild c && c.rootCore != null) return true;
            }
        }
        return false;
    }

    public class CoreStorageBuild extends CoreExpansionBuild{
        public boolean storageLinked;

        @Override
        public void handleStack(Item item, int amount, Teamc source){
            if(rootCore == null || source != self()) return;

            rootCore.handleStack(item, amount, this);
        }

        @Override
        public Color selectionColor(){
            return storageLinked ? storageLinkedColor : selectionColor;
        }

        @Override
        public void bundleArrived(ItemBundle bundle){
            if(storageLinked){
                handleStack(bundle.stack.item, bundle.stack.amount, self());
            }else{
                bundle.destroyed();
            }
        }

        @Override
        public boolean arriveShrink(){
            return storageLinked;
        }
    }
}
