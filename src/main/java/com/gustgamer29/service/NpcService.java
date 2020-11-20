package com.gustgamer29.service;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.database.internal.npc.NpcDaoStrategy;
import com.gustgamer29.database.internal.dao.npc.NpcDAO;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicInteger;

@Getter(AccessLevel.PRIVATE)
public class NpcService implements Service {

    private static final Logger logger = LoggerFactory.getLogger(NpcService.class);

    private final JavaPlugin plugin;
    private final NpcDAO npcDAO;

    public NpcService(JavaPlugin plugin, NpcDaoStrategy npcLoaderStrategy) {
        this.plugin = plugin;
        this.npcDAO = npcLoaderStrategy.invoke(plugin);
    }

    @Override
    public void start() {
//        LocationKeys.NPC_LOCATIONS.get(locationsFile).forEach(loc -> {
//            Location location = loc.getLocation();
//
//            String world = location.getWorld().getName();
//            double x = location.getX(), y = location.getY(), z = location.getZ();
//            float pitch = location.getPitch(), yaw = location.getYaw();
//            NpcType type = loc.getNpcType();
//
//            try {
//                type.getNpc(getPlugin()).spawn(loc.getNpcName(), world, x, y, z, pitch, yaw);
//                logger.debug("Npc {} loaded at x {} y {} z {} world {}", loc.getNpcName(), x, y, z, world);
//            } catch (Exception ex){
//                ex.printStackTrace();
//            }
//        });
        AtomicInteger spawned = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();
        npcDAO.getAll().forEach(inpc -> {
            inpc.spawn((DreamMissions) getPlugin(), (s, e) -> {

                switch (s) {
                    case INTERNAL_ERROR:
                        logger.error("Ocorreu um erro interno durante a execução do comando. " + e.getMessage());
                        error.incrementAndGet();
                        break;
                    case ERROR:
                        logger.error("Ocorreu um erro durante a execução do comando." + e.getMessage());
                        error.incrementAndGet();
                        break;
                    case EXECUTED:
                        logger.debug("Entidade " + inpc.getType().name() + " gerado.");
                        ((DreamMissions) plugin).getNpcController().register(inpc);
                        spawned.incrementAndGet();
                        break;
                }

            });
        });
        logger.info("{} NPCs are loaded and spawned from storage.", spawned.get());
        logger.info("{} NPCs could not be either loaded or spawned.", error.get());
    }
}
