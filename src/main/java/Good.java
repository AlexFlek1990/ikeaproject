public class Good  {
    String goodName;
    String link;
    String price;
    String description;
    String id;
    String categoryName;
    String subCategoryName;

    public Good(String link, String categoryName, String subCategoryName) {
        this.link = link;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
    }
}
