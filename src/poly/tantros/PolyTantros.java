package poly.tantros;

import arc.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.content.*;
import poly.tantros.maps.planet.*;
import poly.tantros.ui.dialogs.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class PolyTantros extends Mod {
    public PolyTantros() {
        Events.on(EventType.ClientLoadEvent.class, ignored -> {
            app.post(() -> {
                new TDisclaimer().show();
            });
        });
    }

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

            mod.meta.displayName = "[#86f195]Poly's[] [#597be3]Tantros[] (beta)";
            mod.meta.author =
                    """
                    [#7d7d7d]ABreaker#5940 (original author)[]
                    [#86f195]Poly#1810 (port author)[]
                    [#c7baad]MeiNanziiii#5309 (translations & stuff)[]
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
        TWeathers.load();

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
    }
}
