package poly.tantros.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import poly.tantros.graphics.*;

public class DrawFloatingRegion extends DrawBlock{
    public TextureRegion region;
    public String suffix = "";
    public float camOffset = 0.25f;
    public float driftScl = 50, driftMag = 2;
    public float surfaceTime = 0.95f;
    public float layerFrom = Layer.light + 0.75f;
    public float layerTo = Layer.light + 2.5f;

    public DrawFloatingRegion(String suffix){
        this.suffix = suffix;
    }

    public DrawFloatingRegion(){
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        Draw.z(build.warmup() > surfaceTime ? layerTo : layerFrom);
        float x = x(build), y = y(build), off = off(build);

        Draw.z(Draw.z() + DrawPseudo3D.layerOffset(x, y));
        Draw.scl(DrawPseudo3D.hScale(off));
        Draw.rect(region, DrawPseudo3D.xHeight(x, off), DrawPseudo3D.yHeight(y, off));
        Draw.scl();
        Draw.z(z);
    }

    public float x(Building build){
        return build.x + Mathf.cos(Time.time, driftScl, driftMag) * build.warmup();
    }

    public float y(Building build){
        return build.y + Mathf.sin(driftScl, driftMag) * build.warmup();
    }

    public float off(Building build){
        return camOffset * build.warmup();
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{region};
    }

    @Override
    public void load(Block block){
        region = Core.atlas.find(block.name + suffix);
    }
}
