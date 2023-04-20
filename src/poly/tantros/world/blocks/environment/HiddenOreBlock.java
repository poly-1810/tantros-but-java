package poly.tantros.world.blocks.environment;

import arc.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import poly.tantros.content.*;

public class HiddenOreBlock extends OverlayFloor {
    /** Whether this should draw even when not in the editor. */
    public boolean drawGame = false;
    public OverlayFloor revealReplacement;
    public OreRevealType oreRevealType = OreRevealType.scanner;
    public int tier = 1;
    public Effect revealEffect = TFx.oreReveal;
    public Item deepItemDrop = null;

    public HiddenOreBlock(String name, OverlayFloor revealReplacement) {
        super(name);
        this.revealReplacement = revealReplacement;
        variants = 0;
    }

    public HiddenOreBlock(String name, OverlayFloor revealReplacement, Item ore) {
        this(name, revealReplacement);
        localizedName = ore.localizedName + " " + Core.bundle.get("poly-tantros-deep");
        deepItemDrop = ore;
        mapColor.set(ore.color).mul(0.8f); //Deep ore, slightly tint it darker
        useColor = true;
        drawGame = true;
        variants = 3;
        oreRevealType = OreRevealType.fracker;
    }

    @Override
    public void init() {
        super.init();

        if (revealReplacement == null) throw new RuntimeException("Hidden ore '" + name + "' doesn't have a revealed replacement ore!");
    }

    public void displayRevealEffect(Tile t) {
        Item drop = revealReplacement instanceof HiddenOreBlock h && h.deepItemDrop != null ? h.deepItemDrop : revealReplacement.itemDrop;
        revealEffect.at(t.worldx(), t.worldy(), 0f, drop);
    }

    @Override
    public void drawBase(Tile tile) {
        if (!Vars.state.isEditor() && !drawGame) return; //Only make visible in the editor.
        super.drawBase(tile);
    }

    @Override
    public String getDisplayName(Tile tile) {
        return localizedName;
    }

    public enum OreRevealType {
        scanner,
        fracker
    }
}
