package tantros.content;

import mindustry.content.Planets;

import static mindustry.content.TechTree.*;
import static tantros.content.TBlocks.corePod;

public class TantrosTechTree {
    public static void load() {
        Planets.tantros.techTree = nodeRoot("tantros", corePod, true, () -> {
            nodeProduce(TItems.tantrosCopper, () -> {
                node(TItems.zinc, () -> {
                   node(TItems.brass, () -> {
                       
                   });
                });
            });
        });
    }
}
