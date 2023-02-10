package tantros.world.blocks.walls;

import mindustry.Vars;
import mindustry.world.blocks.defense.Wall;

public class ReactiveWalls extends Wall {
    public ReactiveWalls(String name) {
        super(name);
        size = 1;
    }

    public class ReactiveWallBuild extends Wall.WallBuild {
        public void placed(){
            super.placed();
            if(Vars.net.client()) return;
            kill();
        }
    }
}

