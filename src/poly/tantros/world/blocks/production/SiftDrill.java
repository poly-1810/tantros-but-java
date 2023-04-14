package poly.tantros.world.blocks.production;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.world.blocks.production.*;
import poly.tantros.content.*;

import static mindustry.Vars.*;

public class SiftDrill extends Drill{
    public float siftScl = 5f, siftMag = Float.MIN_VALUE;
    public float siftEffectTime = Float.MIN_VALUE, siftEffectMinDist = 0f, siftEffectMaxDist = -1f;
    public float updateEffects = 5f;

    public SiftDrill(String name){
        super(name);
        drillEffect = Fx.none;
        updateEffect = TFx.siftDust;
        updateEffectChance = 0.25f;
    }

    @Override
    public void init(){
        super.init();
        if(siftMag == Float.MIN_VALUE) siftMag = size * tilesize / 2f;
        if(siftEffectTime == Float.MIN_VALUE) siftEffectTime = siftMag * 1.1f;
        if(siftEffectMaxDist < 0f) siftEffectMaxDist = size * tilesize / 2f;
    }

    public class SiftDrillBuild extends DrillBuild{
        @Override
        public void updateTile(){
            //Copy over Drill code just so I can mess with the update effect.
            if(timer(timerDump, dumpTime)){
                dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
            }

            if(dominantItem == null){
                return;
            }

            timeDrilled += warmup * delta();

            float delay = getDrillTime(dominantItem);

            if(items.total() < itemCapacity && dominantItems > 0 && efficiency > 0){
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

                lastDrillSpeed = (speed * dominantItems * warmup) / delay;
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
                progress += delta() * dominantItems * speed * warmup;

                float pos = Mathf.sin(timeDrilled, siftScl, siftMag);
                float posChance = 1f - Mathf.curve(Math.abs(pos), 0f, siftEffectTime);
                for(int i = 0; i < updateEffects; i++){
                    if(Mathf.chanceDelta(updateEffectChance * warmup * posChance)){
                        float ex = x + pos, ey = y + Mathf.range(siftEffectMinDist, siftEffectMaxDist);

                        updateEffect.at(ex, ey, posChance * warmup, Tmp.c1.set(world.tileWorld(ex, ey).floor().mapColor).mul(1.5f + Mathf.range(0.15f)));
                    }
                }
            }else{
                lastDrillSpeed = 0f;
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
                return;
            }

            if(dominantItems > 0 && progress >= delay && items.total() < itemCapacity){
                offload(dominantItem);

                progress %= delay;

                if(wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)) drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
            }
        }

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

            Draw.z(Layer.blockAfterCracks + 0.1f);
            Draw.rect(topRegion, x, y);

            if(dominantItem != null && drawMineItem){
                Draw.color(dominantItem.color);
                Draw.rect(itemRegion, x, y);
                Draw.color();
            }
        }
    }
}
