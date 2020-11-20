package com.gustgamer29.listener;

import com.gustgamer29.DreamMissions;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class BlockBreakListener extends AbstractListener {

    private static final BiPredicate<World, String> validWorld = (w, s) -> {
        if(w == null || s == null || s.isEmpty())
            return false;

        return w.getName().equals(s);
    };

    private List<String> world;

    protected BlockBreakListener(DreamMissions plugin, List<String> worldWhitelist) {
        super(plugin);
        this.world = worldWhitelist;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() == null)
            return;

        if(getPlugin().getMissionController().)

        World w = event.getPlayer().getWorld();

        Optional<String> optWorld = world.stream().filter(worldName -> validWorld.test(w, worldName))
                .findFirst();

        if(!optWorld.isPresent()){
            event.getPlayer().sendMessage();
        }
    }
}
