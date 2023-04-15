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
    }).layer(Layer.blockAfterCracks + 0.05f);
}
