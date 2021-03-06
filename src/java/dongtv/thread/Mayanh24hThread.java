/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.contanst.ConstantsCrawler;
import dongtv.crawler.Mayanh24hCategoryCrawler;
import dongtv.crawler.Mayanh24hProductCrawler;
import dongtv.crawler.Mayanh24hProductsCrawler;
import dongtv.crawler.ZshopProductsCrawler;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.raw.ProductRawDTO;
import dongtv.pageconfig.PageConfig;
import dongtv.service.CrawlService;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Tran Dong
 */
public class Mayanh24hThread extends BaseThread {

    private final String IMAGE_PATH = "images/mayanh24h/";
    private final ServletContext context;
    private final Mayanh24hProductsCrawler productsCrawler;
    private final Mayanh24hCategoryCrawler categoryCrawler;
    private final Mayanh24hProductCrawler productCrawler;
    private int count = 1;
    private CrawlService crawlService;

    public Mayanh24hThread(ServletContext context, PageConfig pageConfig) {
        this.context = context;
        this.crawlService = new CrawlService();

        categoryCrawler = new Mayanh24hCategoryCrawler(context, pageConfig.getXCategories());
        productsCrawler = new Mayanh24hProductsCrawler(context, pageConfig.getXProducts(), pageConfig.getXPage());
        productCrawler = new Mayanh24hProductCrawler(context, pageConfig.getXProduct());
    }

    private void getProduct(ProductRawDTO dto) {
        System.out.println(count++ + " :" + dto.getOriginalLink());
        this.imageHande(dto, IMAGE_PATH, context);

        Map<String, String> product = productCrawler.getProduct(dto.getOriginalLink());
//        System.out.println("PARAMS: " + product.get("PARAMS"));

        this.crawlService.getParamtoProduct(dto, product.get("PARAMS"));
        dto.setDescription(product.get("DES"));
        dto.setName(product.get("NAME"));
        crawlService.createProduct(dto);
    }

    @Override
    public void run() {
        try {
            Map<String, String> categories = categoryCrawler.getCategories(ConstantsCrawler.MAYANH24H);
            for (Map.Entry<String, String> categoryEntry : categories.entrySet()) {
                CategoryDTO categoryDb
                        = //                        null;
                        crawlService.createCategory(categoryEntry.getValue());
                Map<String, String> pages = productsCrawler.getPages(categoryEntry.getKey());
                pages.put(categoryEntry.getKey(), "w");
                for (Map.Entry<String, String> pageEntry : pages.entrySet()) {
                    System.out.println(pageEntry.getKey());
                    Map<String, ProductRawDTO> products = productsCrawler.getProducts(pageEntry.getKey());
                    for (Map.Entry<String, ProductRawDTO> productEntry : products.entrySet()) {
                        ProductRawDTO dto = productEntry.getValue();
                        dto.setCategoryId(categoryDb);
                        getProduct(dto);
//                        break;
                    }
//                    break;
                }
//                break;
            }
            long CRAWLING_INTERVAL = 1;
            Mayanh24hThread.sleep(CRAWLING_INTERVAL);
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Mayanh24hThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
