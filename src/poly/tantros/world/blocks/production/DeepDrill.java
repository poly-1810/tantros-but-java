package poly.tantros.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.*;
import poly.tantros.content.Blocks.*;
import poly.tantros.content.*;

public class DeepDrill extends PayloadBlock {
    private int drawTiles = 0; // only for drawPlace

    public DeepDrill(String name) {
        super(name);
        outputsPayload = true;
        rotate = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (DeepDrillBuild e) -> new Bar("bar.loadprogress", Pal.accent, () -> e.drillTime / 3600f));
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, table -> {
            table.row();

            table.table(Styles.grayPanel, t -> {
                t.left();
                t.image(Resources.tCopperBlock.uiIcon).scaling(Scaling.fit).size(32).pad(14).left();
                t.table(info -> {
                    info.defaults().left();
                    info.add(Resources.tCopperBlock.localizedName);
                    info.row();
                    info.add("60 " + Core.bundle.get("unit.seconds")).color(Color.lightGray);
                });
            }).growX().pad(5);
        });
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        drawTiles = 0;

        if (isMultiblock()) {
            for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                if (other.drop() == TItems.tCopper) drawTiles++;
            }
        } else {
            if (tile.drop() == TItems.tCopper) drawTiles = 1;
        }

        return drawTiles >= 1;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile tile = Vars.world.tile(x, y);
        if (tile == null) return;

        boolean ore = false;

        if (isMultiblock()) {
            for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                if (other.drop() == TItems.tCopper) ore = true;
                if (ore) break;
            }
        } else {
            if (tile.drop() == TItems.tCopper) ore = true;
        }

        if (ore) {
            float width = drawPlaceText(Core.bundle.formatFloat("bar.efficiency", ((float) drawTiles / (size * size)) * 100f, 2), x, y, valid);
            float dx = x * Vars.tilesize + offset - width / 2f - 4f, dy = y * Vars.tilesize + offset + size * Vars.tilesize / 2f + 5, s = Vars.iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(Resources.tCopperBlock.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(Resources.tCopperBlock.fullIcon, dx, dy, s, s);
        }
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
        public int tiles = 0;

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return false;
        }

        @Override
        public void placed() {
            super.placed();

            if (isMultiblock()) {
                for (Tile other : tile.getLinkedTilesAs(this.block, tempTiles)) {
                    if (other.drop() == TItems.tCopper) tiles++;
                }
            } else {
                if (tile.drop() == TItems.tCopper) tiles = 1;
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (canConsume()) {
                consume();
                drillTime += buildOn().power.status * ((float) tiles / (size * size));
            }
            if (drillTime >= 3600f) {
                payload = new BuildPayload(Resources.tCopperBlock, team);
                payVector.setZero();
                payRotation = rotdeg();
                drillTime = 0;
            }

            if (payload != null) moveOutPayload();
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
