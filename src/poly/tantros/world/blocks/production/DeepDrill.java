package poly.tantros.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.Scaling;
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

    public class DeepDrillBuild extends PayloadBlock.PayloadBlockBuild<Payload> {
        public float drillTime;

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return false;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            payload = new BuildPayload(Resources.rubedoBlock, team);
            if (canConsume()) {
                drillTime += 1;
            }
            if (drillTime >= 3600) {
                payVector.setZero();
                payRotation = rotdeg();
                dumpPayload(payload);
                drillTime = 0;
            }
        }

        @Override
        public void draw() {
            Draw.rect(region, x, y);
            Draw.rect(outRegion, x, y, rotdeg());
            Draw.rect(topRegion, x, y);
            drawPayload();
            Draw.reset();
        }
    }
}
