package poly.tantros.world.blocks.power;

import mindustry.world.blocks.power.VariableReactor;

public class SteamGenerator extends VariableReactor{
    public SteamGenerator(String name){
        super(name);
        unstableSpeed = -0f;
        explosionRadius = 4;
        explosionDamage = 50;
        explosionPuddles = 0;
        explosionPuddleAmount = 0f;
        explosionPuddleRange = 0f;
    }

    @Override
    public void setBars(){
        super.setBars();
        removeBar("instability");
    }
}
