package com.gustgamer29.listener;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.npc.entity.VillagerNPC;
import com.gustgamer29.test.MissionMainMenu;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NpcListener extends AbstractListener {

    public NpcListener(DreamMissions plugin) {
        super(plugin);
    }

    @EventHandler
    public void onNpcHitEvent(EntityDamageByEntityEvent event){
        if(event.getDamager() == null || event.getEntity() == null)
            return;

        boolean cancel = isCancelable(event.getEntity());
        event.setCancelled(cancel);
    }

    @EventHandler
    public void onNpcInteract(PlayerInteractEntityEvent event){
        if(event.getRightClicked() == null)
            return;

        boolean cancel = isCancelable(event.getRightClicked());
        event.setCancelled(cancel);

        if(!cancel)
            return;

        MissionMainMenu menu = new MissionMainMenu();
        menu.open(event.getPlayer());
    }

    private boolean isCancelable(Entity entity){
        if(!(entity instanceof Villager))
            return false;

        VillagerNPC vnpc = null;
        for(INPC npc : getPlugin().getNpcController().fromType(NpcType.VILLAGER)){
            if(!(npc instanceof VillagerNPC))
                continue;

            vnpc = (VillagerNPC) npc;

            if(entity.equals(vnpc.getEntity())){
                return true;
            }
        }
        return false;
    }
}
