package poly.tantros.world.blocks.power;

import arc.Core;
import mindustry.world.blocks.power.PowerNode;

public class LongNode extends PowerNode {
    public LongNode(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        this.laser = Core.atlas.find("poly-tantros-power-wire");
        this.laserEnd = Core.atlas.find("poly-tantros-power-wire-end");
    }
}
