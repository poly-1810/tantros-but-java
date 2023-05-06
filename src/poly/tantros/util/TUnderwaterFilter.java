package poly.tantros.util;

import arc.*;
import arc.audio.*;
import arc.audio.Filters.*;
import arc.util.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class TUnderwaterFilter{
    protected Interval timer = new Interval(1);
    protected boolean wasPlaying;
    /** 3 overlaps with BetaMindy's wet filter. Hopefully doesn't cause problems. */
    protected int filterIndex = 3;
    protected AudioFilter wetFilter = new BiquadFilter(){{
        set(0, 500, 1);
    }};

    public TUnderwaterFilter(){
        setupFilters();
    }

    public void update(){
        boolean playing = state.isGame();

        //poll every 30 ticks just in case performance is an issue
        if(timer.get(0, 30f)){
            Core.audio.fadeFilterParam(0, filterIndex, Filters.paramWet, playing && (Env.underwater & state.rules.env) == Env.underwater ? 1f : 0f, 0.4f);
        }

        if(playing != wasPlaying){
            wasPlaying = playing;
            if(playing){
                setupFilters();
            }
        }
    }

    protected void setupFilters(){
        Core.audio.setFilter(filterIndex, wetFilter);
        Core.audio.setFilterParam(0, filterIndex, Filters.paramWet, 0f);
    }
}
