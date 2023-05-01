package poly.tantros.world.blocks.power;

import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
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
        Drawf.dashSquare(Pal.placing, x, y, ((spacing + size / 2f) * tilesize) * 2f);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        int hS = size / 2, off = 1 - size % 2;
        for(int x = tile.x - spacing - hS + off; x <= tile.x + spacing + hS; x++){
            for(int y = tile.y - spacing - hS + off; y <= tile.y + spacing + hS; y++){
                Tile t = world.tile(x, y);
                if(t != null && t.block() instanceof FloatingSolarGenerator s && (s == this || s.intersectsSpacing(t.build.tile, tile))) return false;
            }
        }
        return true;
    }

    public boolean intersectsSpacing(Tile self, Tile other){
        int sHS = size/2, off = 1 - size % 2;
        return other.x >= self.x - spacing - sHS + off && other.x <= self.x + spacing + sHS &&
            other.y >= self.y - spacing - sHS + off && other.y <= self.y + spacing + sHS;
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
