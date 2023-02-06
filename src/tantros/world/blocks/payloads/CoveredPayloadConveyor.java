package tantros.world.blocks.payloads;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import mindustry.entities.TargetPriority;
import mindustry.world.blocks.payloads.PayloadConveyor;
import mindustry.world.meta.*;


public class CoveredPayloadConveyor extends PayloadConveyor {
    public float moveTime = 25f, moveForce = 201f;
    public TextureRegion topRegion;
    public TextureRegion edgeRegion;
    public TextureRegion coverRegion;

    @Override
    public void load() {
        topRegion = Core.atlas.find(name + "-top");
        edgeRegion = Core.atlas.find(name + "-edge");
        coverRegion = Core.atlas.find(name + "-cover");
    }

    public Interp interp = Interp.pow5;
    public float payloadLimit = 2f;

    public CoveredPayloadConveyor(String name) {
        super(name);
        group = BlockGroup.transportation;
        size = 2;
        rotate = true;
        update = true;
        outputsPayload = true;
        noUpdateDisabled = true;
        priority = TargetPriority.transport;
        envEnabled |= Env.underwater;
        sync = true;
    }
}