package tantros.content;

import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.type.ItemStack;

public class Tantros {
    public static void load() {
        Planets.tantros.generator = new NewTantrosPlanetGenerator();
        Planets.tantros.visible = true;
        Planets.tantros.accessible = true;
        Planets.tantros.alwaysUnlocked = true;
        Planets.tantros.unlockedOnLand.add(TBlocks.corePod);
        Planets.tantros.defaultCore = TBlocks.corePod;

        // tantros rules
        Planets.tantros.ruleSetter = r -> {
            r.loadout = ItemStack.list(TItems.tantrosCopper, 100);
        };

        // hidding other planets stuff
        Planets.tantros.hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirItems).removeAll(TItems.tantrosItems);

        // hidding tantros stuff for other planets
        Planets.serpulo.hiddenItems.addAll(TItems.tantrosItems);
        Planets.erekir.hiddenItems.addAll(TItems.tantrosItems);
        Planets.gier.hiddenItems.addAll(TItems.tantrosItems);
        Planets.notva.hiddenItems.addAll(TItems.tantrosItems);
        Planets.verilus.hiddenItems.addAll(TItems.tantrosItems);
    }
}
