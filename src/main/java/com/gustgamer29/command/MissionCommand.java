package com.gustgamer29.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.collect.Lists;
import com.gustgamer29.DreamMissions;
import com.gustgamer29.cooldown.ExpiringSet;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcLocation;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.npc.entity.NpcFactory;
import com.gustgamer29.text.TextFormatter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.gustgamer29.text.TextFormatter.colorize;

@RequiredArgsConstructor
@CommandAlias("mission|missao|quests|quest")
public class MissionCommand extends BaseCommand {

    private final DreamMissions plugin;


    private ExpiringSet<UUID> cooldown = new ExpiringSet<UUID>(5, TimeUnit.SECONDS);

    @Default
    public void sendHelp(Player sender) {
        sendHelpMessage(sender);
    }

    @Subcommand("info")
    @CommandCompletion("@custom_players")
    @Syntax("info &e- Veja o status da missão atual!")
    public void questInfo(Player sender) {
        if (isDelayed(sender))
            return;

        plugin.getNpcController().all().forEach((k, v) -> sender.sendMessage(TextFormatter.colorize("&a" + k + " &7: &a" + v.getId())));
    }

    @Subcommand("setnpc")
    @Syntax("[id] [vl/fp] &e- Defina a localização do npc!")
    public void setNpc(Player sender, @Conditions("npc_id_check") String id, NpcType type) {

        if (isDelayed(sender))
            return;

        String npcName = colorize("&cMissões Diárias");
        Location loc = sender.getLocation();

        INPC npc = NpcFactory.of(id, npcName, NpcLocation.from(loc), type);

        if (npc != null)
            npc.spawn(plugin, (s, e) -> {
                switch (s) {
                    case INTERNAL_ERROR:
                        sender.sendMessage(colorize("&cOcorreu um erro interno durante a execução do comando. " + e.getMessage()));
                        break;
                    case ERROR:
                        sender.sendMessage(colorize("&cOcorreu um erro durante a execução do comando." + e.getMessage()));
                        break;
                    case EXECUTED:
                        sender.sendMessage(colorize("&7Entidade &a" + type.name() + "&7 gerado na suas coordenadas."));
                        plugin.getNpcController().register(npc);
                        break;
                }
            });
    }

    @Subcommand("removenpc")
    @Syntax("[id] [vl/fp] &e- Defina a localização do npc!")
    public void removeNpc(Player sender, @Optional String id) {
        if (isDelayed(sender))
            return;

        if(id == null || id.isEmpty()){
            sender.sendMessage(TextFormatter.colorize("&cInsira o identificador do npc a ser removido."));
            return;
        }

        INPC npc = plugin.getNpcController().fromId(id);

        if (npc == null) {
            String fid = id.length() > 5 ? id.substring(0, 5) + "..." : id;
            sender.sendMessage(TextFormatter.colorize("&cIdentificador &e" + fid + " &cinválido."));
            return;
        }

        try {

            npc.destroy();
            plugin.getNpcController().unregister(id);

        } catch (Exception ex){
            ex.printStackTrace();
            sender.sendMessage(TextFormatter.colorize("&cUm erro impediu que o npc fosse removido. &e" + ex.toString()));
        } finally {
            boolean wasRemoved = plugin.getNpcController().contains(id);

            if(!wasRemoved)
                sender.sendMessage(TextFormatter.colorize("&cNpc foi removido."));
            else
                sender.sendMessage(TextFormatter.colorize("&cNpc não foi removido."));
        }

    }

    private boolean isDelayed(Player sender) {
        if (cooldown.contains(sender.getUniqueId())) {
            sender.sendMessage(colorize("&cAguarde " + cooldown.getExpiration(sender.getUniqueId()).getDuration() + "s para executar novamente."));
            return true;
        }

        cooldown.add(sender.getUniqueId());
        return false;
    }

    private void sendHelpMessage(Player toPlayer) {
        List<String> s = Lists.newArrayList(" ",
                "&8 - &a/quests &7info &f- &6Veja o status da missão atual!", " ");
        s.forEach(ss -> {
            toPlayer.sendMessage(colorize(ss));
        });
    }
}
