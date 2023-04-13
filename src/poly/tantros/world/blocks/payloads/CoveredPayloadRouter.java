package poly.tantros.world.blocks.payloads;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.payloads.PayloadRouter;

public class CoveredPayloadRouter extends PayloadRouter{
    public TextureRegion iconRegion, coverRegion;

    public CoveredPayloadRouter(String name){
        super(name);
        size = 2;
    }

    @Override
    public void load(){
        super.load();

        iconRegion = Core.atlas.find(name + "-icon");
        coverRegion = Core.atlas.find(name + "-cover");
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{iconRegion, coverRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(iconRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(coverRegion, plan.drawx(), plan.drawy());
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
