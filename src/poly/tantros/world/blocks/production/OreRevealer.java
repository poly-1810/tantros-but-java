package poly.tantros.world.blocks.production;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.environment.*;
import poly.tantros.world.blocks.environment.HiddenOreBlock.*;

import static mindustry.Vars.*;

/** Base block for any {@link HiddenOreBlock} revealer. Its building does nothing on its own, subclasses need to call {@link OreRevealerBuild#revealOres()}. */
public abstract class OreRevealer extends Block{
    public OreRevealType revealType = OreRevealType.scanner;
    public int revealRange = 10;
    public boolean squareArea = false;
    public Effect revealEffect = TFx.oreReveal;
    public DrawBlock drawer = new DrawDefault();

    public OreRevealer(String name){
        super(name);
        update = true;
        solid = true;
        rebuildable = false;
        group = BlockGroup.drills;
        envEnabled |= Env.underwater;
        flags = EnumSet.of(BlockFlag.drill);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.range, revealRange, StatUnit.blocks);
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(squareArea){
            Drawf.square(x, y, revealRange * tilesize, Pal.placing);
        }else{
            Drawf.dashCircle(x, y, revealRange * tilesize, Pal.placing);
        }
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out){
        drawer.getRegionsToOutline(this, out);
    }

    public class OreRevealerBuild extends Building{
        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        /** Reveal ores within range. Called by subclasses. */
        public void revealOres(){
            boolean oreRevealed = false;
            for(int x = tile.x - revealRange - 2; x <= tile.x + revealRange + 2; x++){
                for(int y = tile.y + revealRange - 2; y >= tile.y - revealRange + 2; y--){
                    Tile t = world.tile(x, y);
                    if(t != null && (squareArea || t.within(this, revealRange * tilesize)) && t.overlay() instanceof HiddenOreBlock h && h.oreRevealType == revealType){
                        revealed(t, h);
                        oreRevealed = true;
                    }
                }
            }

            //If an ore was revealed, fire a world load event to index the newly revealed ore.
            if(oreRevealed) Events.fire(new WorldLoadEvent());
        }

        public void revealed(Tile t, HiddenOreBlock ore){
            t.setOverlayQuiet(ore.revealReplacement);
            revealEffect.at(t.worldx(), t.worldy(), 0f, ore.revealReplacement.itemDrop);
        }
    }
}
