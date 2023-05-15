package poly.tantros.world.blocks.production;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import poly.tantros.graphics.*;

import static mindustry.Vars.*;

public class OreScanner extends OreRevealer{
    public float revealTime = 5f * 60f;
    public float warmupSpeed = 0.019f;

    public int scanPulses = 3, radarRevolutions = 3, radarTrailLength = 6;
    public float pulseStroke = 2f, ringStroke = 2f;
    public Interp radarInterp = Interp.smooth2;
    public Interp pulseInterp = Interp.pow2Out;

    public OreScanner(String name){
        super(name);
    }

    @Override
    public void init(){
        super.init();
        updateClipRadius(revealRange);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("progress", (OreScannerBuild entity) -> new Bar(
            "bar.loadprogress",
            Pal.accent,
            () -> entity.progress
        ));
    }

    public class OreScannerBuild extends OreRevealerBuild{
        public float progress, totalProgress, warmup;
        public ScanWave trail;

        @Override
        public void draw(){
            super.draw();

            Draw.z(Layer.effect);
            Draw.color(team.color);
            float rad = revealRange * tilesize - (size % 2 == 0 ? 0f : 4f);

            Lines.stroke(warmup * ringStroke);
            Lines.circle(x, y, rad);

            if(warmup > 0.001f && trail != null){
                Draw.color();
                trail.draw(x, y, team.color, rad, warmup);
                Draw.color(team.color);
            }

            float pScl = pulseInterp.apply(progress() * scanPulses % 1f);
            Lines.stroke(warmup * pulseStroke * (1f - pScl));
            Lines.circle(x, y, rad * pScl);
        }

        @Override
        public void updateTile(){
            if(efficiency > 0){
                progress += getProgressIncrease(revealTime);
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            if(radarTrailLength > 0){
                if(trail == null) trail = new ScanWave(radarTrailLength);
                float a = radarInterp.apply(progress() * radarRevolutions % 1f) * 360f + 90f;
                trail.update(a);
            }

            totalProgress += warmup * Time.delta;

            //Reveal ores then self-destruct because there's no reason to keep this around after it's completed its task.
            if(progress >= 1){
                revealOres();
                scheduleBreak();
            }
        }

        @Override
        public boolean shouldConsume(){
            return progress < 1f;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return super.acceptItem(source, item) && shouldConsume();
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return super.acceptLiquid(source, liquid) && shouldConsume();
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public float totalProgress(){
            return totalProgress;
        }

        @Override
        public float progress(){
            return progress;
        }
    }
}
