public class TaskGood implements Runnable {
    private Good good;
    Parser parser = new Parser();


    public TaskGood(Good good) {
        this.good = good;
    }


//    @Override
    public void run() {
        System.out.println("new thread");
        parser.fillGoodDescription(good);
//        parser.fillGoodDescription(good);
//        System.out.println(good.getLink());
//        System.out.println(good.getGoodName());
//        System.out.println();

    }

}
