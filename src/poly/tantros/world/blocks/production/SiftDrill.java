package poly.tantros.world.blocks.production;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.blocks.production.*;

import static mindustry.Vars.tilesize;

public class SiftDrill extends Drill{
    public float siftScl = 5f, siftMag = Float.MIN_VALUE;

    public SiftDrill(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();
        if(siftMag == Float.MIN_VALUE) siftMag = size * tilesize / 2f;
    }

    public class SiftDrillBuild extends DrillBuild{
        @Override
        public void draw(){
            float s = 0.3f;
            float ts = 0.6f;

            Draw.rect(region, x, y);
            Draw.z(Layer.blockCracks);
            drawDefaultCracks();

            Draw.z(Layer.blockAfterCracks);
            if(drawRim){
                Draw.color(heatColor);
                Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)));
                Draw.blend(Blending.additive);
                Draw.rect(rimRegion, x, y);
                Draw.blend();
                Draw.color();
            }

            Draw.rect(rotatorRegion, x + Mathf.sin(timeDrilled, siftScl, siftMag), y);

            Draw.rect(topRegion, x, y);

            if(dominantItem != null && drawMineItem){
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }
        }
    }
}
