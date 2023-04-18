package poly.tantros.content;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.type.*;

import static arc.graphics.g2d.Draw.color;

public class TFx{
    //only search once
    private static TextureRegion pointer, pointerIn;

    public static final Effect

    siftDust = new Effect(20f, e -> {
        color(e.color, e.fout(0.1f));
        Fill.circle(e.x, e.y, 3f * e.rotation * e.fout());
    }).layer(Layer.blockAfterCracks + 0.05f),

    oreReveal = new Effect(5f * 60f, e -> {
        float scl = 1f;
        if(e.fin() < 0.1f){
            scl = Interp.sineOut.apply(Mathf.curve(e.fin(), 0, 0.1f));
        }else if(e.fin() > 0.8f){
            scl = 1f - Interp.sineIn.apply(Mathf.curve(e.fin(), 0.8f, 1f));
        }

        float height = 12f * scl;

        if(pointer == null){
            pointer = Core.atlas.find("poly-tantros-ore-reveal-pointer");
            pointerIn = Core.atlas.find("poly-tantros-ore-reveal-pointer-center");
        }

        float dScl = height / (pointer.height / 2f * Draw.scl);
        Draw.scl(dScl);
        Draw.z(Draw.z() - e.y / 64000f);

        Draw.color(Pal.accent);
        Draw.rect(pointer, e.x, e.y);

        Draw.color(Pal.accentBack);
        Draw.rect(pointerIn, e.x, e.y);
        Draw.color();

        TextureRegion item = ((Item)e.data).fullIcon;
        Draw.scl((7f * dScl) / (item.height * Draw.scl));
        Draw.rect(item, e.x, e.y + 12f * dScl);
    }).layer(Layer.overlayUI - 0.1f),

    oreSparkle = new Effect(90f, e -> {
        color(e.color);
        float x = e.x + Mathf.randomSeedRange(e.id, 3f), y = e.y + Mathf.randomSeedRange(e.id + 1, 3f);
        float scl = Mathf.randomSeed(e.id, 0.25f, 1.5f);
        for(int i = 0; i < 4; i++){
            Drawf.tri(x, y, e.fslope() * scl, 2f * e.fslope() * scl, i * 90);
        }
    });
}
