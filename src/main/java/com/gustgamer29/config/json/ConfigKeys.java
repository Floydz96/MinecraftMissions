package com.gustgamer29.config.json;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gustgamer29.database.internal.DatabaseProviderStrategy;
import com.gustgamer29.database.internal.AbstractUrlBuilder;
import com.gustgamer29.database.internal.UrlBuilderFactory;
import com.gustgamer29.exception.BlockOccurrenceNotFound;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;
import com.gustgamer29.model.MissionDifficulty;
import com.gustgamer29.model.Reward;
import com.gustgamer29.model.blockminer.BlockOccurrence;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

import static com.gustgamer29.config.json.KeyTypes.customKey;
import static com.gustgamer29.config.json.KeyTypes.enduringKey;

public final class ConfigKeys {

    private static final Logger logger = LoggerFactory.getLogger("ConfigurationFile");

    public static final JsonKey<Integer> GENERAL_REFRESH_TIME = enduringKey(customKey(c -> {
        int val = c.getInteger("General.refresh-time", 86400);

        if (val == -1) {
            val = c.getInteger("general.refresh-time", 86400);
        }

        return val;
    }));

    public static final JsonKey<List<String>> GENERAL_WORLDS_WHITELIST = enduringKey(customKey(c -> {
        List<String> whitelist = c.getStringList("General.world-whitelist", Lists.newArrayList());

        if (whitelist.isEmpty()) {
            whitelist = c.getStringList("general.world-whitelist", Lists.newArrayList());
        }

        return whitelist;
    }));

    public static final JsonKey<? extends AbstractUrlBuilder> GENERAL_DATABASE_CONNECTION = enduringKey(customKey(c -> {
        String dbType = c.getString("General.database.provider", null);

        if (dbType == null || dbType.isEmpty()) {
            logger.error("Database provider isn't found in the configuration file. Setting default provider to MySQL.");
            dbType = "MySQL";
        }

        DatabaseProviderStrategy type = DatabaseProviderStrategy.fromString(dbType);

        if (type == null)
            type = DatabaseProviderStrategy.MYSQL;

        AbstractUrlBuilder builder = UrlBuilderFactory.fromType(type);

        String  host = c.getString("General.database.host", "127.0.0.1"),
                port = c.getString("General.database.port", "3306"),
                user = c.getString("General.database.user", "root"),
                password = c.getString("General.database.password", ""),
                database = c.getString("General.database.database", "database");


        builder
                .host(host)
                .port(port)
                .user(user)
                .password(password)
                .database(database);

        return builder;
    }));

    public static final JsonKey<List<Reward>> BLOCK_MINER_REWARDS_EASY = enduringKey(customKey(c -> {

        List<Reward> rewards = Lists.newArrayList();

        for (JsonElement jsonElement : c.getElement("BlockMiner.bounties.easy").getAsJsonArray()) {
            JsonObject element = jsonElement.getAsJsonObject();
            rewards.add(new Reward(element.get("command").getAsString(), element.getAsJsonObject().get("priority").getAsInt(), MissionDifficulty.EASY));
        }

        return rewards;
    }));

    public static final JsonKey<List<Reward>> BLOCK_MINER_REWARDS_MEDIUM = enduringKey(customKey(c -> {

        List<Reward> rewards = Lists.newArrayList();

        for (JsonElement jsonElement : c.getElement("BlockMiner.bounties.medium").getAsJsonArray()) {
            JsonObject element = jsonElement.getAsJsonObject();
            rewards.add(new Reward(element.get("command").getAsString(), element.getAsJsonObject().get("priority").getAsInt(), MissionDifficulty.EASY));
        }

        return rewards;
    }));

    public static final JsonKey<List<Reward>> BLOCK_MINER_REWARDS_HARD = enduringKey(customKey(c -> {

        List<Reward> rewards = Lists.newArrayList();

        for (JsonElement jsonElement : c.getElement("BlockMiner.bounties.hard").getAsJsonArray()) {
            JsonObject element = jsonElement.getAsJsonObject();
            rewards.add(new Reward(element.get("command").getAsString(), element.getAsJsonObject().get("priority").getAsInt(), MissionDifficulty.EASY));
        }

        return rewards;
    }));

    public static final JsonKey<List<BlockOccurrence>> BLOCK_MINER_BLOCK_OCCURRENCE = enduringKey(customKey(c -> {

        List<BlockOccurrence> blocks = Lists.newArrayList();

        JsonObject element = c.getElement("BlockMiner.block-occurrence").getAsJsonObject();

        element.entrySet().forEach(entry -> {

            BlockOccurrence occurrence = new BlockOccurrence(entry.getKey());
            JsonObject obj = entry.getValue().getAsJsonObject();

            if (obj.has("easy") && obj.get("easy").getAsJsonPrimitive().isString())
                occurrence.setEasy(obj.get("easy").getAsString());

            if (obj.has("normal") && obj.get("normal").getAsJsonPrimitive().isString())
                occurrence.setNormal(obj.get("normal").getAsString());

            if (obj.has("hard") && obj.get("hard").getAsJsonPrimitive().isString())
                occurrence.setHard(obj.get("hard").getAsString());

            blocks.add(occurrence);

        });

        return blocks;
    }));

    public static final JsonKey<Map<Material, BlockOccurrence>> BLOCK_MINER_BLOCK_WHITELIST = enduringKey(customKey(c -> {

        Map<Material, BlockOccurrence> whitelistOccurrences = Maps.newHashMap();
        List<BlockOccurrence> occurrences = BLOCK_MINER_BLOCK_OCCURRENCE.get(c);


        JsonObject element = c.getElement("BlockMiner.blocks-whitelist").getAsJsonObject();

        element.entrySet().forEach(entry -> {

            Material mat = Material.valueOf(entry.getKey());
            BlockOccurrence occ = occurrences
                    .stream()
                    .filter(oc -> oc.getId().equals(entry.getValue().getAsString()))
                    .findFirst()
                    .orElseThrow(() -> new BlockOccurrenceNotFound(entry.getValue() + " wasn't found in block-occurrences."));

            whitelistOccurrences.put(mat, occ);
        });

        return whitelistOccurrences;
    }));

    public static final JsonKey<List<String>> BLOCK_MINER_WORLD_WHITELIST = customKey(c ->
            c.getStringList("BlockMiner.world-whitelist", Lists.newArrayList())
    );
}
