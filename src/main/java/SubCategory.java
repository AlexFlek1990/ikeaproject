import java.util.List;
import java.util.Map;

public class SubCategory {

    private String name;
    private String link;
    private String categoryName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public SubCategory(String categoryName, String name, String link) {
        this.name = name;
        this.link = link;
        this.categoryName = categoryName;
    }


}
