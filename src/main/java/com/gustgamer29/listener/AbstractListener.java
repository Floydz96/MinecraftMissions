package com.gustgamer29.listener;

import com.gustgamer29.DreamMissions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Listener;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@NonNull
public class AbstractListener implements Listener {

    @Getter(AccessLevel.PROTECTED)
    private final DreamMissions plugin;

}
