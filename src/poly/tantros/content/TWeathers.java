package poly.tantros.content;

import mindustry.type.weather.*;

public class TWeathers {
    public static ParticleWeather iceChunks;

    public static void load() {
        iceChunks = new ParticleWeather("ice-chunks"){{
           particleRegion = "shell-back";
           density = 320000f;
           sizeMax = 360f;
           sizeMin = 120f;
           maxAlpha = 0.3f;
           minAlpha = 0.1f;
           useWindVector = true;
           baseSpeed = 0.2f;
           randomParticleRotation = true;
        }};
    }
}
