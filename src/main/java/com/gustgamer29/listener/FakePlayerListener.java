package com.gustgamer29.listener;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.npc.entity.PlayerNPC;
import com.gustgamer29.test.MissionMainMenu;
import lombok.AccessLevel;
import lombok.Getter;
import net.jitse.npclib.api.events.NPCInteractEvent;
import org.bukkit.event.EventHandler;

import java.util.List;

public class FakePlayerListener extends AbstractListener {

    @Getter(AccessLevel.PRIVATE)
    private List<INPC> npcs;

    public FakePlayerListener(DreamMissions plugin) {
        super(plugin);

        this.npcs = plugin.getNpcController().fromType(NpcType.FAKE_PLAYER);
    }


    @EventHandler
    public void onNpcInteract(NPCInteractEvent event){
        if(!isCancelable(event.getNPC().getId()))
            return;

        MissionMainMenu menu = new MissionMainMenu();
        menu.open(event.getWhoClicked());
    }

    private boolean isCancelable(String id){
        for(INPC npc : npcs){
            if(!(npc instanceof PlayerNPC))
                continue;

            PlayerNPC pnpc = (PlayerNPC) npc;
            String pid = pnpc.getId();
            if(id.equals(pid))
                return true;
        }

        return false;
    }
}
