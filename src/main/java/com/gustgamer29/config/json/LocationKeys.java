package com.gustgamer29.config.json;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcSerializer;

import java.util.List;

import static com.gustgamer29.config.json.KeyTypes.customKey;
import static com.gustgamer29.config.json.KeyTypes.enduringKey;

public class LocationKeys {

    public static final JsonKey<List<INPC>> NPC_LOCATIONS = enduringKey(customKey((e) -> {
        List<INPC> npcLocations = Lists.newArrayList();
        for(JsonElement je : e.getElement("npc-locations").getAsJsonArray()){
            npcLocations.add(NpcSerializer.deserialize(je));
        }

        return npcLocations;
    }));

    public static final JsonKey<Integer> NPC_LAST_ID = customKey((e) ->{
        JsonElement id = e.getElement("last-npc-id");

        if(id == null || !id.isJsonPrimitive() || !id.getAsJsonPrimitive().isNumber())
            return -1;

        return id.getAsJsonPrimitive().getAsInt();
    });
}
