package poly.tantros.entity.abilities;

import arc.util.*;
import mindustry.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;

public class ScaledMoveEffectAbility extends MoveEffectAbility{
    @Override
    public void update(Unit unit){
        if(Vars.headless) return;

        counter += Time.delta;
        float len = unit.vel.len();
        if(len >= minVelocity && (counter >= interval) && !unit.inFogTo(Vars.player.team())){
            Tmp.v1.trns(unit.rotation - 90f, x, y);
            counter %= interval;
            effect.at(Tmp.v1.x + unit.x, Tmp.v1.y + unit.y, (rotateEffect ? unit.rotation : effectParam) + rotation, teamColor ? unit.team.color : color, (len - minVelocity) / unit.type.speed);
        }
    }
}
