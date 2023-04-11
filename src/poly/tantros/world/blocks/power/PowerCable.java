package poly.tantros.world.blocks.power;

import arc.Core;
import mindustry.world.blocks.power.PowerNode;

public class PowerCable extends PowerNode {
    public String name;

    public PowerCable(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void load() {
        super.load();
        this.laser = Core.atlas.find("poly-tantros-" + name + "-laser");
        this.laserEnd = Core.atlas.find("poly-tantros-" + name + "-laser-end");
    }
}
