package ZKJ;

/**
 * Created by ZKJ on 2017/9/14.
 */
public class Main {
    public void show() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main Show");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.show();
    }
}
