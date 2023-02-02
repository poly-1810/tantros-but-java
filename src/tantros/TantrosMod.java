package tantros;

import mindustry.mod.Mod;
import tantros.content.TBlocks;
import tantros.content.TItems;
import tantros.content.Tantros;
import tantros.content.TantrosTechTree;

public class TantrosMod extends Mod {
    public TantrosMod() {}

    @Override
    public void loadContent() {
        TItems.load();
        TBlocks.load();
        Tantros.load();
        TantrosTechTree.load();
    }
}
