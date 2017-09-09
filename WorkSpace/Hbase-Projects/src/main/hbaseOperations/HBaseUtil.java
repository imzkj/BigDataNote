import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.exceptions.HBaseException;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;


public class HBaseUtil {
    private static Logger logger = Logger.getLogger(HBaseUtil.class);
    private static Configuration conf = null;
    private Table htable = null;
    private ResultScanner resultScanner = null;
    private static Connection connection = null;

    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("hbase.zookeeper.quorum", "10.201.3.46,10.201.3.65,10.201.3.66");
            conf.set("hbase.client.scanner.caching", "100");
            conf.set("hbase.rpc.timeout", "6000");//rpc的超时时间
            conf.set("ipc.socket.timeout", "2000");//socket建立链接的超时时间，应该小于或者等于rpc的超时时间
            conf.set("hbase.client.retries.number", "3");//重试次数
            conf.set("hbase.client.pause", "100");//重试休眠时间，默认为1s
            conf.set("zookeeper.recovery.retry", "3");//zk重试次数
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            logger.error("HBase Connection init failed " + e.getMessage());
        }
    }

    public HBaseUtil(String tableName) throws HBaseException {
        try {
            if (connection != null) {
                htable = connection.getTable(TableName.valueOf(tableName));
            }
        } catch (IOException e) {
            throw new HBaseException("init failed " + e.getMessage());
        }
    }

    public HBaseUtil() {

    }

    public ResultScanner getResultScanner() throws HBaseException {
        try {
            Scan scan = new Scan();
            scan.setCacheBlocks(false);
            if (htable != null) {
                resultScanner = htable.getScanner(scan);
            } else {
                resultScanner = null;
            }
        } catch (IOException e) {
            throw new HBaseException("get resultScanner failed " + e.getMessage());
        }
        return resultScanner;
    }

    public ResultScanner getResultScanner(FilterList filter) throws HBaseException {
        try {
            Scan scan = new Scan();
            scan.setCacheBlocks(false);
            scan.setFilter(filter);
            if (htable != null) {
                resultScanner = htable.getScanner(scan);
            } else {
                resultScanner = null;
            }
        } catch (IOException e) {
            throw new HBaseException("get resultScanner failed " + e.getMessage());
        }
        return resultScanner;
    }

    public ResultScanner getResultScanner(Scan scan) throws HBaseException {
        try {
            resultScanner = htable.getScanner(scan);
        } catch (IOException e) {
            throw new HBaseException("get resultScanner failed " + e.getMessage());
        }
        return resultScanner;
    }

    public Result get(String rowKey) throws HBaseException {
        Get get = new Get(Bytes.toBytes(rowKey));
        Result rs = null;
        try {
            rs = htable.get(get);
        } catch (IOException e) {
            throw new HBaseException(" query failed rowkey:" + rowKey + " " + e.getMessage());
        }
        return rs;
    }

    public Result get(String rowKey, Filter filter) throws HBaseException {
        Get get = new Get(Bytes.toBytes(rowKey));
        get.setFilter(filter);
        Result rs = null;
        try {
            rs = htable.get(get);
        } catch (IOException e) {
            throw new HBaseException(" query failed rowkey:" + rowKey + e.getMessage());
        }
        return rs;
    }

    public static Result get(String table, String rowKey, Filter filter) throws HBaseException {
        Table htable = null;
        Result rs = null;
        try {
            if (connection != null) {
                htable = connection.getTable(TableName.valueOf(table));
            }
            if (htable != null && rowKey != null) {
                Get get = new Get(Bytes.toBytes(rowKey));
                get.setFilter(filter);
                rs = htable.get(get);
            }
        } catch (IOException e) {
            throw new HBaseException("get failed " + e.getMessage());
        } finally {
            try {
                if (htable != null)
                    htable.close();
            } catch (IOException e) {
            }
        }
        return rs;
    }

    public static Result[] get(String table, List<String> rowKeys) throws HBaseException {
        Table htable = null;
        try {
            if (connection != null) {
                htable = connection.getTable(TableName.valueOf(table));
            }
            if (htable != null && rowKeys != null && rowKeys.size() > 0) {
                List<Get> gets = new ArrayList<Get>();
                for (String rowKey : rowKeys) {
                    Get get = new Get(Bytes.toBytes(rowKey));
                    gets.add(get);
                }
                return htable.get(gets);
            }
        } catch (IOException e) {
            throw new HBaseException("get failed " + e.getMessage());
        } finally {
            try {
                if (htable != null)
                    htable.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static Result[] get(String table, List<String> rowKeys, Filter filter) throws HBaseException {
        Table htable = null;
        try {
            if (connection != null) {
                htable = connection.getTable(TableName.valueOf(table));
            }
            if (htable != null && rowKeys != null && rowKeys.size() > 0) {
                List<Get> gets = new ArrayList<Get>();
                for (String rowKey : rowKeys) {
                    Get get = new Get(Bytes.toBytes(rowKey));
                    get.setFilter(filter);
                    gets.add(get);
                }
                return htable.get(gets);
            }
        } catch (IOException e) {
            throw new HBaseException("get failed " + e.getMessage());
        } finally {
            try {
                if (htable != null)
                    htable.close();
            } catch (IOException e) {
            }
        }
        return null;
    }


    public void closeHtable() {
        if (resultScanner != null) {
            resultScanner.close();
        }
        try {
            if (htable != null) {
                htable.close();
            }
        } catch (IOException e) {
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (IOException e) {
        }
    }

    /**
     * 获取Htable
     *
     * @param tabel
     * @return
     * @throws HBaseException
     */
    public static Table getHTable(String tabel) throws HBaseException {
        try {
            if (connection != null)
                return connection.getTable(TableName.valueOf(tabel));
        } catch (IOException e) {
            throw new HBaseException("get HTable failed tablename: " + tabel + " " + e.getMessage());
        }
        return null;
    }

    /**
     * 判断表是否存在
     *
     * @param table
     * @return
     * @throws IOException
     */
    public static boolean tableExists(String table) throws HBaseException {
        Admin admin = null;
        boolean exist = false;
        try {
            admin = connection.getAdmin();
            exist = admin.tableExists(TableName.valueOf(table));
        } catch (IOException e) {
            throw new HBaseException("tableExists failed tablename: " + table + " " + e.getMessage());
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                }
            }
        }
        return exist;
    }

    /**
     * 建表
     *
     * @param table   表名
     * @param familys 列族名
     * @throws IOException
     */
    public static void createTable(String table, String... familys) throws HBaseException {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            TableName tableName = TableName.valueOf(table);
            if (!admin.tableExists(tableName)) {
                HTableDescriptor tdesc = new HTableDescriptor(tableName);
                for (String family : familys) {
                    HColumnDescriptor cdesc = new HColumnDescriptor(family);
                    tdesc.addFamily(cdesc);
                }
                admin.createTable(tdesc);
            }
        } catch (IOException e) {
            throw new HBaseException("createTable failed tablename: " + table + " " + e.getMessage());
        } finally {
            if (admin != null) {
                try {
                    admin.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * 往hbase中添加多个单元
     *
     * @param table
     * @param rowkey
     * @param family
     * @param records
     * @throws HBaseException
     */
    public static void addCells(String table, String rowkey, String family, Map<String, String> records) throws HBaseException {
        Table htable = null;
        try {
            htable = getHTable(table);
            Put put = new Put(Bytes.toBytes(rowkey));
            Iterator<String> iterator = records.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = records.get(key);
                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(value));
                htable.put(put);
            }
            logger.info("udid为 " + rowkey + " 写入HBase成功！");
        } catch (Exception e) {
            logger.info("udid为 " + rowkey + " 写入HBase失败！", e);
            throw new HBaseException("add cells exception " + e.getMessage());
        } finally {
            if (htable != null) {
                try {
                    htable.close();
                } catch (IOException e) {
                }
            }
        }
    }


    /**
     * 批量添加多条记录
     *
     * @param table
     * @param family
     * @param list
     */
    public static void addRecordsBatch(String table, String family, List<Map<String, String>> list) throws HBaseException {
        BufferedMutator mutator = null;
        try {
            mutator = connection.getBufferedMutator(TableName.valueOf(table));
            ArrayList<Put> puts = new ArrayList<Put>();
            for (Map<String, String> records : list) {
                String rowkey = records.get("rowkey");
                if (rowkey != null) {
                    Put put = new Put(Bytes.toBytes(rowkey));
                    Iterator<String> iterator = records.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        if (!key.equals("rowkey")) {
                            String value = records.get(key);
                            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(value));
                        }
                    }
                    puts.add(put);
                    if (puts.size() >= 1000) {
                        mutator.mutate(puts);
                        mutator.flush();
                        puts.clear();
                    }
                }
            }
            if (puts.size() >= 0) {
                mutator.mutate(puts);
                mutator.flush();
                puts.clear();
            }
        } catch (IOException e) {
            throw new HBaseException("addRecordsBatch failed tablename: " + table + " " + e.getMessage());
        } finally {
            if (mutator != null) {
                try {
                    mutator.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 批量删除多行的同一列族
     *
     * @param table   表名
     * @param rowList 行键集合
     * @param family  列簇
     * @throws HBaseException
     */
    public static void deleteColumnBatch(String table, List<String> rowList, String family) throws HBaseException {
        BufferedMutator mutator = null;
        try {
            mutator = connection.getBufferedMutator(TableName.valueOf(table));
            ArrayList<Delete> delList = new ArrayList<Delete>();
            for (String rowKey : rowList) {
                Delete delete = new Delete(Bytes.toBytes(rowKey));
                delete.addFamily(Bytes.toBytes(family));
                delList.add(delete);
                if (delList.size() > 1000) {
                    mutator.mutate(delList);
                    mutator.flush();
                    delList.clear();
                }
            }

            if (delList.size() > 0) {
                mutator.mutate(delList);
                mutator.flush();
                delList.clear();
            }
        } catch (Exception e) {
            throw new HBaseException("deleteColumnBatch failed tablename:" + table + " " + e.getMessage());
        } finally {
            try {
                mutator.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 批量删除多行的同一个qualfier列
     *
     * @param table     表名
     * @param rowList   行键集合
     * @param family    列簇
     * @param qualifier 列簇限定符
     * @throws HBaseException
     */
    public static void deleteColumnBatch(String table, List<String> rowList, String family, String qualifier) throws HBaseException {
        BufferedMutator mutator = null;
        try {
            mutator = connection.getBufferedMutator(TableName.valueOf(table));
            ArrayList<Delete> delList = new ArrayList<Delete>();
            for (String rowKey : rowList) {
                Delete delete = new Delete(Bytes.toBytes(rowKey));
                delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
                delList.add(delete);
                if (delList.size() > 1000) {
                    mutator.mutate(delList);
                    mutator.flush();
                    delList.clear();
                }
            }

            if (delList.size() > 0) {
                mutator.mutate(delList);
                mutator.flush();
                delList.clear();
            }
        } catch (Exception e) {
            throw new HBaseException("deleteColumnBatch failed tablename:" + table + " " + e.getMessage());
        } finally {
            try {
                mutator.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 批量删除
     *
     * @param table
     * @param columnFamily
     * @param list
     * @throws HBaseException
     */
    public static void deleteColumnBatch(String table, String columnFamily, List<List<String>> list) throws HBaseException {
        BufferedMutator mutator = null;
        try {
            mutator = connection.getBufferedMutator(TableName.valueOf(table));
            ArrayList<Delete> delList = new ArrayList<Delete>();
            for (List<String> records : list) {
                String rowkey = records.get(0);
                if (rowkey != null) {
                    Delete delete = new Delete(Bytes.toBytes(rowkey));
                    for (int i = 1; i < records.size(); i++) {
                        delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(records.get(i)));
                    }
                    delList.add(delete);
                }
                if (delList.size() > 1000) {
                    mutator.mutate(delList);
                    mutator.flush();
                    delList.clear();
                }
            }
            if (delList.size() > 0) {
                mutator.mutate(delList);
                mutator.flush();
                delList.clear();
            }
        } catch (Exception e) {
            throw new HBaseException("deleteColumnBatch failed tablename:" + table + " " + e.getMessage());
        } finally {
            try {
                mutator.close();
            } catch (IOException e) {
            }
        }
    }
}
