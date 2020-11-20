package com.gustgamer29.database.internal;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.gustgamer29.DreamMissions;
import com.gustgamer29.MenuBootstrap;
import com.gustgamer29.model.MPlayer;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class StorageFacade {

    private final Cache<UUID, MPlayer> playerCache = Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .maximumSize(20_000).build();

    private final Executor executor = (r) -> MenuBootstrap.SCHEDULER.sync.runTask(r);

    private DatabaseProviderFactory providerFactory;
    private DatabaseDaoProvider provider;


    public StorageFacade(DreamMissions plugin) throws SQLException {
        this.providerFactory = new DatabaseProviderFactory(plugin);
    }

    public void setup() {
        try {
            this.provider = providerFactory.setupDatabase();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private <C> CompletableFuture<C> future(Callable<C> callable) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return callable.call();
            } catch (Exception ex) {
                throw new CompletionException(ex);
            }
        }, executor);
    }

    public CompletableFuture<Set<MPlayer>> loadAllPlayers() {
        return future(() ->
           new HashSet<>(provider.mPlayerDao().getAll())
        );
    }

    public CompletableFuture<MPlayer> loadMPlayer(UUID uniqueId){
        return future(() -> playerCache.get(uniqueId, p -> provider.mPlayerDao().getById(p)));
    }
}
