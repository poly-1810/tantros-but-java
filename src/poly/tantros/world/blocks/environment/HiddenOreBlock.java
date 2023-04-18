package poly.tantros.world.blocks.environment;

import mindustry.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class HiddenOreBlock extends OverlayFloor{
    public OverlayFloor revealReplacement;
    public OreRevealType oreRevealType = OreRevealType.scanner;

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
        if(!Vars.state.isEditor()) return; //Only make visible in the editor.
        super.drawBase(tile);
    }

    public enum OreRevealType{
        scanner,
        fracker
    }
}
