
import java.util.concurrent.Callable;

public class TaskGood implements Callable<Good> {
    private Good good;
    private String name;

    public TaskGood(Good good, String name) {
        this.good = good;
        this.name = name;
    }


    public Good call() {
        Parser parser = new Parser();
        System.out.println("Thread " + name + " start");
        parser.fillGoodDescription(good);
        System.out.println("Thread " + name + " finished " + good.toString());

        return good;
    }


}
