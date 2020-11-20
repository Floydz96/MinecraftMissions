package com.gustgamer29.mc_wrapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class FakePlayerInfo {
    @Getter
    private final String name;
    @Getter
    private final String uuid;
    @Getter
    private final String skin;
    @Getter
    private final String skinSignature;

    public static FakePlayerBuilder builder() {
        return new FakePlayerBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FakePlayerBuilder {

        private String name;
        private String uuid;
        private String skin;
        private String skinSignature;

        public FakePlayerBuilder name(String name) {
            Objects.requireNonNull(name, "name");
            this.name = name;
            return this;
        }

        public FakePlayerBuilder uuid(String uuid) {
            Objects.requireNonNull(uuid, "uuid");
            this.uuid = uuid;
            return this;
        }

        public FakePlayerBuilder skin(String skin) {
            Objects.requireNonNull(skin, "skin");
            this.skin = skin;
            return this;
        }

        public FakePlayerBuilder signature(String skinSignature) {
            Objects.requireNonNull(skinSignature, "skinSignature");
            this.skinSignature = skinSignature;
            return this;
        }

        public FakePlayerInfo build(){
            return new FakePlayerInfo(name, uuid, skin, skinSignature);
        }
    }
}
