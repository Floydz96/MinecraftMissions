package com.gustgamer29.mc_wrapper.v1_8_R3;

import com.google.common.collect.Sets;
import com.gustgamer29.mc_wrapper.FakePlayerInfo;
import com.gustgamer29.mc_wrapper.Hologram;
import com.gustgamer29.mc_wrapper.NmsVersion;
import com.gustgamer29.text.TextFormatter;
import com.gustgamer29.util.Reflection;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class VersionControl1_8_R3 implements NmsVersion {

    private static VersionControl1_8_R3 instance;
    private static final double DELTA = 0.2;

    private VersionControl1_8_R3(){
    }

    public static VersionControl1_8_R3 getInstance(){
        if(null == instance){
            instance = new VersionControl1_8_R3();
        }
        return instance;
    }

    public void sendActionBar(Player player, String json) {
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
        net.minecraft.server.v1_8_R3.Entity craftEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = craftEntity.getNBTTag();

        if(tag == null)
            tag = new NBTTagCompound();

        craftEntity.c(tag);
        tag.setInt("NoAI", enabled ? 0 : 1);
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
    public Hologram summonHologram(String name, Location location) {
        WorldServer craftWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityHologram1_8_R3 hologram = new EntityHologram1_8_R3(craftWorld);
        hologram.setCustomName(TextFormatter.colorize(name));
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
//        PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();
//
//        Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "h", int.class)
//                .set(packetPlayOutScoreboardTeam, 0);
//        Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "b", String.class)
//                .set(packetPlayOutScoreboardTeam, info.getName());
//        Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "a", String.class)
//                .set(packetPlayOutScoreboardTeam, info.getName());
//        Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "e", String.class)
//                .set(packetPlayOutScoreboardTeam, "never");
//        Reflection.getField(packetPlayOutScoreboardTeam.getClass(), "i", int.class)
//                .set(packetPlayOutScoreboardTeam, 1);
//        Reflection.FieldAccessor<Collection> collectionFieldAccessor = Reflection.getField(
//                packetPlayOutScoreboardTeam.getClass(), "g", Collection.class);
//        collectionFieldAccessor.set(packetPlayOutScoreboardTeam, Collections.singletonList(info.getName()));
//
//        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo();
//        Reflection.getField(packetPlayOutPlayerInfo.getClass(), "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.class)
//                .set(packetPlayOutPlayerInfo, action);
//
//        PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = packetPlayOutPlayerInfo.new PlayerInfoData(gameProfile, 1,
//                WorldSettings.EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.a("{\"text\":\"[NPC] " + name + "\",\"color\":\"dark_gray\"}"));
//
//        Reflection.FieldAccessor<List> fieldAccessor = Reflection.getField(packetPlayOutPlayerInfo.getClass(), "b", List.class);
//        fieldAccessor.set(packetPlayOutPlayerInfo, Collections.singletonList(playerInfoData));
//

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
