package pinduoduo;

public class Main {
    public static void main(String[] args) {
//        String url = "http://mobile.yangkeduo.com/search_result.html?search_key="
//                + "手表女";
//        String content = "";
//        for (int i=0; i<1000; i++) {
//            RecordGoods.start(content, url);
//            try {
////                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println(RecordGoods.getGoodsId("http://mobile.yangkeduo.com/goods.html?goods_id=1177907795"));
//        Monitor.start();
    }
}
