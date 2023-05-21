package poly.tantros;

import arc.*;
import arc.graphics.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.mod.Mods.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.content.*;
import poly.tantros.maps.planet.*;
import poly.tantros.ui.dialogs.*;
import poly.tantros.util.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class PolyTantros extends Mod{
    public TUnderwaterFilter tUnderwaterFilter;

    public PolyTantros(){
        if(Core.settings.getBool("pt-show-disclaimer", true)){
            Events.on(EventType.ClientLoadEvent.class, ignored -> {
                app.post(() -> new TDisclaimer().show());
            });
            Core.settings.put("pt-show-disclaimer", false);
        }
    }


    @Override
    public void init(){
        Planets.tantros.generator = new PolyTantrosPlanetGenerator();
        Planets.tantros.visible = true;
        Planets.tantros.accessible = true;
        Planets.tantros.alwaysUnlocked = true;
        Planets.tantros.defaultCore = Effect.corePod;
        Planets.tantros.ruleSetter = r -> {
            r.lighting = true;
            r.ambientLight = Color.valueOf("020749");
            r.fog = true;
        };

        //hide all not-tantros items
        Planets.tantros.hiddenItems.addAll(TItems.notTantrosItems).removeAll(TItems.tantrosItems);

        //hide all tantros items on another planets
        Planets.serpulo.hiddenItems.addAll(TItems.onlyTantrosItems);
        Planets.erekir.hiddenItems.addAll(TItems.onlyTantrosItems);

        Planets.tantros.unlockedOnLand.add(Effect.corePod);

        if(!headless){
            LoadedMod mod = Vars.mods.locateMod("poly-tantros");

            mod.meta.displayName = "[#86f195]Poly's[] [#597be3]Tantros[] (beta)";
            mod.meta.author =
            """
            [#86f195]Poly#1810 (this mod)[]
            [#c7baad]MeiNanziiii#5309 (translator & mod dev)[]
            [#f9c11c]MEEPofFaith#7277 (cool mod dev)[]
            [#7d7d7d]ABreaker#5940 (original mod)[]
            """
            ;
            mod.meta.description =
            """
            Explore the depths of planet Tantros with new mind-blowing additions!
            """
            ;

            loadSettings();

            tUnderwaterFilter = new TUnderwaterFilter();
            Events.run(Trigger.update, tUnderwaterFilter::update);
        }
    }

    @Override
    public void loadContent(){
        TItems.load();
        TStatuses.load();
        TUnitTypes.load();
        TWeathers.load();

        //special category
        Resources.load();

        //loading blocks
        Crafting.load();
        Defense.load();
        Distribution.load();
        Effect.load();
        Environment.load();
        Liquid.load();
        Logic.load();
        Power.load();
        Production.load();
        Turret.load();
        Units.load();
    }

    private void loadSettings(){
        ui.settings.addCategory(bundle.get("setting.pt-title"), "poly-tantros-settings-icon", t -> {
            t.sliderPref("pt-filter-intensity", 60, 0, 100, 5, s -> s + "%");
        });
    }
}
