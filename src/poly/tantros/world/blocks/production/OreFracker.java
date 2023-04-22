package poly.tantros.world.blocks.production;

import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import poly.tantros.content.*;
import poly.tantros.graphics.*;
import poly.tantros.world.blocks.environment.HiddenOreBlock.*;

import static mindustry.Vars.*;

public class OreFracker extends OreRevealer {
    protected static final Rand crackRand = new Rand();

    public float reloadTime = 4f * 60f;
    public float warmupSpeed = 0.019f;
    public int blasts = 4;

    public int cracks = 8;
    public float crackAngRnd = 30f;
    public float minCrackSegRange = 4f, maxCrackSegRange = 8f;
    public float minCrackDst = -1, maxCrackDist = -1;
    public float minCrackStroke = 3f, maxCrackStroke = 7f;
    public Color crackColor = TPal.darkBrown;

    public float shake = 4f;
    public Sound blastSound = Sounds.drillImpact;
    public Effect blastEffect = new MultiEffect( //TODO placeholder-this is just a copy of eruption drill's mine effect
        Fx.mineImpact,
        Fx.drillSteam,
        Fx.dynamicSpikes.wrap(Pal.bulletYellowBack, 30f),
        Fx.mineImpactWave.wrap(Pal.bulletYellowBack, 45f)
    );
    public float blastSoundVolume = 1f, blastSoundPitchRand = 0.1f;

    public OreFracker(String name) {
        super(name);
        revealType = OreRevealType.fracker;
        revealRange = -1;
        squareArea = true;
    }

    @Override
    public void init() {
        super.init();
        if (revealRange < 0) revealRange = size / 2 - 1 + size % 2;
        updateClipRadius(revealRange * tilesize);

        if (maxCrackDist < 0) maxCrackDist = revealRange * tilesize * 1.1f;
        if (minCrackDst < 0) minCrackDst = maxCrackDist * 0.75f;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("progress", (OreFrackerBuild entity) -> new Bar(
            "bar.loadprogress",
            Pal.accent,
            () -> (float)entity.blastCounter / blasts
        ));
    }

    public class OreFrackerBuild extends OreRevealerBuild {
        public float reloadCounter, totalProgress, warmup;
        public int blastCounter;
        public GroundCrack[] groundCracks;

        @Override
        public void draw() {
            super.draw();

            Draw.z(Layer.scorch - 1f);

            if (blastCounter < 1 || blastCounter >= blasts) return;

            if (groundCracks == null) {
                groundCracks = new GroundCrack[cracks];
                crackRand.setSeed(id + tile.pos());
                for (int i = 0; i < cracks; i++) {
                    float dst = crackRand.random(minCrackDst, maxCrackDist),
                        ang = ((float)i / cracks) * 360f + crackRand.range(crackAngRnd / 2f),
                        range = crackRand.random(minCrackSegRange, maxCrackSegRange),
                        width = crackRand.random(minCrackStroke, maxCrackStroke);

                    groundCracks[i] = new GroundCrack(dst, ang, range, width);
                }
            }

            float blastf = (float)blastCounter / blasts;
            for (GroundCrack crack : groundCracks) {
                crack.draw(x, y, crackColor, blastf);
            }
        }

        @Override
        public void updateTile() {
            if (efficiency > 0 && reloadCounter < 1) {
                reloadCounter += getProgressIncrease(reloadTime);
                warmup = Mathf.approachDelta(warmup, 1f, warmupSpeed);
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if (reloadCounter >= 1f) {
                reloadCounter %= 1f;
                blastCounter++;
                consume();
                Effect.shake(shake, shake, this);
                blastSound.at(x, y, 1f + Mathf.range(blastSoundPitchRand), blastSoundVolume);
                blastEffect.at(x, y);

                if (blastCounter >= blasts) {
                    revealOres();
                    scheduleBreak();
                    fadeCracks();
                    reloadCounter = 0f;
                }
            }
        }

        @Override
        public float warmup(){
            return warmup;
        }

        @Override
        public float progress(){
            return reloadCounter;
        }

        @Override
        public float totalProgress(){
            return totalProgress;
        }

        @Override
        public boolean shouldConsume() {
            return blastCounter < blasts;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return super.acceptItem(source, item) && shouldConsume();
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return super.acceptLiquid(source, liquid) && shouldConsume();
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            fadeCracks();
        }

        @Override
        public void afterPickedUp() {
            super.afterPickedUp();
            groundCracks = null;
            blastCounter = 0;
            reloadCounter = 0;
        }

        public void fadeCracks() {
            if (groundCracks == null) return;

            float blastf = (float)blastCounter / blasts;
            for (GroundCrack crack : groundCracks) {
                TFx.groundCrackFade.at(x, y, blastf, crackColor, crack.copy());
            }

            groundCracks = null;
        }
    }
}
