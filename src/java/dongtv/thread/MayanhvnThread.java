package dongtv.thread;

import dongtv.contanst.ConstantsCrawler;
import dongtv.crawler.MayanhvnCategoryCrawler;
import dongtv.crawler.MayanhvnProductCrawler;
import dongtv.crawler.MayanhvnProductsCrawler;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.raw.ProductRawDTO;
import dongtv.pageconfig.PageConfig;
import dongtv.service.CrawlService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tran Dong
 */
public class MayanhvnThread extends BaseThread {

    private final String IMAGE_PATH = "images/mayanhvn/";
    private final ServletContext context;
    private final MayanhvnCategoryCrawler mayanhvnCategoryCrawler;
    private final MayanhvnProductsCrawler mayanhvnProductsCrawler;
    private final MayanhvnProductCrawler mayanhvnProductCrawler;
    private final CrawlService crawlService;

    public MayanhvnThread(ServletContext context, PageConfig pageConfig) {
        this.context = context;
        this.crawlService = new CrawlService();
        mayanhvnCategoryCrawler = new MayanhvnCategoryCrawler(context, pageConfig.getXCategories());
        mayanhvnProductsCrawler = new MayanhvnProductsCrawler(context, pageConfig.getXProducts(), pageConfig.getXPage());
        mayanhvnProductCrawler = new MayanhvnProductCrawler(context, pageConfig.getXProduct());
    }
    private int count = 1;

    private void getProduct(ProductRawDTO dto) {
        System.out.println(count++ + dto.getOriginalLink());
        this.imageHande(dto, IMAGE_PATH, context);
        Map<String, String> product = mayanhvnProductCrawler.getProduct(dto.getOriginalLink());
        this.crawlService.getParamtoProduct(dto, product.get("DES"));
        dto.setDescription(product.get("DES"));
        crawlService.createProduct(dto);
    }

    @Override
    public void run() {
        try {

            Map<String, String> categories = mayanhvnCategoryCrawler.getCategories(ConstantsCrawler.MAYANHVN);
            for (Map.Entry<String, String> categoryEntry : categories.entrySet()) {
                if (categoryEntry.getValue().contains("Ống")) {
                    continue;
                }
                CategoryDTO categoryDb
                        = //                        null;
                        crawlService.createCategory(categoryEntry.getValue());
                Map<String, String> pages = mayanhvnProductsCrawler.getPages(categoryEntry.getKey());
                if (pages == null) {
                    pages = new HashMap<>();
                }
                pages.put(categoryEntry.getKey(), "w");
                for (Map.Entry<String, String> pageEntry : pages.entrySet()) {
                    Map<String, ProductRawDTO> products = mayanhvnProductsCrawler.getProducts(pageEntry.getKey());
                    for (Map.Entry<String, ProductRawDTO> productEntry : products.entrySet()) {
                        ProductRawDTO dto = productEntry.getValue();
                        dto.setCategoryId(categoryDb);
                        getProduct(dto);
//                        return;
                    }
//                    return;
                }
//                return;
            }
            long CRAWLING_INTERVAL = 1;
            MayanhvnThread.sleep(CRAWLING_INTERVAL);
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MayanhvnThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
