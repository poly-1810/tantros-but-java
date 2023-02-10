package poly.tantros.world.blocks.payloads;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Layer;
import mindustry.world.blocks.payloads.PayloadRouter;

public class CoveredPayloadRouter extends PayloadRouter{
    public TextureRegion coverRegion;

    public CoveredPayloadRouter(String name){
        super(name);
        size = 2;
    }

    @Override
    public void load(){
        super.load();

        coverRegion = Core.atlas.find(name + "-cover");
    }

    public class CoveredPayloadRouterBuild extends PayloadRouterBuild{
        @Override
        public void draw(){
            super.draw();
            Draw.z(Layer.blockOver + 0.01f);
            Draw.rect(coverRegion, x, y);
        }
    }
}
