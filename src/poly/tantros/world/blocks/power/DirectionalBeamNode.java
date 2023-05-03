package poly.tantros.world.blocks.power;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class DirectionalBeamNode extends PowerBlock {
    public int range = 5;
    public Color laserColor1 = Color.white;
    public Color laserColor2 = Color.valueOf("ffd9c2");
    public float pulseScl = 7, pulseMag = 0.05f;
    public float laserWidth = 0.4f;
    
    public TextureRegion dirRegion, beam, beamEnd;
    
    public DirectionalBeamNode(String name) {
        super(name);
        rotate = true;
        rotateDraw = false;
        consumesPower = outputsPower = false;
        drawDisabled = false;
        envEnabled |= Env.space;
        allowDiagonal = false;
        underBullets = true;
        priority = TargetPriority.transport;
    }

    @Override
    public void init() {
        super.init();

        updateClipRadius((range + 1));
    }

    @Override
    public void load() {
        super.load();

        dirRegion = Core.atlas.find(name + "-dir");
        beam = Core.atlas.find(name + "-beam", "power-beam");
        beamEnd = Core.atlas.find(name + "-beam-end", "power-beam-end");
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, dirRegion};
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("power", PowerNode.makePowerBalance());
        addBar("batteries", PowerNode.makeBatteryBalance());
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.powerRange, range, StatUnit.blocks);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(dirRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        drawLine(x, y, rotation);
    }

    public void drawLine(int x, int y, int rotation) {
        int maxLen = range + size/2;
        Building dest = null;
        var dir = Geometry.d4[rotation];
        int dx = dir.x, dy = dir.y;
        int offset = size/2;
        for (int j = 1 + offset; j <= range + offset; j++) {
            var other = world.build(x + j * dir.x, y + j * dir.y);

            // hit insulated wall
            if (other != null && other.isInsulated()) {
                break;
            }

            if (other != null && other.block.hasPower && other.team == Vars.player.team() && !(other.block instanceof PowerNode)) {
                maxLen = j;
                dest = other;
                break;
            }
        }

        Drawf.dashLine(Pal.placing,
            x * tilesize + dx * (tilesize * size / 2f + 2),
            y * tilesize + dy * (tilesize * size / 2f + 2),
            x * tilesize + dx * (maxLen) * tilesize,
            y * tilesize + dy * (maxLen) * tilesize
        );

        if (dest != null) {
            Drawf.square(dest.x, dest.y, dest.block.size * tilesize/2f + 2.5f, 0f);
        }
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation) {
        Placement.calculateNodes(points, this, rotation, (point, other) -> Math.max(Math.abs(point.x - other.x), Math.abs(point.y - other.y)) <= range);
    }

    public boolean positionsValid(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) <= range;
        } else if (y1 == y2) {
            return Math.abs(x1 - x2) <= range;
        } else {
            return false;
        }
    }
    
    public class DirectionalBeamNodeBuild extends Building {
        public Building link;
        public Tile dest;
        public int lastChange = -2;

        @Override
        public void updateTile() {
            if (lastChange != world.tileChanges) {
                lastChange = world.tileChanges;
                findLink();
            }
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            findLink();
        }

        @Override
        public BlockStatus status() {
            if (Mathf.equal(power.status, 0f, 0.001f)) return BlockStatus.noInput;
            if (Mathf.equal(power.status, 1f, 0.001f)) return BlockStatus.active;
            return BlockStatus.noOutput;
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.rect(dirRegion, x, y, rotdeg());

            if (Mathf.zero(Renderer.laserOpacity)) return;
            if (dest != null && link.wasVisible && (!(link.block instanceof BeamNode node) ||
                (link.tileX() != tileX() && link.tileY() != tileY()) ||
                (link.id > id && range >= node.range) || range > node.range)) {

                Draw.z(Layer.power);
                Draw.color(laserColor1, laserColor2, (1f - power.graph.getSatisfaction()) * 0.86f + Mathf.absin(3f, 0.1f));
                Draw.alpha(Renderer.laserOpacity);
                float w = laserWidth + Mathf.absin(pulseScl, pulseMag);

                int dst = Math.max(Math.abs(dest.x - tile.x),  Math.abs(dest.y - tile.y));
                // don't draw lasers for adjacent blocks
                if (dst > 1 + size/2) {
                    var point = Geometry.d4[rotation];
                    float poff = tilesize/2f;
                    Drawf.laser(beam, beamEnd, x + poff*size*point.x, y + poff*size*point.y, dest.worldx() - poff*point.x, dest.worldy() - poff*point.y, w);
                }
            }
        }

        @Override
        public void drawSelect() {
            drawLine(tile.x, tile.y, rotation);
        }

        @Override
        public void pickedUp() {
            link = null;
            dest = null;
        }

        public void findLink() {
            var prev = link;
            var dir = Geometry.d4[rotation];
            link = null;
            dest = null;
            int offset = size/2;
            // find first block with power in range
            for (int j = 1 + offset; j <= range + offset; j++) {
                var other = world.build(tile.x + j * dir.x, tile.y + j * dir.y);

                // hit insulated wall
                if (other != null && other.isInsulated()) {
                    break;
                }

                // power nodes do NOT play nice with beam nodes, do not touch them as that forcefully modifies their links
                if (other != null && other.block.hasPower && other.block.connectedPower && other.team == team && !(other.block instanceof PowerNode)) {
                    link = other;
                    dest = world.tile(tile.x + j * dir.x, tile.y + j * dir.y);
                    break;
                }
            }

            var next = link;

            if (next != prev) {
                // unlinked, disconnect and reflow
                if (prev != null) {
                    prev.power.links.removeValue(pos());
                    power.links.removeValue(prev.pos());

                    PowerGraph newgraph = new PowerGraph();
                    // reflow from this point, covering all tiles on this side
                    newgraph.reflow(this);

                    if (prev.power.graph != newgraph) {
                        // reflow power for other end
                        PowerGraph og = new PowerGraph();
                        og.reflow(prev);
                    }
                }

                // linked to a new one, connect graphs
                if (next != null) {
                    power.links.addUnique(next.pos());
                    next.power.links.addUnique(pos());

                    power.graph.addGraph(next.power.graph);
                }
            }
        }
    }
}
