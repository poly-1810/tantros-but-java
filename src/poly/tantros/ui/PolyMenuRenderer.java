package poly.tantros.ui;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.math.geom.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.graphics.g3d.*;

import static arc.Core.graphics;
import static mindustry.Vars.*;

public class PolyMenuRenderer extends MenuRenderer{
    public static FrameBuffer buffer;
    public PlanetParams params = new PlanetParams(){{
        alwaysDrawAtmosphere = true;
        planet = Planets.tantros;
    }};

    @Override
    public void render() {
        int size = Math.max(graphics.getWidth(), graphics.getHeight());

        if(buffer == null) buffer = new FrameBuffer(size, size);

        buffer.begin(Color.clear);

        params.camPos.rotate(Vec3.Y, -0.10f);

        renderer.planets.render(params);

        buffer.end();

        Draw.rect(Draw.wrap(buffer.getTexture()), (float) graphics.getWidth() / 2, (float) graphics.getHeight() / 2, graphics.getWidth(), graphics.getHeight());
    }
}
