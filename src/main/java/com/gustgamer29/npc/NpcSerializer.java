package com.gustgamer29.npc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gustgamer29.npc.entity.NpcFactory;
import com.gustgamer29.util.MathUtils;

public class NpcSerializer {

    public static JsonElement serialize(INPC npc){
        JsonObject firstBlock = new JsonObject();
        JsonObject location = new JsonObject();

        firstBlock.addProperty("id", npc.getId());
        firstBlock.addProperty("name", npc.getName());
        firstBlock.addProperty("type", npc.getType().name());

        double x, y, z;
        float yaw, pitch;
        NpcLocation loc = npc.getLocation();

        x = MathUtils.round(loc.getX(), 2);
        y = MathUtils.round(loc.getY(), 2);
        z = MathUtils.round(loc.getZ(), 2);
        yaw = (float) MathUtils.round(loc.getYaw(), 3);
        pitch = (float) MathUtils.round(loc.getPitch(), 3);


        location.addProperty("x", x);
        location.addProperty("y", y);
        location.addProperty("z", z);
        location.addProperty("yaw", yaw);
        location.addProperty("pitch", pitch);
        location.addProperty("world", npc.getLocation().getWorld());

        firstBlock.add("location", location);

        return firstBlock;
    }

    public static INPC deserialize(JsonElement serializedNpc){
       JsonObject firstBlock = serializedNpc.getAsJsonObject();

        String id = firstBlock.get("id").getAsString();
        String name = firstBlock.get("name").getAsString();
        NpcType type = NpcType.from(firstBlock.get("type").getAsString());
        JsonObject locations = firstBlock.getAsJsonObject("location");

        double x, y, z;
        float pitch, yaw;
        String world;

        x = locations.get("x").getAsDouble();
        y = locations.get("y").getAsDouble();
        z = locations.get("z").getAsDouble();
        pitch = locations.get("pitch").getAsFloat();
        yaw = locations.get("yaw").getAsFloat();
        world = locations.get("world").getAsString();

        NpcLocation npcLocation = new NpcLocation(world, x, y, z, yaw, pitch);

        return NpcFactory.of(id, name, npcLocation, type);
    }

}
