package com.gustgamer29.npc.entity;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.database.internal.npc.NpcDaoStrategy;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import com.gustgamer29.mc_wrapper.Hologram;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcLocation;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.util.Callback;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.Arrays;
import java.util.Set;

public class VillagerNPC extends AbstractNPC {

    private static final Logger logger = LoggerFactory.getLogger(VillagerNPC.class);

    @Setter(AccessLevel.PRIVATE)
    @Getter
    private Villager entity;

    @Getter
    private Set<Hologram> holograms;

    public VillagerNPC(String id, String name, NpcLocation loc) {
        super(id, name, loc, NpcType.VILLAGER);
    }

    @Override
    public boolean persist(DreamMissions plugin, NpcDaoStrategy persistStrategy) {
        logger.info("Trying to persist {} into {} logic..", getName(), persistStrategy.name());

        try {
            persistStrategy.invoke(plugin).save(this);
            logger.info("{} was saved into {}", getName(), persistStrategy.name());
        } catch (Exception ex){
            ex.printStackTrace();
            logger.error("Could not possible to persist {} into {}. An error occurred. {}", getName(), persistStrategy.name(), ex);
            return false;
        }

        return true;
    }

    @Override
    public void spawn(DreamMissions plugin, Callback<DispatchedActionStatus> statusCallback) {
        Location vl = NpcLocation.of(getLocation());

        Villager villager = (Villager) vl.getWorld().spawnEntity(vl, EntityType.VILLAGER);
        //villager.setCustomName(getName());
        //villager.setCustomNameVisible(true);
        villager.setProfession(Villager.Profession.PRIEST);

        try {
            plugin.getProvider().getNmsVersion().setEntityAi(villager, false);
        } catch (Exception ex){
            statusCallback.call(DispatchedActionStatus.ERROR, ex);
            return;
        }

        setEntity(villager);
        setSpawned(true);

        this.holograms = plugin.getProvider()
                .getNmsVersion()
                .summonHologram(
                        Arrays.asList("&aMissões Diárias",
                                "&e&k!@@!&r &7(&fClique para abrir o Menu&7) &e&k!@@!"),
                        vl.clone().add(0, 0.6, 0));

        statusCallback.call(DispatchedActionStatus.EXECUTED, null);
    }

    @Override
    public void destroy() {
        if(isSpawned()){
            entity.remove();
            holograms.forEach(Hologram::destroy);
        }
    }

    @Override
    public INPC copy() {
        return null;
    }
}
