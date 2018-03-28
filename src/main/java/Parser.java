import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    /**
     * @return
     */
    public List<SubCategory> parseCategories() {
        List<SubCategory> list = new ArrayList<SubCategory>();

        Document document;
        try {
            document = Jsoup.connect("http://www.ikea.com/ru/ru/allproducts/index.html").get();
            Elements div = document.getElementsByClass("allGoodsPage_tab");
            Elements categories = div.get(0).getElementsByClass("allGoodsPage_item");

            for (Element element : categories) {
                String title = element.getElementsByClass("allGoodsPage_itemTitle").text();
                Elements div_links = element.getElementsByClass("allGoodsPage_itemLinks");

                for (Element div_link : div_links) {
                    Elements subLinks = div_link.getElementsByTag("a");
                    for (Element subLink : subLinks) {
                        String name = subLink.getElementsByTag("a").text();
                        String link = subLink.getElementsByTag("a").attr("href");
                        list.add(new SubCategory(title, name, link));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Good> getGoods(List<SubCategory> categoryList) {
        Document document;
        List<Good> list = new ArrayList<Good>();
        for (SubCategory subCategory : categoryList) {
            try {
                document = Jsoup.connect(subCategory.getLink()).get();
            } catch (HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    System.out.println("No page found");
                    continue;
                } else {
                    throw new RuntimeException("shit...", e);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }

            Elements div = document.getElementsByClass("productLists");

            for (Element element : div) {
                Elements goods = element.getElementsByClass("product");
                for (Element good : goods) {
                    Element a = good.getElementsByTag("a").get(0);
                    String linkGood = a.attr("href");
                    Good g = new Good();
                    g.setLink(linkGood);
                    g.setCategoryName(subCategory.getCategoryName());
                    g.setSubCategoryName(subCategory.getName());
                    list.add(g);
                }
            }
        }
        return list;
    }

    public Good fillGoodDescription(Good good) {
        Document document = null;

            try {
                document = Jsoup.connect("http://www.ikea.com/" + good.getLink()).get();
            } catch (HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    System.out.println("No page found");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements div = document.getElementsByClass("addList");
            Element description = div.get(0);
            String name =description.getElementsByClass("productName").text();
            String price = description.getElementsByClass("packagePrice").text();
            good.setGoodName(name);
            good.setPrice(price);
        return good;
    }
}


