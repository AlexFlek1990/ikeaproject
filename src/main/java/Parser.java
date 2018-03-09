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
//                Category cat = new Category(title);
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
        Document document=null;
        List<Good> list = new ArrayList<Good>();
        for (SubCategory subCategory : categoryList) {
            try {
                document = Jsoup.connect(subCategory.link).get();
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
                    list.add(new Good(linkGood, subCategory.categoryName, subCategory.name));
                }
            }


        }
        return list;
    }

    public List<Good> fullGoodDescription(List<Good> list) {
        int i=0;
        List<Good> result = new ArrayList<Good>();
        Document document = null;

        for (Good good : list) {
            try {
                document = Jsoup.connect("http://www.ikea.com/"+good.link).get();
            } catch (HttpStatusException e) {
                if (e.getStatusCode() == 404) {
                    System.out.println("No page found");
                    continue;
                }

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Elements div = document.getElementsByClass("addList");
            Element description = div.get(0);
            good.goodName = description.getElementsByClass("productName").text();
            good.price = description.getElementsByClass("packagePrice").text();
//            Element desc= description.getElementsByClass("salesArguments").get(0);
//            good.description = desc.getElementsByTag("t").text();
            result.add(good);
            System.out.println("add description "+i++);

        }
        return result;
    }
}


