package poly.tantros.maps.planet;

import mindustry.maps.planet.TantrosPlanetGenerator;
import mindustry.type.Sector;

public class PolyTantrosPlanetGenerator extends TantrosPlanetGenerator {
    @Override
    public boolean allowLanding(Sector sector) {
        return false;
    }
}
