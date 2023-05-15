package poly.tantros.maps.planet;

import mindustry.maps.planet.*;
import mindustry.type.*;

public class PolyTantrosPlanetGenerator extends TantrosPlanetGenerator{
    @Override
    public boolean allowLanding(Sector sector){
        return false;
    }
}
