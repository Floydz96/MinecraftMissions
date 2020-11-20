package com.gustgamer29.database.internal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractUrlBuilder {

    protected static final String DEFAULT_CONNECTION_FORMAT = "{provider}://{host}:{port}/{database}?user={user}&password={password}";

    @Getter @Setter(AccessLevel.PROTECTED)
    private String provider, host, port, database, user, password;

    @Getter
    private DatabaseProviderStrategy dbProviderType;

    public AbstractUrlBuilder(DatabaseProviderStrategy dbProviderType) {
        this.dbProviderType = dbProviderType;
    }

    protected abstract String getDefaultConnectionFormat();

    public AbstractUrlBuilder host(String host){
        Objects.requireNonNull(host, "host");

        setHost(host);
        return this;
    }

    public AbstractUrlBuilder port(String port){
        Objects.requireNonNull(port, "port");

        setPort(port);
        return this;
    }

    public AbstractUrlBuilder database(String database){
        Objects.requireNonNull(database, "database");

        setDatabase(database);
        return this;
    }

    public AbstractUrlBuilder user(String user){
        Objects.requireNonNull(user, "user");

        setUser(user);
        return this;
    }

    public AbstractUrlBuilder password(String password){
        Objects.requireNonNull(password, "password");

        setPassword(password);
        return this;
    }

    public String build(){
       return getDefaultConnectionFormat()
               .replace("{provider}", getProvider())
               .replace("{host}", getHost())
               .replace("{port}", getPort())
               .replace("{database}", getDatabase())
               .replace("{user}", getUser())
               .replace("{password}", getPassword());
    }
}
