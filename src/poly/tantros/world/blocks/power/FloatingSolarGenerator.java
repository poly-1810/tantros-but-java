package poly.tantros.world.blocks.power;

import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.input.*;
import poly.tantros.world.draw.*;

import static mindustry.Vars.*;

public class FloatingSolarGenerator extends SolarGenerator{
    public int spacing = 4;
    public float riseSpeed = 0.01f;
    public Interp riseInterp = Interp.smooth;

    public FloatingSolarGenerator(String name){
        super(name);

        drawer = new DrawMulti(
            new DrawDefault(),
            new DrawFloatingCable("-cable"),
            new DrawFloatingRegion("-panel")
        );
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(spacing < 1) return;
        Drawf.square(x, y, (spacing + size / 2f + 2) * tilesize, 0f, Pal.remove);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        for(int x = tile.x - spacing + off; x <= tile.x + spacing ; x++){
            for(int y = tile.y - spacing + off; y <= tile.y + spacing; y++){
                Tile t = world.tile(x, y);
                if(t != null && t.block() instanceof FloatingSolarGenerator s && (s == this || s.intersectsSpacing(t.build.tile, tile))) return false;
            }
        }
        return true;
    }

    public boolean intersectsSpacing(int sx, int sy, int ox, int oy, int ext){ //TODO untested with panels larger than 1x1
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        return ox >= sx - spacing + off - ext && ox <= sx + spacing + ext &&
            oy >= sy - spacing + off - ext && oy <= sy + spacing + ext;
    }

    public boolean intersectsSpacing(Tile self, Tile other){
        return intersectsSpacing(self.x, self.y, other.x, other.y, 0);
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        if(spacing >= 1) TPlacement.calculatePanels(points, this, rotation, (point, other) -> intersectsSpacing(point.x, point.y, other.x, other.y, 1));
    }

    public class FloatingSolarGeneratorBuild extends SolarGeneratorBuild{
        public float rise = 0f;

        @Override
        public void updateTile(){
            productionEfficiency = enabled ? state.rules.solarMultiplier * Mathf.maxZero(Attribute.light.env() + (state.rules.lighting ? 1f - state.rules.ambientLight.a * (1f - warmup()) : warmup())) : 0f;

            rise += riseSpeed * Time.delta;
            if(rise > 1f) rise = 1f;
        }

        @Override
        public float warmup(){
            return riseInterp.apply(rise);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(rise);
        }

        @Override
        public void read(Reads read){
            super.read(read);
            rise = read.f();
        }
    }
}
