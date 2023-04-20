package poly.tantros.world.draw;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class DrawParts extends DrawBlock {
    public Seq<DrawPart> parts = new Seq<>();
    public String previewName = "";
    public float totalProgressScl = 60f;

    public DrawParts(String previewName, DrawPart... parts) {
        this.previewName = previewName;
        this.parts.addAll(parts);
    }

    public DrawParts(String previewName) {
        this.previewName = previewName;
    }

    public DrawParts() {}

    @Override
    public void getRegionsToOutline(Block block, Seq<TextureRegion> out) {
        for (DrawPart part : parts) {
            part.getOutlines(out);
        }
    }

    @Override
    public void draw(Building build) {
        var params = DrawPart.params.set(build.warmup(), build.progress(), build.totalProgress() / totalProgressScl % 1f, 0f, 0f, 0f, build.x, build.y, 0);

        for (DrawPart part : parts) {
            part.draw(params);
        }
    }

    @Override
    public void load(Block block) {
        for (DrawPart part : parts) {
            part.load(block.name);
        }
    }

    @Override
    public TextureRegion[] icons(Block block) {
        return new TextureRegion[]{Core.atlas.find(block.name + previewName)};
    }
}
