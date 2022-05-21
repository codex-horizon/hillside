package com.metaverse.hillside.core.configurer.database;

import com.metaverse.hillside.core.helper.MultipleDataSourceHelper;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingMultipleDataSourceSelector extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return MultipleDataSourceHelper.getDataSource();
    }

}
