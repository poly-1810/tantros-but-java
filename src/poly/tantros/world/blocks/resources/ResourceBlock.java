package poly.tantros.world.blocks.resources;

import mindustry.Vars;
import mindustry.world.blocks.defense.Wall;

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
            if (Vars.net.client()) return;
            kill();
        }
    }
}
