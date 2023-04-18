package poly.tantros.world.blocks.production;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import poly.tantros.content.*;
import poly.tantros.graphics.*;
import poly.tantros.world.blocks.environment.*;

import static mindustry.Vars.*;

public class OreScanner extends OreRevealer{
    public float revealTime = 5f * 60f;
    public float warmupSpeed = 0.019f;
    public Effect revealEffect = TFx.oreReveal;

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
        updateClipRadius(revealRange * tilesize);
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
        public ScanTrail trail;

        @Override
        public void draw(){
            super.draw();

            Draw.z(Layer.effect);
            Draw.color(team.color);
            float rad = revealRange * tilesize;

            Lines.stroke(warmup * ringStroke);
            Lines.circle(x, y, rad);

            if(warmup > 0.001f){
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
            if(efficiency > 0 && progress < 1){
                progress += 1f / revealTime;
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            if(radarTrailLength > 0){
                if(trail == null) trail = new ScanTrail(radarTrailLength);
                float a = radarInterp.apply(progress() * radarRevolutions % 1f) * 360f + 90f;
                trail.update(a);
            }

            totalProgress += warmup * Time.delta;

            //Reveal ores then self-destruct because there's no reason to keep this around after it's completed its task.
            if(progress >= 1){
                progress = 1;
                revealOres();
                kill();
            }
        }

        @Override
        public void revealed(Tile t, HiddenOreBlock ore){
            super.revealed(t, ore);
            revealEffect.at(t.worldx(), t.worldy(), 0f, ore.revealReplacement.itemDrop);
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
