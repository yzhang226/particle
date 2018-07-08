package org.lightning.particle.jdbc.test;

import org.junit.jupiter.api.Test;
import org.lightning.particle.jdbc.common.meta.Table;
import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.meta.MetadataFetcher;
import org.lightning.particle.jdbc.meta.MetadataFetcherFacotry;

/**
 * Created by cook at 2018/7/8
 */
public class MetaDataFetcherTest {

    @Test
    public void testMySQLFetch() {
        DataSourceParam param = DbTestUtils.createMySQLDemoParam();
        MetadataFetcher fetcher = MetadataFetcherFacotry.createFetcher(param);
        Table table = fetcher.fetchTable("mkt_tasks");
        System.out.println(table);
    }

    @Test
    public void testSQLServerFetch() {
        DataSourceParam param = DbTestUtils.createSQLServerDemoParam();
        MetadataFetcher fetcher = MetadataFetcherFacotry.createFetcher(param);
        Table table = fetcher.fetchTable("cmn_BizLog");
        System.out.println(table);
    }

}
