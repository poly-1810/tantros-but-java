package tantros.content;

import mindustry.content.Planets;

public class Tantros {
    public static void load() {
        Planets.tantros.generator = new NewTantrosPlanetGenerator();
        Planets.tantros.visible = true;
        Planets.tantros.accessible = true;
        Planets.tantros.alwaysUnlocked = true;
    }
}
