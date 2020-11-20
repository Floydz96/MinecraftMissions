package com.gustgamer29.mc_wrapper.v1_8_R3;

import com.gustgamer29.mc_wrapper.Hologram;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.World;

public class EntityHologram1_8_R3 extends EntityArmorStand implements Hologram {

    public EntityHologram1_8_R3(World world) {
        super(world);
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setGravity(true);
        setBasePlate(true);

        try {
            n(this);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        a(new ZeroBoundingBox1_8_R3());
    }

    @Override
    public void summon(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public void destroy() {
        super.dead = true;
        super.damageEntity(DamageSource.GENERIC, 10000f);
    }

    @Override
    public void makeSound(String s, float f, float f1) {
        
    }
}
