package com.gustgamer29.mc_wrapper.v1_8_R2;

import com.google.common.collect.Sets;
import com.gustgamer29.mc_wrapper.FakePlayerInfo;
import com.gustgamer29.mc_wrapper.Hologram;
import com.gustgamer29.mc_wrapper.NmsVersion;
import com.gustgamer29.text.TextFormatter;
import net.minecraft.server.v1_8_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Set;

public class VersionControl1_8_R2 implements NmsVersion {

    private static VersionControl1_8_R2 instance;
    private static final double DELTA = 0.3;


    private VersionControl1_8_R2(){
    }

    public static VersionControl1_8_R2 getInstance(){
        if(null == instance){
            instance = new VersionControl1_8_R2();
        }
        return instance;
    }

    public void sendActionBar(Player player, String json) {
        assert player != null && json != null;
        try {
            IChatBaseComponent baseComponent = IChatBaseComponent.ChatSerializer.a(json);
            PacketPlayOutChat cht = new PacketPlayOutChat(baseComponent, (byte)2);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(cht);
        }catch (Throwable ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void setEntityAi(Entity entity, boolean enabled) {
        net.minecraft.server.v1_8_R2.Entity craftEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = craftEntity.getNBTTag();
        craftEntity.c(tag);

        tag.setInt("AIUtils", enabled ? 0 : 1);
        craftEntity.f(tag);
    }

    @Override
    public void disconnectPlayer(Player player, String reason) {
        try {
        PlayerConnection pc = ((CraftPlayer)player).getHandle().playerConnection;
        pc.disconnect(reason);
        }catch (Exception ex){
            ex. printStackTrace();
        }
    }

    @Override
    public Hologram summonHologram(String s, Location location) {
        WorldServer craftWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityHologram1_8_R2 hologram = new EntityHologram1_8_R2(craftWorld);
        hologram.setCustomName(TextFormatter.colorize(s));
        hologram.setCustomNameVisible(true);
        hologram.summon(location.getX(), location.getY(), location.getZ());
        craftWorld.addEntity(hologram, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return hologram;
    }

    @Override
    public Set<Hologram> summonHologram(List<String> text, Location location) {
        Set<Hologram> entities = Sets.newHashSet();
        Location firstLine = location.clone().add(0, DELTA * text.size(), 0);
        entities.add(this.summonHologram(text.get(0), firstLine));

        if(text.size() == 1)
            return entities;

        for(int i = 1; i < text.size(); i ++){
            entities.add(this.summonHologram(text.get(i), firstLine.subtract(0, DELTA, 0)));
        }
        return entities;
    }

    @Override
    public Entity summonFakePlayer(Player player, FakePlayerInfo info) {
        return null;
    }

    @Override
    public Entity summonFakePlayer(FakePlayerInfo info) {

        return null;
    }

    public void sendTitle(Player player, String json, int fadein, int show, int fadeout){
        try{
            PacketPlayOutTitle cht = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(json), fadein, show, fadeout);
            PlayerConnection pc = ((CraftPlayer)player).getHandle().playerConnection;
            pc.sendPacket(cht);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void sendSubTitle(Player player, String json, int fadein, int show, int fadeout) {
        try {
            PacketPlayOutTitle ppot = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a(json), fadein, show, fadeout);
            PlayerConnection pc = ((CraftPlayer) player).getHandle().playerConnection;
            pc.sendPacket(ppot);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendTitle(Player player, String t1, String t2, int fadein, int show, int fadeout) {
        try {
            PacketPlayOutTitle cht = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(t1), fadein, show, fadeout);
            PacketPlayOutTitle ppot = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a(t2), fadein, show, fadeout);
            PlayerConnection pc = ((CraftPlayer) player).getHandle().playerConnection;
            pc.sendPacket(cht);
            pc.sendPacket(ppot);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
