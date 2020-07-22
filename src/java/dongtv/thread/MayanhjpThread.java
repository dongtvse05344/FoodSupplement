/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.contanst.ConstantsCrawler;
import dongtv.crawler.MayanhjpCategoryCrawler;
import dongtv.crawler.MayanhjpProductsCrawler;
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
 * @author shuu1
 */
public class MayanhjpThread extends BaseThread {

    private final String IMAGE_PATH = "images/mayanhjp/";
    private final ServletContext context;
    private final CrawlService crawlService;
    private final MayanhjpCategoryCrawler mayanhjpCategoryCrawler;
    private final MayanhjpProductsCrawler mayanhjpProductsCrawler;

    public MayanhjpThread(ServletContext context, PageConfig pageConfig) {
        this.context = context;
        this.crawlService = new CrawlService();
        mayanhjpCategoryCrawler = new MayanhjpCategoryCrawler(context, pageConfig.getXCategories());
        mayanhjpProductsCrawler = new MayanhjpProductsCrawler(context, pageConfig.getXProducts());
    }

    private int count = 1;

    @Override
    public void run() {
        try {

            Map<String, String> categories = mayanhjpCategoryCrawler.getCategories(ConstantsCrawler.MAYANHJP);
            for (Map.Entry<String, String> cateEntry : categories.entrySet()) {
                CategoryDTO cateDto = crawlService.createCategory(cateEntry.getValue());
                Map<String, ProductRawDTO> products = mayanhjpProductsCrawler.getProducts(cateEntry.getKey());
                if (products != null) {
                    for (Map.Entry<String, ProductRawDTO> proEntry : products.entrySet()) {
                        ProductRawDTO p = proEntry.getValue();
                        p.setCategoryId(cateDto);
                        getProduct(p);
                    }
                }

//                break;
            }

            long CRAWLING_INTERVAL = 1;
            MayanhjpThread.sleep(CRAWLING_INTERVAL);
            synchronized (MayanhjpThread.getInstance()) {
                while (MayanhjpThread.isSuspended()) {
                    MayanhjpThread.getInstance().wait();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MayanhjpThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getProduct(ProductRawDTO dto) {
        System.out.println(count++ + dto.getOriginalLink());
        this.imageHande(dto, IMAGE_PATH, context);
        this.crawlService.getParamtoProduct(dto, dto.getDescription());
        crawlService.createProduct(dto);
    }

}
