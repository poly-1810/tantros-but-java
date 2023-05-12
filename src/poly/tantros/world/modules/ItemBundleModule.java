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
import poly.tantros.world.blocks.core.*;

import static mindustry.Vars.*;

public class ItemBundleModule extends BlockModule{
    /** Vector used for movement. */
    protected static final Vec2 mVec = new Vec2();

    protected final Seq<ItemBundle> itemBundles = new Seq<>();

    public void update(Building building, float speed){
        for(int i = 0; i < itemBundles.size; i++){
            ItemBundle bundle = itemBundles.get(i);
            mVec.set(bundle.x, bundle.y);

            if(bundle.entering){ //Move in
                mVec.approachDelta(Tmp.v1.set(building), speed);

                if(mVec.within(building, 0.01f)){
                    bundle.entering = false;

                    if(world.build(bundle.destination) == building){
                        building.handleStack(bundle.stack.item, bundle.stack.amount, building);
                        itemBundles.remove(bundle);
                        i--;
                    }
                }
            }else{ //Move out
                Building next = world.build(bundle.path.last());
                if(next instanceof ItemBundleMover m){
                    float ang = building.angleTo(next);
                    float calc = 1f + (1f - Mathf.sinDeg(Mathf.mod(ang + 45f, 90f) * 2)) * (Mathf.sqrt2 - 1f);
                    Vec2 dest = Tmp.v1.trns(ang, (building.block.size * tilesize / Mathf.sqrt2) * calc).add(building);

                    mVec.approachDelta(dest, speed);

                    if(mVec.within(dest, 0.001f)){
                        m.itemBundleModule().itemBundles.add(bundle);
                        bundle.entering = true;
                        bundle.path.removeLast();
                        itemBundles.remove(bundle);
                        i--;
                    }
                }else{
                    itemBundles.remove(bundle);
                    i--;
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

        public float x, y;
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

            float size = (itemSize + Mathf.absin(Time.time, 5f, 1f));
            Draw.mixcol(Pal.accent, Mathf.absin(Time.time, 5f, 0.1f));
            Draw.rect(stack.item.fullIcon, x, y, size, size, 0);
            Draw.mixcol();

            size = ((3f + Mathf.absin(Time.time, 5f, 1f)) + 0.5f) * 2;
            Draw.color(Pal.accent);
            Draw.rect(itemCircleRegion, x, y, size, size);
        }

        public void write(Writes write){
            write.f(x);
            write.f(y);
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
            boolean entering = read.bool();
            ItemStack items = TypeIO.readItems(read);

            int length = read.i();
            IntQueue journey = new IntQueue();
            for(int i = 0; i < length; i++){
                journey.addFirst(read.i());
            }

            ItemBundle out = new ItemBundle(x, y, items, journey);
            out.entering = entering;

            return out;
        }
    }
}
