package poly.tantros.world.modules;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.type.*;
import mindustry.world.modules.*;
import poly.tantros.content.*;
import poly.tantros.world.blocks.core.*;

import static mindustry.Vars.*;

public class ItemBundleModule extends BlockModule{
    /** Vector used for movement. */
    protected static final Vec2 mVec = new Vec2();

    protected final Seq<ItemBundle> itemBundles = new Seq<>();

    public void update(ItemBundleMover mover, float speed){
        Building building = mover.building();
        for(int i = 0; i < itemBundles.size; i++){
            ItemBundle bundle = itemBundles.get(i);
            mVec.set(bundle.x, bundle.y);

            if(bundle.entering){ //Move in
                boolean atDest = world.build(bundle.destination) == building;
                mVec.approachDelta(Tmp.v1.set(building), speed);

                if(mVec.within(building, 0.01f)){
                    bundle.entering = false;

                    if(atDest){
                        mover.bundleArrived(bundle);
                        bundle.scale = 0;
                        itemBundles.remove(bundle);
                        i--;
                    }
                }

                if(atDest && bundle.scale > 0 && mover.arriveShrink()){
                    float ang = building.angleTo(mVec);
                    float calc = 1f + (1f - Mathf.sinDeg(Mathf.mod(ang + 45f, 90f) * 2)) * (Mathf.sqrt2 - 1f);
                    Vec2 from = Tmp.v1.trns(ang, building.block.size * tilesize / 2f * calc).add(building);
                    bundle.scale = Mathf.clamp(building.dst(mVec) / building.dst(from));
                }
            }else{ //Move out
                Building next = world.build(bundle.path.last());
                if(!(next instanceof ItemBundleMover)){
                    itemBundles.remove(bundle);
                    bundle.destroyed();
                    i--;
                    continue;
                }

                float ang = building.angleTo(next);
                float calc = 1f + (1f - Mathf.sinDeg(Mathf.mod(ang + 45f, 90f) * 2)) * (Mathf.sqrt2 - 1f);
                Vec2 dest = Tmp.v1.trns(ang, building.block.size * tilesize / 2f * calc).add(building);

                mVec.approachDelta(dest, speed);

                if(mVec.within(dest, 0.001f)){
                    bundle.entering = true;
                    bundle.path.removeLast();
                    itemBundles.remove(bundle);
                    i--;

                    ((ItemBundleMover)next).itemBundleModule().itemBundles.add(bundle);
                    bundle.scale = 1f;
                }

                if(bundle.scale < 1){
                    bundle.scale = Mathf.clamp(building.dst(mVec) / building.dst(dest));
                }
            }
            bundle.x = mVec.x;
            bundle.y = mVec.y;
        }
    }

    public void draw(){
        itemBundles.each(ItemBundle::draw);
    }

    public void clear(){
        itemBundles.each(ItemBundle::destroyed);
        itemBundles.clear();
    }

    public ItemBundle addBundle(ItemBundle bundle){
        itemBundles.add(bundle);
        return bundle;
    }

    public ItemBundle addBundle(float x, float y, ItemStack itemStack, IntQueue journey){
        ItemBundle bundle = new ItemBundle(x, y, itemStack, journey);
        return addBundle(bundle);
    }

    public ItemBundle addBundle(float x, float y, Item item, int amount, IntQueue journey){
        ItemBundle bundle = new ItemBundle(x, y, item, amount, journey);
        return addBundle(bundle);
    }

    public Seq<ItemBundle> getItemBundles(){
        return itemBundles;
    }

    @Override
    public void write(Writes write){
        write.i(itemBundles.size);
        for(ItemBundle bundle : itemBundles){
            bundle.write(write);
        }
    }

    @Override
    public void read(Reads read){
        int bundles = read.i();
        for(int i = 0; i < bundles; i++){
            itemBundles.add(ItemBundle.read(read));
        }
    }

    public static class ItemBundle{
        protected static TextureRegion itemCircleRegion;

        public float x, y, scale;
        public boolean entering;
        public ItemStack stack;
        public IntQueue path;
        public int destination;

        public ItemBundle(float x, float y, ItemStack stack, IntQueue path){
            this.x = x;
            this.y = y;
            this.stack = stack;
            this.path = path;
            destination = path.first();
        }

        public ItemBundle(float x, float y, Item item, int amount, IntQueue path){
            this(x, y, new ItemStack(item, amount), path);
        }

        public void draw(){
            if(itemCircleRegion == null) itemCircleRegion = Core.atlas.find("ring-item");

            float size = (itemSize + Mathf.absin(Time.time, 5f, 1f)) * scale;
            Draw.mixcol(Pal.accent, Mathf.absin(Time.time, 5f, 0.1f));
            Draw.rect(stack.item.fullIcon, x, y, size, size, 0);
            Draw.mixcol();

            size = ((3f + Mathf.absin(Time.time, 5f, 1f)) + 0.5f) * 2 * scale;
            Draw.color(Pal.accent);
            Draw.rect(itemCircleRegion, x, y, size, size);
        }

        public void destroyed(){
            TFx.bundleBurst.at(x, y, stack.amount, stack.item);
        }

        public void write(Writes write){
            write.f(x);
            write.f(y);
            write.f(scale);
            write.bool(entering);
            TypeIO.writeItems(write, stack);

            write.i(path.size);
            for(int i = 0; i < path.size; i++){
                write.i(path.removeLast());
            }
        }

        public static ItemBundle read(Reads read){
            float x = read.f();
            float y = read.f();
            float scale = read.f();
            boolean entering = read.bool();
            ItemStack items = TypeIO.readItems(read);

            int length = read.i();
            IntQueue journey = new IntQueue();
            for(int i = 0; i < length; i++){
                journey.addFirst(read.i());
            }

            ItemBundle out = new ItemBundle(x, y, items, journey);
            out.entering = entering;
            out.scale = scale;

            return out;
        }
    }
}
