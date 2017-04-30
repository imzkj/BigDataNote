import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by ZKJ on 2017/4/30 0030.
 */

/**
 * 1. Implement one or more methods named
 * "evaluate" which will be called by Hive.
 * <p>
 * 2. "evaluate" should never be a void method.
 * However it can return "null" if needed.
 *
 * @author XuanYu
 */
public class LowCase extends UDF {
    public Text evaluate( Text str ) {
        // validate
        if (null == str.toString()) {
            return null;
        }
        // lower
        return new Text(str.toString().toLowerCase());
    }

    public static void main( String[] args ) {
        System.out.println(new LowCase().evaluate(new Text("HIVE")));
    }
}
