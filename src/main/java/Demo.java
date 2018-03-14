
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        Parser parser = new Parser();

        List<SubCategory> los = parser.parseCategories();
        System.out.println(los.size());
        List<SubCategory> test = new ArrayList<SubCategory>();
        test.add(los.get(0));

        List<Good> log =parser.getGoods(test);
        for (Good good : log) {
            TaskGood task = new TaskGood(good);
            executorService.submit(task);
//            System.out.println(good.getGoodName());
//            System.out.println(good.getPrice());
//            System.out.println(good.getLink());
        }

        for (Good good : log) {
            System.out.println(good.getGoodName());
            System.out.println(good.getPrice());
            System.out.println(good.getLink());
            System.out.println();
        }
    }
//    private static void doWork(Good good){
//        executorService.submit(new TaskGood(good));



}
