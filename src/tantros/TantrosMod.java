package tantros;

import mindustry.mod.Mod;
import tantros.content.Blocks;
import tantros.content.Tantros;

public class TantrosMod extends Mod {
    public TantrosMod() {}

    @Override
    public void loadContent() {
        Blocks.load();
        Tantros.load();
    }
}
