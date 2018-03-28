
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        Parser parser = new Parser();

        List<SubCategory> subCategories = parser.parseCategories();
        System.out.println(subCategories.size());
        List<Good> log = parser.getGoods(subCategories);


        Collection<TaskGood> tasks = new ArrayList<TaskGood>();
        for (int i = 0; i < log.size(); i++) {
            Good good = log.get(i);
            TaskGood task = new TaskGood(good, "Thread#" + i);
            tasks.add(task);
        }

        executorService.invokeAll(tasks);
        executorService.shutdown();

        long finish = System.currentTimeMillis();

        System.out.println("done in " + (finish - start) / 1000);
    }
}