package poly.tantros.type;

import arc.graphics.*;
import mindustry.type.*;
import poly.tantros.world.blocks.resources.*;

public class ResourceItem extends Item {
    public ResourceBlock block; // for easy access to block

    public ResourceItem(String name, Color color) {
        super(name, color);
    }
}
