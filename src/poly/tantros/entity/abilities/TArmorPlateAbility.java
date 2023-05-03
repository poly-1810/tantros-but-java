package poly.tantros.entity.abilities;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class TArmorPlateAbility extends ArmorPlateAbility{
    @Override
    public void draw(Unit unit){
        if(warmup > 0.001f){
            if(plateRegion == null){
                plateRegion = Core.atlas.find(unit.type.name + "-armor", unit.type.region);
            }

            Draw.draw(z <= 0 ? Draw.z() : z, () -> {
                Shaders.armor.region = plateRegion;
                Shaders.armor.progress = warmup;
                Shaders.armor.time = -Time.time / 20f;

                //Literally the same thing except for the lack of the line this comment replaces.
                Draw.color(color);
                Draw.shader(Shaders.armor);
                Draw.rect(Shaders.armor.region, unit.x, unit.y, unit.rotation - 90f);
                Draw.shader();

                Draw.reset();
            });
        }
    }
}
