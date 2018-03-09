import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Parser parser = new Parser();

        List<SubCategory> los = parser.parseCategories();
        List<Good> test = new ArrayList<Good>();
        test.add(parser.getGoods(los).get(0));

        List<Good> result = parser.fullGoodDescription((test));
        System.out.println(result.size());
        for (Good good : result) {
            System.out.println(good.goodName);
            System.out.println(good.description);
            System.out.println(good.price);
            System.out.println();
        }

    }
}
