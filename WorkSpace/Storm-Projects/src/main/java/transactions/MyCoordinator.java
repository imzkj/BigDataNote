package transactions;

import backtype.storm.transactional.ITransactionalSpout;
import backtype.storm.utils.Utils;

import java.math.BigInteger;

/**
 * Created by ZKJ on 2017/5/11 0011.
 */
public class MyCoordinator implements ITransactionalSpout.Coordinator<MyMeta> {

    public static int BATCH_NUM = 10;

    //启动事务的ID  txid 默认从0开始
    //prevMetadata  上一个源数据

    public MyMeta initializeTransaction( BigInteger txid, MyMeta prevMetadata ) {
        long beginPoint = 0;
        //如果为空则是第一个事务，开始位置从 0开始
        //否则从上一个事务的开始位置加上批处理个数处（即结尾处）开始
        if (null==prevMetadata) {
            beginPoint = 0;
        } else {
            beginPoint = prevMetadata.getBeginPoint() + prevMetadata.getNum();
        }
        MyMeta mata = new MyMeta();
        mata.setBeginPoint(beginPoint);
        mata.setNum(BATCH_NUM);
        System.err.println("启动一个事务：" + mata.toString());
        return mata;
    }

    //返回TRUE则事务开始

    public boolean isReady() {
        Utils.sleep(2000);
        return true;
    }


    public void close() {

    }
}
