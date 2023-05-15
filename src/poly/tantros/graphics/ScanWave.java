package poly.tantros.graphics;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;

public class ScanWave{
    public int length;

    protected FloatSeq points;
    protected float counter = 0f, lastAngle = -1f, lastRadScl = -1f;

    public ScanWave(int length){
        this.length = length;
        points = new FloatSeq(length * 2);
    }

    public void clear(){
        points.clear();
    }

    public int size(){
        return points.size / 2;
    }

    public void draw(float x, float y, Color color, float radius, float alpha){
        float[] items = points.items;

        for(int i = 0; i < points.size; i += 2){
            float a1 = items[i], r1 = items[i + 1] * radius;
            float a2, r2;

            //last position is always lastAngle/RadScl
            if(i < points.size - 2){
                a2 = items[i + 2];
                r2 = items[i + 3];
            }else{
                a2 = lastAngle;
                r2 = lastRadScl;
            }
            r2 *= radius;

            float c1 = Tmp.c1.set(color).mulA((float)i / points.size * alpha).toFloatBits();
            float c2 = Tmp.c1.set(color).mulA((i + 2f) / points.size * alpha).toFloatBits();

            Fill.quad(
            x, y, c2,
            x + Mathf.cosDeg(a2) * r2, y + Mathf.sinDeg(a2) * r2, c2,
            x + Mathf.cosDeg(a1) * r1, y + Mathf.sinDeg(a1) * r1, c1,
            x, y, c1
            );
        }
    }

    public void shorten(){
        if((counter += Time.delta) >= 1f){
            if(points.size >= 2){
                points.removeRange(0, 1);
            }

            counter %= 1f;
        }
    }

    public void update(float angle){
        update(angle, 1f);
    }

    public void update(float angle, float radScl){
        if((counter += Time.delta) >= 1f){
            if(points.size > length * 2){
                points.removeRange(0, 1);
            }

            points.add(angle, radScl);

            counter %= 1f;
        }

        lastAngle = angle;
        lastRadScl = radScl;
    }
}
