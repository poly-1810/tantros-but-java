package poly.tantros.world.blocks.environment;

import mindustry.*;
import mindustry.entities.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import poly.tantros.content.*;

public class HiddenOreBlock extends OverlayFloor{
    /** Whether this should draw even when not in the editor. */
    public boolean drawGame = false;
    public OverlayFloor revealReplacement;
    public OreRevealType oreRevealType = OreRevealType.scanner;
    public Effect revealEffect = TFx.oreReveal;

    public HiddenOreBlock(String name){
        super(name);
        variants = 0;
    }

    @Override
    public void init(){
        super.init();

        if(revealReplacement == null) throw new RuntimeException("Hidden ore '" + name + "' doesn't have a revealed replacement ore!");
    }

    @Override
    public void drawBase(Tile tile){
        if(!Vars.state.isEditor() && !drawGame) return; //Only make visible in the editor.
        super.drawBase(tile);
    }

    public enum OreRevealType{
        scanner,
        fracker
    }
}
