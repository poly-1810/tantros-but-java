package tantros.world.blocks.walls;

import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.bullet.ExplosionBulletType;
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

            new ExplosionBulletType(){{
                hitEffect = Fx.none;
                despawnEffect = Fx.none;
                splashDamageRadius = 1;
                splashDamage = 99999;
            }};
        }
    }
}

