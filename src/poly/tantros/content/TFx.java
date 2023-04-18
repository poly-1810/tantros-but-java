package poly.tantros.content;

import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.*;
import mindustry.graphics.*;

import static arc.graphics.g2d.Draw.color;

public class TFx{
    public static final Effect

    siftDust = new Effect(20f, e -> {
        color(e.color, e.fout(0.1f));
        Fill.circle(e.x, e.y, 3f * e.rotation * e.fout());
    }).layer(Layer.blockAfterCracks + 0.05f),

    oreSparkle = new Effect(90f, e -> {
        color(e.color);
        float x = e.x + Mathf.randomSeedRange(e.id, 3f), y = e.y + Mathf.randomSeedRange(e.id + 1, 3f);
        float scl = Mathf.randomSeed(e.id, 0.25f, 1.5f);
        for(int i = 0; i < 4; i++){
            Drawf.tri(x, y, e.fslope() * scl, 2f * e.fslope() * scl, i * 90);
        }
    });
}
