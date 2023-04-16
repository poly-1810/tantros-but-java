package poly.tantros.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.content.*;

public class DeepDrill extends BlockProducer {
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

    public class DeepDrillBuild extends BlockProducer.BlockProducerBuild {
        public float drillTime = 0f;

        @Nullable
        @Override
        public Block recipe(){
            return Resources.rubedoBlock;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (canConsume()) {
                consume();
                drillTime += this.power.status >= 1f ? 1f : this.power.status;
            }
            if (drillTime > 0) items.set(TItems.rubedo, Math.round(drillTime / 150));
            if (drillTime >= 3600f) {
                drillTime = 0;
                items.clear();
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
