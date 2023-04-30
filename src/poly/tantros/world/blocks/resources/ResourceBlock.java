package poly.tantros.world.blocks.resources;

import mindustry.type.*;
import mindustry.world.blocks.defense.*;

public class ResourceBlock extends Wall{
    public boolean breakOnPlace = false;
    public boolean placeable = true;

    public Item item; // for easy access to item
    public int drillTime; // in seconds

    public ResourceBlock(String name){
        super(name);
    }

    @Override
    public boolean isPlaceable(){
        return super.isPlaceable() && placeable;
    }

    public class ResourceBlockBuild extends WallBuild{
        @Override
        public void placed(){
            super.placed();
            if(!breakOnPlace) return;
            kill();
        }
    }
}
