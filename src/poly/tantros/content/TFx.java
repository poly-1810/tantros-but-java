package poly.tantros.content;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.blocks.payloads.*;

import static arc.graphics.g2d.Draw.color;

public class TFx{
    public static Effect

    siftDust = new Effect(20f, e -> {
        color(e.color, e.fout(0.1f));
        Fill.circle(e.x, e.y, 3f * e.rotation * e.fout());
    }).layer(Layer.blockAfterCracks + 0.05f),

    launcherFx = new Effect(7, 600, e -> {
        if (!(e.data instanceof PayloadMassDriver.PayloadMassDriverData p)) return;
        e.lifetime = 0.5f + (Mathf.dst(e.x, e.y, p.ox, p.oy) * 2);
        Tmp.v1.set(p.x, p.y).lerp(p.ox, p.oy, Interp.pow2In.apply(e.fin()));
        p.payload.set(Tmp.v1.x, Tmp.v1.y, e.rotation);
        p.payload.draw();
    });

    public static void load() {
        launcherFx.layer = Layer.flyingUnitLow - 1;
    }
}
