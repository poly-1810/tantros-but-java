package poly.tantros.content;

import arc.graphics.Color;
import mindustry.type.StatusEffect;

public class TStatuses {
    public static StatusEffect stunned;

    public static void load() {
        stunned = new StatusEffect("stunned"){{
            color = Color.valueOf("feb380");
            healthMultiplier = 0.9f;
            speedMultiplier = 0.0f;
            dragMultiplier = 0.3f;
        }};
    };
}
