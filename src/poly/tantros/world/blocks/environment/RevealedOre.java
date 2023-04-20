package poly.tantros.world.blocks.environment;

import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import poly.tantros.content.*;

public class RevealedOre extends OreBlock {
    public Effect sparkleEffect = TFx.oreSparkle;
    public float effectChance = 0.01f;

    public RevealedOre(String name, Item ore) {
        super(name, ore);
    }

    public RevealedOre(Item ore) {
        super(ore);
    }

    /*
    Waiting for my PR to be merged so that the ore can be solely effects

    @Override
    public void drawBase(Tile tile) {
        if (!Vars.state.isEditor()) return; //Only make visible in the editor.
        super.drawBase(tile);
    }*/

    @Override
    public boolean updateRender(Tile tile) {
        return sparkleEffect != Fx.none;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if (state.tile.block() == Blocks.air && Mathf.chanceDelta(effectChance)) {
            sparkleEffect.at(state.tile.worldx(), state.tile.worldy(), itemDrop.color);
        }
    }
}
