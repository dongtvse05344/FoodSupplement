/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.contanst.ConstantsCrawler;
import dongtv.crawler.ZshopCategoryCrawler;
import dongtv.crawler.ZshopProductCrawler;
import dongtv.crawler.ZshopProductsCrawler;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.raw.ProductRawDTO;
import dongtv.pageconfig.PageConfig;
import dongtv.service.CrawlService;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author shuu1
 */
public class ZshopThread extends BaseThread {

    private final String IMAGE_PATH = "images/zshop/";
    private final ServletContext context;
    private final CrawlService crawlService;

    private final ZshopCategoryCrawler zshopCategoryCrawler;
    private final ZshopProductsCrawler zshopProductsCrawler;
    private final ZshopProductCrawler zshopProductCrawler;
    
    public ZshopThread(ServletContext context, PageConfig pageConfig) {
        this.context = context;
        this.crawlService = new CrawlService();
        zshopCategoryCrawler = new ZshopCategoryCrawler(context, pageConfig.getXCategories());
        zshopProductsCrawler = new ZshopProductsCrawler(context, pageConfig.getXProducts(), pageConfig.getXPage());
        zshopProductCrawler = new ZshopProductCrawler(context, pageConfig.getXProduct());
    }

    @Override
    public void run() {
        try {
            Map<String, String> categories = zshopCategoryCrawler.getCategories(ConstantsCrawler.ZSHOP);
            for (Map.Entry<String, String> cateEntry : categories.entrySet()) {
                if (cateEntry.getValue().toLowerCase().indexOf("máy ảnh") == 0) {
                    Map<String, String> pages = zshopProductsCrawler.getPages(cateEntry.getKey());
                    if (pages == null) {
                        pages = new HashMap<>();
                    }
                    CategoryDTO categoryDb
                            = //                        null;
                            crawlService.createCategory(cateEntry.getValue());
                    pages.put(cateEntry.getKey(), "x");
                    for (Map.Entry<String, String> pageEntry : pages.entrySet()) {
                        Map<String, ProductRawDTO> products = zshopProductsCrawler.getProducts(pageEntry.getKey());
                        if (products != null) {
                            for (Map.Entry<String, ProductRawDTO> proEntry : products.entrySet()) {
                                ProductRawDTO dto = proEntry.getValue();
                                dto.setCategoryId(categoryDb);
                                getProduct(dto);
//                            break;
                            }
                        }

//                    break;
                    }
//                break;
                }
            }
            long CRAWLING_INTERVAL = 1;
            ZshopThread.sleep(CRAWLING_INTERVAL);
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ZshopThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int count = 1;

    private void getProduct(ProductRawDTO dto) {
        System.out.println(count++ + dto.getOriginalLink());
        String imageUrl = dto.getImage();
        imageUrl = imageUrl.split("\\?")[0];
        dto.setImage(imageUrl);
        this.imageHande(dto, IMAGE_PATH, context); 
        Map<String, String> product = zshopProductCrawler.getProduct(dto.getOriginalLink());
        if (product != null && product.get("DES") !=null) {
            dto.setDescription(product.get("DES"));
            this.crawlService.getParamtoProduct(dto, product.get("DES"));
            crawlService.createProduct(dto);

        }
    }

}
