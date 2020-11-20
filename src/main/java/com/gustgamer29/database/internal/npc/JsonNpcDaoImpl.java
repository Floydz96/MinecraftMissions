package com.gustgamer29.database.internal.npc;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.config.adapter.ConfigurationAdapter;
import com.gustgamer29.config.files.LocationsFile;
import com.gustgamer29.config.json.LocationKeys;
import com.gustgamer29.database.internal.dao.npc.NpcDAO;
import com.gustgamer29.exception.IdNotMatchException;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcSerializer;
import com.gustgamer29.npc.NpcType;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class JsonNpcDaoImpl implements NpcDAO {

    @Getter
    private LocationsFile jsonFile;

    @Getter
    private ConfigurationAdapter adapter;

    public JsonNpcDaoImpl(JavaPlugin plugin) {
        this.jsonFile = ((DreamMissions) plugin).getConfigManager().getLocationsFile();
        this.adapter = jsonFile.getAdapter();
    }

    @Override
    public List<INPC> getByType(NpcType type) {
        return LocationKeys.NPC_LOCATIONS.get(adapter)
                .stream()
                .filter(npc -> npc.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public INPC getById(String id) {
        return LocationKeys.NPC_LOCATIONS.get(adapter)
                .stream()
                .filter(npc -> npc.getId().equals(id)).findFirst()
                .orElseThrow(IdNotMatchException::new);
    }

    @Override
    public List<INPC> getAll() {
        return LocationKeys.NPC_LOCATIONS.get(adapter);
    }

    @Override
    public void save(INPC npc) {
        jsonFile.getAdapter().save(NpcSerializer.serialize(npc));
    }

    @Override
    public void delete(String id) {

        INPC inpc = getAll().stream().filter(npc -> npc.getId().equals(id)).findFirst().orElseThrow(IdNotMatchException::new);
        jsonFile.getAdapter().remove(NpcSerializer.serialize(inpc));
    }

    @Override
    public void update(String identifier, INPC obj) {

    }
}
