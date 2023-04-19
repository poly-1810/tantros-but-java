package poly.tantros.world.blocks.production;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import poly.tantros.world.blocks.environment.*;
import poly.tantros.world.blocks.environment.HiddenOreBlock.*;

import static mindustry.Vars.*;

/** Base block for any {@link HiddenOreBlock} revealer. Its building does nothing on its own, subclasses need to call {@link OreRevealerBuild#revealOres()}. */
public abstract class OreRevealer extends Block{
    public OreRevealType revealType = OreRevealType.scanner;
    public int revealRange = 10;
    public boolean squareArea = false;
    public int tier = 1;
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
        boolean even = size % 2 == 0;
        if(squareArea){
            Drawf.dashSquare(Pal.placing, x, y, revealRange * tilesize * 2f - (even ? 0f : 8f));
        }else{
            Drawf.dashCircle(x, y, revealRange * tilesize - (even ? 0f : 4f), Pal.placing);
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
            boolean even = size % 2 == 0;
            int evenOffset = even ? 1 : 0; //On an even-sized block, the center tile is the bottom-left of the 4 center tiles.
            for(int x = tile.x - revealRange + 1; x <= tile.x + revealRange - 1 + evenOffset; x++){
                for(int y = tile.y + revealRange - 1 + evenOffset; y >= tile.y - revealRange + 1; y--){
                    Tile t = world.tile(x, y);
                    if(t != null && (squareArea || within(t.worldx(), t.worldy(), revealRange * tilesize - (even ? 0f : 4f))) && t.overlay() instanceof HiddenOreBlock h && h.oreRevealType == revealType && tier >= h.tier){
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
            ore.displayRevealEffect(t);
        }

        public void scheduleBreak(){
            if(!headless) control.input.tryBreakBlock(tile.x, tile.y);
        }
    }
}
