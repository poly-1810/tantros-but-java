package poly.tantros.world.blocks.resources;

import mindustry.*;
import mindustry.world.blocks.defense.*;

public class ResourceBlock extends Wall {
    public boolean breakOnPlace = false;
    public boolean placeable = true;

    public ResourceBlock(String name) {
        super(name);
    }

    @Override
    public boolean isPlaceable() {
        super.isPlaceable();
        return placeable;
    }

    public class ResourceBlockBuild extends Wall.WallBuild {
        @Override
        public void placed() {
            super.placed();
            if (!breakOnPlace) return;
            kill();
        }
    }
}
