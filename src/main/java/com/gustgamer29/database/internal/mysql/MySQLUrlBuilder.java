package com.gustgamer29.database.internal.mysql;

import com.gustgamer29.database.internal.AbstractUrlBuilder;

public class MySQLUrlBuilder extends AbstractUrlBuilder {

    @Override
    protected String getDefaultConnectionFormat() {
        return AbstractUrlBuilder.DEFAULT_CONNECTION_FORMAT;
    }

    public static MySQLUrlBuilder builder(){
        return new MySQLUrlBuilder();
    }
}
