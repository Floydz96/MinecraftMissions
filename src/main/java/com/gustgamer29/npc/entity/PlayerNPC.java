package com.gustgamer29.npc.entity;

import com.gustgamer29.DreamMissions;
import com.gustgamer29.MenuBootstrap;
import com.gustgamer29.database.internal.npc.NpcDaoStrategy;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import com.gustgamer29.mc_wrapper.Hologram;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcLocation;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.util.Callback;
import lombok.Getter;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.Set;

public class PlayerNPC extends AbstractNPC {

    private static final Logger logger = LoggerFactory.getLogger(PlayerNPC.class);
    private static final String SKIN_DATA = "eyJ0aW1lc3RhbXAiOjE1NzAwMjY5NDMwNTEsInByb2ZpbGVJZCI6IjkxOGEwMjk1NTlkZDRjZTZiMTZmN2E1ZDUzZWZiNDEyIiwicHJvZmlsZU5hbWUiOiJCZWV2ZWxvcGVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mOTg4YjU0ZDExYjllZDFjMGRiNmZkODViZDNmOGQzYzQyMWNkZmVjNmZjYzdiNDM3ZTE2MDJiYzhmYzVhODcwIn19fQ==";
    private static final String SKIN_SIG = "HPZOUmoi2SoDwxP5E3JkrqIcbKS7gw57lgeF6fwkAlZh15ohAA4WJX2FoXRi/Dc/2Bpd2rWze+wRVio6G/fMv6wyohovEtCTG3FbvQZnIBB69x2hEaKum3vOjShMpMiVPIJ63EBNlXv+o6tg/dIRyye16v5iqc9q9sJJkDoRAsKfe7pXwc7pFdrlqidmZRsSNtwu3fiY7DhQk82WkssBCB/Bn1RGIH4Ddrf1qpjO0Pk65yyjCUeHru9lL7ioldqnwiEQ0WHwj41C6CZ07e9YKDYsmmOz/md88L9uiTDr3ttBdb6yhdWXHm1uwtuFqv7iO35GXkof77S5AguA8qq4C83VHSOolfGhT1cS4gfOmgIGOOxk4dNhcB43lOTELFuEx/krMRYRzsf90rHIQbLgigupJCzRIt2yhF9LysaKF2cXnpf8Dya+niJ8roiPDBGDzRkXeeXqUQ6PdMQbTQFy8BvKu0sSkDH/IEQ9sG/R+QAVryO8P0SQmlhl52MdG8a+d5aPFCTrKBNJjnUPbJMf59O/AHVvCEf72pvA6Ap6eMf+uPW26jNXf0PMe0mCjatKgSnZtAklPypp0nvD3LyLCVUPJusWVb+CN95u0x+7GdAotfCCszVbmOSYHvxyZWg/n6WDTLP5EPELr671cDzNREfcEqtDtf8jxnlhFKwDIYg=";

    @Getter
    private NPC npc;

    @Getter
    private Set<Hologram> holograms;

    public PlayerNPC(String id, String name, NpcLocation loc) {
        super(id, name, loc, NpcType.FAKE_PLAYER);
    }

    @Override
    public boolean persist(DreamMissions plugin, NpcDaoStrategy persistStrategy) {
        logger.info("Trying to persist {} into {} logic..", getName(), persistStrategy.name());

        try {
            persistStrategy.invoke(plugin).save(this);
            logger.info("{} was saved into {}", getName(), persistStrategy.name());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Could not possible to persist {} into {}. An error occurred. {}", getName(), persistStrategy.name(), ex);
            return false;
        }

        return true;
    }

    @Override
    public void spawn(DreamMissions plugin, Callback<DispatchedActionStatus> statusCallback) {
        npc = plugin
                .getNpcLib()
                .createNPC();

        Location l = NpcLocation.of(getLocation());
        npc.setLocation(l);
        npc.setSkin(new Skin(SKIN_DATA, SKIN_SIG));
        npc.create();

        this.holograms = plugin.getProvider()
                .getNmsVersion()
                .summonHologram(
                        Arrays.asList("&aMissões Diárias",
                                "&e&k!@@!&r &7(&fClique para abrir o Menu&7) &e&k!@@!"),
                        l.clone().add(0, 0.6, 0));

        MenuBootstrap.newChain().execute(() -> {
            Bukkit.getOnlinePlayers().forEach(npc::show);
            setSpawned(true);
            statusCallback.call(DispatchedActionStatus.EXECUTED, null);
        });
    }

    @Override
    public void destroy() {
        if(isSpawned()){
            npc.destroy();
            this.holograms.forEach(Hologram::destroy);
        }
    }

    @Override
    public INPC copy() {
        return null;
    }
}
