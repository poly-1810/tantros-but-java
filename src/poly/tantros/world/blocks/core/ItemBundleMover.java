package poly.tantros.world.blocks.core;

import mindustry.gen.*;
import poly.tantros.world.modules.*;
import poly.tantros.world.modules.ItemBundleModule.*;

public interface ItemBundleMover{
    void bundleArrived(ItemBundle bundle);
    Building building();
    ItemBundleModule itemBundleModule();

    default boolean arriveShrink(){
        return false;
    }
}
