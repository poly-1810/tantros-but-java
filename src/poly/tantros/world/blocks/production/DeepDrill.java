package poly.tantros.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.Eachable;
import arc.util.Log;
import arc.util.Scaling;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.*;
import poly.tantros.content.Blocks.*;

public class DeepDrill extends PayloadBlock {
    public DeepDrill(String name) {
        super(name);
        update = true;
        outputsPayload = true;
        rotate = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (DeepDrillBuild e) -> new Bar("bar.loadprogress", Pal.ammo, () -> e.drillTime / 3600));
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, table -> {
            table.row();

            table.table(Styles.grayPanel, t -> {
                t.left();
                t.image(Resources.rubedoBlock.uiIcon).scaling(Scaling.fit).size(32).pad(14).left();
                t.table(info -> {
                    info.defaults().left();
                    info.add(Resources.rubedoBlock.localizedName);
                    info.row();
                    info.add("60 " + Core.bundle.get("unit.seconds")).color(Color.lightGray);
                });
            }).growX().pad(5);
        });
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region, outRegion, topRegion};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(outRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
        Draw.rect(topRegion, plan.drawx(), plan.drawy());
    }

    public class DeepDrillBuild extends PayloadBlock.PayloadBlockBuild<Payload> {
        public float drillTime = 0f;

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return false;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (canConsume()) {
                consume();
                drillTime += buildOn().power.status >= 1f ? 1f : buildOn().power.status;
                Log.info(drillTime);
            }
            if (drillTime >= 3600f) {
                payload = new BuildPayload(Resources.rubedoBlock, team);
                payVector.setZero();
                payRotation = rotdeg();
                dumpPayload(payload);
                drillTime = 0;
            }
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y, 0);
            Draw.rect(outRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y);
            drawPayload();
            Draw.z(Layer.blockBuilding + 1.1f);
            Draw.reset();
        }
    }
}
