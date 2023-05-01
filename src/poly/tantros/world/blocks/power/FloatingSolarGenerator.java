package poly.tantros.world.blocks.power;

import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.world.draw.*;

import static mindustry.Vars.*;

public class FloatingSolarGenerator extends SolarGenerator{
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
