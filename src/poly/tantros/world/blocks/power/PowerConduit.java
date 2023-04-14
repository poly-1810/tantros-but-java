package poly.tantros.world.blocks.power;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.input.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class PowerConduit extends PowerBlock {
    public TextureRegion baseRegion;
    public TextureRegion laser;
    public TextureRegion[] sideRegions, laserEnds;

    public float pulseScl = 7, pulseMag = 0.05f;
    public float laserWidth = 0.2f;
    public Block bridgeReplacement;
    
    public PowerConduit(String name) {
        super(name);
        conductivePower = true;
    }

    @Override
    public void init() {
        super.init();

        if(bridgeReplacement == null || !(bridgeReplacement instanceof DirectionalBeamNode)) bridgeReplacement = Power.powerPipe;
    }

    @Override
    public void load() {
        super.load();
        baseRegion = atlas.find(name + "-base");

        sideRegions = new TextureRegion[2];
        for (int i = 0; i < sideRegions.length; i++) {
            sideRegions[i] = atlas.find(name + "-side-" + i);
        }
        
        laser = atlas.find(name + "-beam", "power-beam");
        laserEnds = new TextureRegion[5];
        for (int i = 0; i < laserEnds.length; i++) {
            laserEnds[i] = atlas.find(name + "-beam-end-" + i);
        }
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(baseRegion, plan.drawx(), plan.drawy());

        boolean[] connections = new boolean[4];
        //Copied from Autotiler
        list.each(other -> {
            if (other.breaking || other == plan) return;

            int i = 0;
            for (Point2 point : Geometry.d4) {
                int x = plan.x + point.x, y = plan.y + point.y;
                if (x >= other.x - (other.block.size - 1) / 2 && x <= other.x + (other.block.size / 2) && y >= other.y - (other.block.size - 1) / 2 && y <= other.y + (other.block.size / 2)) {
                    connections[i] = other.block.hasPower;
                }
                i++;
            }
        });

        for (int i = 0; i < connections.length; i++) {
            if (!connections[i]) {
                float rot = i * 90f;
                Draw.rect(sideRegions[i / 2], plan.drawx(), plan.drawy(), rot);
            }
        }

        Draw.rect(region, plan.drawx(), plan.drawy());
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans) {
        if (bridgeReplacement == null) return;

        TPlacement.calculateBridges(plans, (DirectionalBeamNode)bridgeReplacement, b -> b == this || b.hasPower);
    }

    @Override
    protected TextureRegion[] icons() {
        return new TextureRegion[]{baseRegion, atlas.find(name + "-sides"), region};
    }

    public class PowerConduitBuild extends Building {
        public boolean any = false;
        public boolean[] connections = new boolean[4];

        @Override
        public void draw() {
            Draw.rect(baseRegion, x, y);

            if (any) {
                Draw.z(Layer.blockOver);
                float scale = laserWidth + Mathf.absin(pulseScl, pulseMag);
                float scl = 8f * scale * Draw.scl;
                for (int i = 0; i < connections.length; i++) {
                    float rot = i * 90f;
                    if (connections[i]) {
                        boolean node = nearby(i).block == block;
                        float dx = Geometry.d4x(i), dy = Geometry.d4y(i);
                        float vx = dx * scl, vy = dy * scl;
                        float px = dx * size * tilesize / 2f, py = dy * size * tilesize / 2f;

                        Draw.scl(scale);
                        Draw.rect(laserEnds[1], x, y, rot);
                        if(!node) Draw.rect(laserEnds[0], x + px, y + py, rot + 180);
                        Draw.reset();

                        Lines.stroke(12f * scale);
                        Lines.line(laser, x + vx, y + vy, x + px - (node ? 0 : vx), y + py - (node ? 0 : vy), false);
                        Lines.stroke(1f);
                    } else {
                        boolean prev = connections[Mathf.mod(i - 1, 4)],
                            next = connections[(i + 1) % 4];
                        Draw.scl(scale);
                        Draw.rect(laserEnds[prev ? 2 : 3], x, y, rot);
                        Draw.yscl *= -1f;
                        Draw.rect(laserEnds[next ? 2 : 4], x, y, rot);
                        Draw.reset();
                    }
                }
            }

            Draw.z(Layer.blockOver + 0.1f);
            for (int i = 0; i < connections.length; i++) {
                if (!connections[i]) {
                    float rot = i * 90f;
                    Draw.rect(sideRegions[i / 2], x, y, rot);
                }
            }
            Draw.rect(region, x, y);
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            any = false;
            for (int i = 0; i < 4; i++) {
                Building adj = nearby(i);
                boolean connection = adj != null && adj.block.hasPower;
                connections[i] = connection;
                if (connection) any = true;
            }
        }
    }
}
