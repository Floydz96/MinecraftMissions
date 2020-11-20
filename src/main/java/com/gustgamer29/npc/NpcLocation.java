package com.gustgamer29.npc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter @Setter
@RequiredArgsConstructor
public class NpcLocation {

    private final String world;
    private final double x, y, z;
    private final float yaw, pitch;

    public static NpcLocation from(Location location){

        double x, y, z;
        float yaw, pitch;
        String world;

        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = 0f;//location.getPitch();
        world = location.getWorld().getName();

        return new NpcLocation(world, x, y, z, yaw, pitch);
    }

    public static Location of(NpcLocation loc){

        double x, y, z;
        float yaw, pitch;
        String world;

        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        yaw = loc.getYaw();
        pitch = loc.getPitch();

        world = loc.getWorld();

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
