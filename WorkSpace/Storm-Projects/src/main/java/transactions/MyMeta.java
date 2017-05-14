package transactions;

import java.io.Serializable;

/**
 * Created by ZKJ on 2017/5/11 0011.
 */
public class MyMeta implements Serializable{

    private static final long serialVersionUID = -5979605826697061010L;

    private long beginPoint ;//事务开始位置

    private int num ;//batch 的tuple个数

    @Override
    public String toString() {
        return "MyMeta{" +
                "beginPoint=" + beginPoint +
                ", num=" + num +
                '}';
    }

    public long getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint( long beginPoint ) {
        this.beginPoint = beginPoint;
    }

    public int getNum() {
        return num;
    }

    public void setNum( int num ) {
        this.num = num;
    }
}
