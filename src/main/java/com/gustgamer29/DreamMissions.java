package com.gustgamer29;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import com.gustgamer29.command.MissionCommand;
import com.gustgamer29.config.ConfigManager;
import com.gustgamer29.controller.MPlayerController;
import com.gustgamer29.controller.MissionController;
import com.gustgamer29.controller.NpcController;
import com.gustgamer29.database.internal.DatabaseProvider;
import com.gustgamer29.database.internal.DatabaseProviderFactory;
import com.gustgamer29.database.internal.StorageFacade;
import com.gustgamer29.database.internal.npc.NpcDaoStrategy;
import com.gustgamer29.exception.UnSupportedVersion;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import com.gustgamer29.listener.FakePlayerListener;
import com.gustgamer29.listener.NpcListener;
import com.gustgamer29.mc_wrapper.NmsProvider;
import com.gustgamer29.npc.INPC;
import com.gustgamer29.npc.NpcType;
import com.gustgamer29.service.NpcService;
import com.gustgamer29.service.Service;
import lombok.Getter;
import net.jitse.npclib.NPCLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class DreamMissions extends JavaPlugin {

    private static final Logger MAIN_LOGGER = LoggerFactory.getLogger(DreamMissions.class);
    private static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName();
    private static final Pattern ID_PATTERN = Pattern.compile("^[0-9]");

    private static DreamMissions instance;

    @Getter
    private NmsProvider provider;

    @Getter
    private NPCLib npcLib;

    @Getter
    private BukkitCommandManager commandManager;

    @Getter
    private ConfigManager configManager;

    @Getter
    private NpcController npcController;

    @Getter
    private MissionController missionController;

    @Getter
    private StorageFacade storage;

    @Getter
    private MPlayerController userController;

    @Override
    public void onEnable() {
        instance = this;

        initialize();
    }

    @Override
    public void onDisable() {
        getNpcController().all().values().forEach(INPC::destroy);
    }

    private void initialize() {
        configure();

        loadDatabase();

        hookToNms();

        initializeServices();

        registerListeners();

        contextRegister();

        commandsRegister();
    }

    private void loadDatabase() {
        try {
            this.storage = new StorageFacade(this);
        } catch (SQLException e) {
            MAIN_LOGGER.error("An exception occurred when tried to load database. " +
                    "Please, make sure that connection configuration is properly correct. \n{}", e);
        }
    }

    private void commandsRegister() {
//        manager.getLocales().setDefaultLocale(new Locale("pt"));

        commandManager.registerCommand(new MissionCommand(this));
    }

    private void initializeServices() {
        Service service = new NpcService(this, NpcDaoStrategy.JSON);

        service.start();
    }

    private void configure() {
        MenuBootstrap.init(this);

        this.configManager = new ConfigManager(this);
        this.npcLib = new NPCLib(this);
        this.commandManager = new BukkitCommandManager(this);
        this.npcController = new NpcController();
        this.missionController = new MissionController();
        this.userController = new MPlayerController();
    }

    private void contextRegister() {
        commandManager
                .getCommandContexts()
                .registerContext(NpcType.class, context -> {

                    Player player = context.getPlayer();
                    String npc = context.popFirstArg();
                    NpcType type;

                    if (npc == null || npc.isEmpty()) {
                        return NpcType.VILLAGER;
                    }

                    if (npc.equalsIgnoreCase("vl") || npc.equalsIgnoreCase("villager")) {
                        type = NpcType.VILLAGER;
                        return type;
                    }

                    if (npc.equalsIgnoreCase("fk") ||
                            npc.equalsIgnoreCase("fp") ||
                            npc.equalsIgnoreCase("fakeplayer") ||
                            npc.equalsIgnoreCase("fake_player")) {
                        type = NpcType.FAKE_PLAYER;
                        return type;
                    }

                    return NpcType.VILLAGER;
                });

        getCommandManager()
                .getCommandConditions()
                .addCondition(String.class, "npc_id_check", (context, execContext, value) -> {
                    if (value == null || value.isEmpty()) {
                        throw new ConditionFailedException("&cInsira um id válido.");
                    }

                    if (value.length() > 12 || ID_PATTERN.matcher(value).matches()) {
                        throw new ConditionFailedException("&cNão é possível utilizar o id inserido.");
                    }

                    if (getNpcController().contains(value))
                        throw new ConditionFailedException("&cId inserido já está sendo utilizado por outro npc.");
                });
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new NpcListener(this), this);
        pm.registerEvents(new FakePlayerListener(this), this);
    }

    private void hookToNms() {
        String version = NMS_VERSION.substring(NMS_VERSION.lastIndexOf(".") + 1);

        try {
            MAIN_LOGGER.info("Trying to hook to mc_wrapper..");
            provider = new NmsProvider(version);
            MAIN_LOGGER.info("Hooked to " + version.replaceAll("_", ".") + " mc_wrapper.");
        } catch (UnSupportedVersion err) {
            MAIN_LOGGER.error("Plugin can't hook to your nms. " +
                    "Probably that you're using a unsupported server version for this plugin. " +
                    "Report it if you think that is an error.");
            err.printStackTrace();
        }
    }

    public static DreamMissions getInstance() {
        return instance;
    }

}
