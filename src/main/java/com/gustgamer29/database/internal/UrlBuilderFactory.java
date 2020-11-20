package com.gustgamer29.database.internal;

import com.gustgamer29.database.internal.mysql.MySQLUrlBuilder;
import com.gustgamer29.libs.logger.Logger;
import com.gustgamer29.libs.logger.LoggerFactory;

public class UrlBuilderFactory {

    private static final Logger logger = LoggerFactory.getLogger(UrlBuilderFactory.class);

    public static AbstractUrlBuilder fromType(DatabaseProviderStrategy type){
        switch (type){
            case MYSQL:
                return new MySQLUrlBuilder();
            case H2:
                throw new UnsupportedOperationException("H2 is not supported yet.");
            case MONGODB:
                throw new UnsupportedOperationException("MongoDB is not supported yet");
            default:
                logger.error("UrlBuilder of type {} isn't found.", type.name());
                throw new RuntimeException();
        }
    }

    public static AbstractUrlBuilder fromName(String name){
        return UrlBuilderFactory.fromType(DatabaseProviderStrategy.fromString(name));
    }
}
