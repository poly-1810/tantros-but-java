package poly.tantros.world.blocks.power;

import arc.Core;
import mindustry.world.blocks.power.BeamNode;

public class Node extends BeamNode {
    public Node(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        this.laser = Core.atlas.find("poly-tantros-power-bridge");
        this.laserEnd = Core.atlas.find("poly-tantros-power-bridge-end");
    }
}
