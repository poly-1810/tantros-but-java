package poly.tantros.maps.planet;

import mindustry.maps.planet.*;
import mindustry.type.*;

import static mindustry.Vars.state;

public class PolyTantrosPlanetGenerator extends TantrosPlanetGenerator{
    @Override
    public boolean allowLanding(Sector sector){
        return false;
    }

    @Override
    protected void generate(){
        super.generate();

        state.rules.env = sector.planet.defaultEnv;
    }
}
