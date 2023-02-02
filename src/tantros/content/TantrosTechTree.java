package tantros.content;

import mindustry.content.Planets;

import static mindustry.content.TechTree.nodeRoot;
import static tantros.content.TBlocks.corePod;

public class TantrosTechTree {
    public static void load() {
        Planets.tantros.techTree = nodeRoot("tantros", corePod, true, () -> {
            
        });
    }
}
