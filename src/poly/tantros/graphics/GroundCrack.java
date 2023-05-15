package poly.tantros.graphics;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;

public class GroundCrack{
    protected int length;
    protected FloatSeq points;
    protected float width;

    public GroundCrack(float dst, float angle, float range, float width){
        length = Mathf.ceil(dst / range);
        this.width = width;
        points = new FloatSeq(length * 2);

        //See Fx.chainLightning
        Tmp.v1.trns(angle, dst).nor();
        float normx = Tmp.v1.x, normy = Tmp.v1.y;
        float spacing = dst / length;

        for(int i = 0; i < length; i++){
            float nx = 0, ny = 0;

            if(i > 0){
                float len = (i + 1) * spacing;
                Tmp.v1.setToRandomDirection().scl(range / 2f * Mathf.sqrt(Mathf.random()));
                nx = normx * len + Tmp.v1.x;
                ny = normy * len + Tmp.v1.y;
            }

            points.add(nx, ny);
        }
    }

    //only for copy
    protected GroundCrack(int length){
        this.length = length;
        points = new FloatSeq(length * 2);
    }

    public GroundCrack copy(){
        GroundCrack out = new GroundCrack(length);
        out.points.addAll(points);
        out.width = width;
        return out;
    }

    public float width(){
        return width;
    }

    public void draw(float rootX, float rootY, Color color, float scl){
        scl = Mathf.clamp(scl);
        if(scl < 0.001f || width < 0.001f) return;

        Draw.color(color);

        float[] items = points.items;
        int drawnPoints = (int)Math.ceil(length * scl);
        float width = this.width * scl;
        float size = width / drawnPoints;
        float lastAngle = 0f;

        for(int i = 0; i < drawnPoints - 1; i++){
            int item = i * 2;
            float x1 = rootX + items[item], y1 = rootY + items[item + 1];
            float x2 = rootX + items[item + 2], y2 = rootY + items[item + 3];

            float z2 = -Angles.angleRad(x1, y1, x2, y2);
            float z1 = i == 0 ? z2 : lastAngle;

            float
            cx = Mathf.sin(z1) * (width - i * size),
            cy = Mathf.cos(z1) * (width - i * size),
            nx = Mathf.sin(z2) * (width - (i + 1) * size),
            ny = Mathf.cos(z2) * (width - (i + 1) * size);

            Fill.quad(
            x1 - cx, y1 - cy,
            x1 + cx, y1 + cy,
            x2 + nx, y2 + ny,
            x2 - nx, y2 - ny
            );

            lastAngle = z2;
        }

        Draw.reset();
    }

    public void shrink(float amount){
        width = Mathf.maxZero(width - amount * Time.delta);
    }
}
