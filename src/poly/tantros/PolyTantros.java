package poly.tantros;

import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.mod.Mod;
import mindustry.mod.Mods.LoadedMod;
import poly.tantros.content.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.maps.planet.PolyTantrosPlanetGenerator;

import static mindustry.Vars.headless;

public class PolyTantros extends Mod {
    public PolyTantros() {}

    @Override
    public void init() {
        Planets.tantros.generator = new PolyTantrosPlanetGenerator();
        Planets.tantros.visible = true;
        Planets.tantros.accessible = true;
        Planets.tantros.alwaysUnlocked = true;
        Planets.tantros.defaultCore = Effect.corePod;
        Planets.tantros.hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirItems).removeAll(TItems.tantrosItems);
        Planets.tantros.unlockedOnLand.add(Effect.corePod);

        if (!headless) {
            LoadedMod mod = Vars.mods.locateMod("poly-tantros");

            mod.meta.displayName = "[#86f195]Poly's[] [#597be3]Tantros[] (pre-aplha)";
            mod.meta.author =
                    """
                    [#7d7d7d]ABreaker#5940 (original author)[]
                    [#86f195]Poly#1810 (port author)[]
                    [#c7baad]MeiNanziiii#5309 (tanslations & stuff)[]
                    [#f9c11c]MEEPofFaith#7277 (fixes & helping)[]
                    """
            ;
            mod.meta.description =
                    """
                    Mod adds content to Tantros
                    """
            ;
        }
    }

    @Override
    public void loadContent() {
        TItems.load();
        TStatuses.load();
        TUnitTypes.load();

        // loading blocks
        Crafting.load();
        Defense.load();
        Distribution.load();
        Effect.load();
        Environment.load();
        Liquid.load();
        Logic.load();
        Power.load();
        Production.load();
        Resources.load();
        Turret.load();
        Units.load();

        TWeathers.load();
    }
}
