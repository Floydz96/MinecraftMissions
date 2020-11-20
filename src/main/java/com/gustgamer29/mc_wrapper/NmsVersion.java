package com.gustgamer29.mc_wrapper;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface NmsVersion {

    void sendTitle(Player player, String json, int fadein, int show, int fadeout);
    void sendSubTitle(Player player, String json, int fadein, int show, int fadeout);
    void sendTitle(Player player, String t1, String t2, int fadein, int show, int fadeout);
    void sendActionBar(Player player, String messageInJson);
    void disconnectPlayer(Player player, String reason);
    void setEntityAi(Entity entity, boolean enabled);
    Hologram summonHologram(String text, Location location);
    Set<Hologram> summonHologram(List<String> text, Location location);
    Entity summonFakePlayer(Player player, FakePlayerInfo info);
    Entity summonFakePlayer(FakePlayerInfo info);
}
