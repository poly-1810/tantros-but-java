package poly.tantros.world.draw;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import poly.tantros.graphics.*;

public class DrawFloatingCable extends DrawFloatingRegion{
    public int segments = 8;

    public DrawFloatingCable(String suffix){
        super(suffix);
    }

    public DrawFloatingCable(){
        super();
    }

    {
        layerFrom = layerTo = Layer.light + 0.25f;
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        Draw.z(layer(build));

        float x = x(build), y = y(build), off = off(build);
        Draw.z(Draw.z() + DrawPseudo3D.layerOffset(x, y));

        float hWidth = region.height * Draw.scl * region.scl() * 2, hScl = DrawPseudo3D.hScale(off);
        float ex = DrawPseudo3D.xHeight(x, off), ey = DrawPseudo3D.yHeight(y, off);

        for(int i = 0; i < segments; i++){
            float p1 = (float)i / segments, p2 = (i + 1f) / segments,
                x1 = Mathf.lerp(build.x, ex, p1), y1 = Mathf.lerp(build.y, ey, p1),
                x2 = Mathf.lerp(build.x, ex, p2), y2 = Mathf.lerp(build.y, ey, p2),
                s1 = Mathf.lerp(1f, hScl, p1), s2 = Mathf.lerp(1f, hScl, p2);

            float len = Mathf.len(x2 - x1, y2 - y1);
            float diffX = (x2 - x1) / len * hWidth, diffY = (y2 - y1) / len * hWidth;

            Fill.quad(
                region,
                x1 - diffY * s1,
                y1 + diffX * s1,

                x1 + diffY * s1,
                y1 - diffX * s1,

                x2 + diffY * s2,
                y2 - diffX * s2,

                x2 - diffY * s2,
                y2 + diffX * s2
            );
        }

        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{};
    }
}
