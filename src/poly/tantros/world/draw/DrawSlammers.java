package poly.tantros.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;

/** Similar to {@link DrawPistons}, slams to the center when the building complete's its progress. */
public class DrawSlammers extends DrawBlock{
    public float angleOffset, maxOffset;
    public int sides = 4;
    public Interp offsetInterp = Interp.smooth2;
    /** Any number <=0 disables layer changes. */
    public float layer = -1;
    public boolean drawPlan = true;
    public String suffix = "-slammer";
    public TextureRegion region1, region2, regiont, regionPrev;

    public DrawSlammers(float maxOffset){
        this.maxOffset = maxOffset;
    }

    public DrawSlammers(String suffix, float maxOffset){
        this(maxOffset);
        this.suffix = suffix;
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        float len = offsetInterp.apply(build.progress()) * maxOffset;
        for(int i = 0; i < sides; i++){
            float angle = angleOffset + i * 360f / sides;
            TextureRegion reg =
                regiont.found() && (Mathf.equal(angle, 315) || Mathf.equal(angle, 135)) ? regiont :
                angle >= 135 && angle < 315 ? region2 : region1;

            if(Mathf.equal(angle, 315)){
                Draw.yscl = -1f;
            }

            Draw.rect(reg, build.x + Angles.trnsx(angle, len), build.y + Angles.trnsy(angle, len), angle);

            Draw.yscl = 1f;
        }
        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        if(!drawPlan) return;
        Draw.rect(regionPrev, plan.drawx(), plan.drawy());
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{regionPrev};
    }

    @Override
    public void load(Block block){
        super.load(block);

        region1 = Core.atlas.find(block.name + suffix + "0", block.name + suffix);
        region2 = Core.atlas.find(block.name + suffix + "1", block.name + suffix);
        regiont = Core.atlas.find(block.name + suffix + "-t");
        regionPrev = Core.atlas.find(block.name + suffix + "-preview");
    }
}
