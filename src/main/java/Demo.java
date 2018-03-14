
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
todo: хорошо бы подчистить весь закоммнетированный код  - плохой это тон -  код в перемешку с закоментированными кусками
todo: в идеале начать пользовать логгером
 */
public class Demo {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        Parser parser = new Parser();

        List<SubCategory> los = parser.parseCategories(); //todo: нормальное имя
        System.out.println(los.size());
        List<Good> log = parser.getGoods(los);


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

//        System.out.println("----------------Results-----------------");
//
//        for (Good good : log) {
//            System.out.println(good.getGoodName());
//            System.out.println(good.getPrice());
//            System.out.println(good.getLink());
//            System.out.println();
//        }
//
//        System.out.println("----------------Results DONE------------");
    }


}
