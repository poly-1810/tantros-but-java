package poly.tantros.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.*;
import poly.tantros.world.blocks.resources.*;
import poly.tantros.world.meta.*;

public class DeepDrill extends PayloadBlock {
    protected @Nullable Item returnItem;
    protected int returnCount;
    protected final ObjectIntMap<Item> oreCount = new ObjectIntMap<>();
    protected final Seq<Item> itemArray = new Seq<>();

    public Seq<ResourceBlock> allowedBlocks = new Seq<>();

    public DeepDrill(String name) {
        super(name);
        outputsPayload = true;
        rotate = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("progress", (DeepDrillBuild e) -> new Bar("bar.loadprogress", Pal.accent, () -> e.drillTime / (e.blockB.drillTime * 60f)));
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, TStatValues.blockList(allowedBlocks));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (isMultiblock()) {
            for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
                if (canMine(other)) {
                    return true;
                }
            }
            return false;
        } else {
            return canMine(tile);
        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile tile = Vars.world.tile(x, y);
        if (tile == null) return;

        countOre(tile);

        if (returnItem != null) {
            float width = drawPlaceText(Core.bundle.formatFloat("bar.efficiency", ((float) returnCount / (size * size)) * 100f, 2), x, y, valid);
            float dx = x * Vars.tilesize + offset - width / 2f - 4f, dy = y * Vars.tilesize + offset + size * Vars.tilesize / 2f + 5, s = Vars.iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(blockByItem(returnItem).fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(blockByItem(returnItem).fullIcon, dx, dy, s, s);
        }
    }

    public boolean canMine(Tile tile) {
        if (tile == null || tile.block().isStatic()) return false;
        for (ResourceBlock block : allowedBlocks) {
            if (tile.drop() == block.item) return true;
        }
        return false;
    }

    public ResourceBlock blockByItem(Item item) {
        for (ResourceBlock block : allowedBlocks) {
            if (block.item == item) return block;
        }
        return null;
    }

    protected void countOre(Tile tile) {
        returnItem = null;
        returnCount = 0;

        oreCount.clear();
        itemArray.clear();

        for (Tile other : tile.getLinkedTilesAs(this, tempTiles)) {
            if (canMine(other)) {
                oreCount.increment(other.drop(), 0, 1);
            }
        }

        for (Item item : oreCount.keys()) {
            itemArray.add(item);
        }

        itemArray.sort((item1, item2) -> {
            int type = Boolean.compare(!item1.lowPriority, !item2.lowPriority);
            if (type != 0) return type;
            int amounts = Integer.compare(oreCount.get(item1, 0), oreCount.get(item2, 0));
            if (amounts != 0) return amounts;
            return Integer.compare(item1.id, item2.id);
        });

        if (itemArray.size == 0) {
            return;
        }

        returnItem = itemArray.peek();
        returnCount = oreCount.get(itemArray.peek(), 0);
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
        public ResourceBlock blockB;
        protected @Nullable Item returnItemB;
        protected int returnCountB;
        protected final ObjectIntMap<Item> oreCountB = new ObjectIntMap<>();
        protected final Seq<Item> itemArrayB = new Seq<>();

        protected void countOreB(Tile tile) {
            returnItemB = null;
            returnCountB = 0;

            oreCountB.clear();
            itemArrayB.clear();

            for (Tile other : tile.getLinkedTilesAs(this.block, tempTiles)) {
                if (canMine(other)) {
                    oreCountB.increment(other.drop(), 0, 1);
                }
            }

            for (Item item : oreCountB.keys()) {
                itemArrayB.add(item);
            }

            itemArrayB.sort((item1, item2) -> {
                int type = Boolean.compare(!item1.lowPriority, !item2.lowPriority);
                if (type != 0) return type;
                int amounts = Integer.compare(oreCountB.get(item1, 0), oreCountB.get(item2, 0));
                if (amounts != 0) return amounts;
                return Integer.compare(item1.id, item2.id);
            });

            if (itemArrayB.size == 0) {
                return;
            }

            returnItemB = itemArrayB.peek();
            returnCountB = oreCountB.get(itemArrayB.peek(), 0);
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            return false;
        }

        @Override
        public void placed() {
            super.placed();

            countOreB(this.tile);
            blockB = blockByItem(returnItemB);
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (canConsume()) {
                consume();
                drillTime += buildOn().power.status * ((float) returnCountB / (size * size));
            }
            if (drillTime >= blockB.drillTime * 60) {
                payload = new BuildPayload(blockB, team);
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
            drawPayload();
            Draw.rect(topRegion, x, y);
        }
    }
}
