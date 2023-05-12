package poly.tantros.world.blocks.core.expansions;

import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.*;
import poly.tantros.world.blocks.core.RootCore.*;

public class CoreStorage extends CoreExpansion{
    public CoreStorage(String name){
        super(name);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < size; j++){
                nearbySide(tile.x, tile.y, i, j, Tmp.p1);
                Building build = Vars.world.build(Tmp.p1.pack());
                if(build instanceof RootCoreBuild || build instanceof CoreStorageBuild c && c.rootCore != null) return true;
            }
        }
        return false;
    }

    public class CoreStorageBuild extends CoreExpansionBuild{
    }
}
