package tantros;

import mindustry.mod.Mod;
import tantros.content.Blocks;

public class TantrosMod extends Mod {
    public TantrosMod() {}

    @Override
    public void loadContent() {
        Blocks.load();
    }
}
