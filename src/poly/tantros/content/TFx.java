package poly.tantros.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.type.*;
import poly.tantros.graphics.*;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class TFx {
    private static TextureRegion pointer, pointerIn, pointerUnknown;

    public static final Effect

    siftDust = new Effect(20f, e -> {
        color(e.color, e.fout(0.1f));
        Fill.circle(e.x, e.y, 3f * e.rotation * e.fout());
    }).layer(Layer.blockAfterCracks + 0.05f),

    oreReveal = new Effect(5f * 60f, e -> {
        float scl = 1f;
        if (e.fin() < 0.1f) {
            scl = Interp.sineOut.apply(Mathf.curve(e.fin(), 0, 0.1f));
        } else if (e.fin() > 0.8f) {
            scl = 1f - Interp.sineIn.apply(Mathf.curve(e.fin(), 0.8f, 1f));
        }

        float height = 12f * scl;

        if (pointer == null) {
            pointer = Core.atlas.find("poly-tantros-ore-reveal-pointer");
            pointerIn = Core.atlas.find("poly-tantros-ore-reveal-pointer-center");
            pointerUnknown = Core.atlas.find("poly-tantros-ore-reveal-pointer-unknown");
        }

        float dScl = height / (pointer.height / 2f * Draw.scl);
        Draw.scl(dScl);
        Draw.z(Draw.z() - e.y / 64000f);

        Draw.color(Pal.accent);
        Draw.rect(pointer, e.x, e.y);

        Draw.color(Pal.accentBack);
        Draw.rect(pointerIn, e.x, e.y);
        Draw.color();

        Item ictem = (Item)e.data;
        TextureRegion icon;
        if (ictem != null) {
            icon = ictem.fullIcon;
            Draw.scl((7f * dScl) / (icon.height * Draw.scl));
        } else {
            icon = pointerUnknown;
        }
        Draw.rect(icon, e.x, e.y + 12f * dScl);
    }).layer(Layer.overlayUI - 0.1f),

    oreSparkle = new Effect(90f, e -> {
        color(e.color);
        float x = e.x + Mathf.randomSeedRange(e.id, 3f), y = e.y + Mathf.randomSeedRange(e.id + 1, 3f);
        float scl = Mathf.randomSeed(e.id, 0.25f, 1.5f);
        for (int i = 0; i < 4; i++) {
            Drawf.tri(x, y, e.fslope() * scl, 2f * e.fslope() * scl, i * 90);
        }
    }),

    groundCrackFade = new Effect(400f, 500f, e -> {
        if (!(e.data instanceof GroundCrack crack)) return;
        e.lifetime = 240f * e.rotation;

        crack.draw(e.x, e.y, e.color, e.rotation * e.fout());
    }).layer(Layer.scorch - 1f),

    frackFlame = new Effect(75f, 250f, e -> {
        //dist from center to slammer tops
        float len = 137/4f;

        for(int i = 0; i < 4; i++){
            float ex = e.x + Geometry.d4x(i) * len,
                ey = e.y + Geometry.d4y(i) * len,
                rot = i * 90f;
            e.scaled(28f, s -> {
                color(TPal.rubedoLight, TPal.rubedoDark, s.fin());
                float w = 1.2f + 9 * s.fout();
                Drawf.tri(ex, ey, w, 32f * s.fout(), rot);
                Drawf.tri(ex, ey, w, 3f * s.fout(), rot + 180f);
            });

            color(TPal.rubedoLight, TPal.rubedoDark, e.fin());
            randLenVectors(e.id, 13, e.finpow() * 36f, rot, 15f, (x, y) -> {
                Fill.circle(ex + x, ey + y, e.fout() * 3f);
            });
        }
    });
}
