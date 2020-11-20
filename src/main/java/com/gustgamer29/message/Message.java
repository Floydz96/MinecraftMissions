package com.gustgamer29.message;

import com.gustgamer29.text.TextFormatter;
import org.bukkit.entity.Player;

public enum Message {

    PLUGIN_CHAT_PREFIX("&7[&ADailyQuests&7]"),

    DEFAULT_COMMAND_LIST_HEADER(" "),
    DEFAULT_COMMAND_LIST("&8 - &a/missao &7info &f- &6Show your actual mission status.\""),
    DEFAULT_COMMAND_LIST_FOOTER(" ");

    private final String message;
    private final boolean showPrefix;

    Message(String message, boolean showPrefix) {
        // rewrite hardcoded placeholders according to their position
        this.message = TextUtils.rewritePlaceholders(message);
        this.showPrefix = showPrefix;
    }

    public String getMessage() {
        return this.message;
    }

    private String getTranslatedMessage(@Nullable LocaleManager localeManager) {
        String message = null;
        if (localeManager != null) {
            message = localeManager.getTranslation(this);
        }
        if (message == null) {
            message = this.getMessage();
        }
        return message;
    }

    private String format(@Nullable LocaleManager localeManager, Object... objects) {
        String prefix = PREFIX.getTranslatedMessage(localeManager);
        String msg = format(
                this.getTranslatedMessage(localeManager)
                        .replace("{PREFIX}", prefix)
                        .replace("\\n", "\n"),
                objects
        );
        return this.showPrefix ? prefix + msg : msg;
    }

    public String asString(@Nullable LocaleManager localeManager, Object... objects) {
        return colorize(format(localeManager, objects));
    }

    public TextComponent asComponent(@Nullable LocaleManager localeManager, Object... objects) {
        return TextUtils.fromLegacy(format(localeManager, objects), TextUtils.AMPERSAND_CHAR);
    }

    public void send(Player sender, Object... objects) {
        sender.sendMessage(asString(sender.getPlugin().getLocaleManager(), objects));
    }

    private static String format(String s, Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            Object o = objects[i];
            s = s.replace("{" + i + "}", String.valueOf(o));
        }
        return s;
    }

}
