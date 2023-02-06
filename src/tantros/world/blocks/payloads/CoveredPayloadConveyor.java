package tantros.world.blocks.payloads;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*;
import mindustry.world.blocks.payloads.*;

public class CoveredPayloadConveyor extends PayloadConveyor{
    public TextureRegion coverRegion;

    public CoveredPayloadConveyor(String name){
        super(name);
        size = 2;
    }

    @Override
    public void load(){
        super.load();

        coverRegion = Core.atlas.find(name + "-cover");
    }

    public class CoveredPayloadConveyorBuild extends PayloadConveyorBuild{
        @Override
        public void draw(){
            super.draw();
            Draw.z(Layer.blockOver + 0.01f);
            Draw.rect(coverRegion, x, y);
        }
    }
}
