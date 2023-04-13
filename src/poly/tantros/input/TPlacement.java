package poly.tantros.input;

import arc.func.*;
import arc.struct.*;
import mindustry.entities.units.*;
import mindustry.input.*;
import mindustry.world.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.power.*;

import static mindustry.Vars.player;

public class TPlacement {
    private static final Seq<BuildPlan> plans1 = new Seq<>();

    /** Copy of {@link Placement#calculateBridges(Seq, DirectionBridge, boolean, Boolf)} adapted to work for PowerConduits and BeamNodes */
    public static void calculateBridges(Seq<BuildPlan> plans, BeamNode bridge, Boolf<Block> same) {
        if (Placement.isSidePlace(plans)) return;

        //check for orthogonal placement + unlocked state
        if (!(plans.first().x == plans.peek().x || plans.first().y == plans.peek().y) || !bridge.unlockedNow()) {
            return;
        }

        Boolf<BuildPlan> placeable = plan ->
            (plan.placeable(player.team()) ||
            (plan.tile() != null && same.get(plan.tile().block()))); //don't count the same block as inaccessible

        var result = plans1.clear();

        outer:
        for (int i = 0; i < plans.size;) {
            var cur = plans.get(i);
            result.add(cur);

            //gap found
            if (i < plans.size - 1 && placeable.get(cur) && (!placeable.get(plans.get(i + 1)))) {

                //find the closest valid position within range
                for (int j = i + 2; j < plans.size; j++) {
                    var other = plans.get(j);

                    //BeamNodes don't have a valid position method
                    boolean posValid = false;
                    if (cur.x == other.x) {
                        posValid = Math.abs(cur.y - other.y) <= bridge.range;
                    } else if(cur.y == other.y) {
                        posValid = Math.abs(cur.x - other.x) <= bridge.range;
                    }

                    //out of range now, set to current position and keep scanning forward for next occurrence
                    if (!posValid) {
                        //add 'missed' conduits
                        for (int k = i + 1; k < j; k++) {
                            result.add(plans.get(k));
                        }
                        i = j;
                        continue outer;
                    } else if(placeable.get(other)) {
                        //found a link, assign bridges
                        cur.block = bridge;
                        other.block = bridge;

                        i = j;
                        continue outer;
                    }
                }

                //if it got here, that means nothing was found. this likely means there's a bunch of stuff at the end; add it and bail out
                for (int j = i + 1; j < plans.size; j++) {
                    result.add(plans.get(j));
                }
                break;
            } else {
                i ++;
            }
        }

        plans.set(result);
    }
}
