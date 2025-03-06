import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(10);


    executorService.execute(new Runnable() {
      @Override
      public void run() {
        System.out.println("我要抛出异常了");
        throw new RuntimeException("我是故意抛出异常的");
      }
    });
    Thread.sleep(1000);
  }
}
