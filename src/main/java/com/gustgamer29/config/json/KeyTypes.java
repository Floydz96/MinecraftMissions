package com.gustgamer29.config.json;

import com.google.common.collect.ImmutableMap;
import com.gustgamer29.config.adapter.ConfigurationAdapter;

import java.util.Map;
import java.util.function.Function;

public class KeyTypes {

    private static final KeyFactory<Boolean> BOOLEAN = ConfigurationAdapter::getBoolean;

    private static final KeyFactory<String> STRING = ConfigurationAdapter::getString;

    private static final KeyFactory<String> LOWERCASE_STRING = (conf, path, def) -> conf.getString(path, def).toLowerCase();

    private static final KeyFactory<Map<String, String>> STRING_MAP = (element, path, def) -> ImmutableMap.copyOf(element.getStringMap(path, ImmutableMap.of()));

    public static BaseConfigKey<Boolean> booleanKey(String path, boolean def) {
        return BOOLEAN.createKey(path, def);
    }

    public static BaseConfigKey<String> stringKey(String path, String def) {
        return STRING.createKey(path, def);
    }

    public static BaseConfigKey<String> lowercaseStringKey(String path, String def) {
        return LOWERCASE_STRING.createKey(path, def);
    }

    public static BaseConfigKey<Map<String, String>> mapKey(String path) {
        return STRING_MAP.createKey(path, null);
    }

    public static <T> CustomKey<T> customKey(Function<ConfigurationAdapter, T> function) {
        return new CustomKey<>(function);
    }

    public static <T> EnduringKey<T> enduringKey(JsonKey<T> delegate) {
        return new EnduringKey<>(delegate);
    }

    /**
     * Functional interface that extracts values from a {@link ConfigurationAdapter}.
     *
     * @param <T> the value type.
     */
    @FunctionalInterface
    public interface KeyFactory<T> {
        T getValue(ConfigurationAdapter adapter, String path, T def);

        default BaseConfigKey<T> createKey(String path, T def) {
            return new FunctionalKey<>(this, path, def);
        }
    }

    public abstract static class BaseConfigKey<T> implements JsonKey<T> {
        int ordinal = -1;

        BaseConfigKey() {

        }

        @Override
        public int elementPosition() {
            return this.ordinal;
        }
    }

    private static class FunctionalKey<T> extends BaseConfigKey<T> implements JsonKey<T> {
        private final KeyFactory<T> factory;
        private final String path;
        private final T def;

        FunctionalKey(KeyFactory<T> factory, String path, T def) {
            this.factory = factory;
            this.path = path;
            this.def = def;
        }

        @Override
        public T get(ConfigurationAdapter adapter) {
            return this.factory.getValue(adapter, this.path, this.def);
        }
    }

    public static class CustomKey<T> extends BaseConfigKey<T> {
        private final Function<ConfigurationAdapter, T> function;

        private CustomKey(Function<ConfigurationAdapter, T> function) {
            this.function = function;
        }

        @Override
        public T get(ConfigurationAdapter adapter) {
            return this.function.apply(adapter);
        }
    }

    public static class EnduringKey<T> extends BaseConfigKey<T> {
        private final JsonKey<T> delegate;

        private EnduringKey(JsonKey<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public T get(ConfigurationAdapter adapter) {
            return this.delegate.get(adapter);
        }
    }

}
