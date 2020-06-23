/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.contanst.ConstantsCrawler;
import dongtv.crawler.LienaCategoryCrawler;
import dongtv.crawler.LienaProductCrawler;
import dongtv.crawler.LienaProductsCrawler;
import dongtv.dao.CategoryDao;
import dongtv.dao.ProductDao;
import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.util.ImageUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Tran Dong
 */
public class LienaThread extends BaseThread {

    private final ServletContext context;
    public static String DESCRIPTION_TAG = "DESCRIPTION";
    private int keep = 1;
    private final String IMAGE_PATH = "images/liena/";

    public LienaThread(ServletContext context) {
        this.context = context;
    }

    private void getProduct(LienaProductCrawler lienaProductCrawler, Map.Entry<String, ProductDTO> pEntry, CategoryDTO categoryDb) throws Exception {
        keep++;
//        if (keep > 2) {
//            return;
//        }
        Map<String, String> productInfo = lienaProductCrawler.getProduct(pEntry.getKey());
        if (productInfo != null) {
            String des = productInfo.get(DESCRIPTION_TAG);
            pEntry.getValue().setDescription(des);
        }
        pEntry.getValue().setCategoryId(categoryDb);
        //insert product
        try {
            String filename = IMAGE_PATH + new Date().getTime() + ".jpg";
            String realPath = context.getRealPath("/");
            ImageUtils.saveImage(pEntry.getValue().getImage(), realPath + filename);
            pEntry.getValue().setImage(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProductDTO productDb = ProductDao.getInstance().create(pEntry.getValue());
//
//        List<VolumeDTO> volumeDTOs = lienaProductCrawler.getProductVolume(pEntry.getKey());
//        for (VolumeDTO volumeDTO : volumeDTOs) {
//            volumeDTO.setProductId(productDb);
//            //insert volume 
//            VolumeDao.getInstance().create(volumeDTO);
//        }
    }
    private LienaCategoryCrawler categoryCrawler;
    private LienaProductsCrawler productsCrawler;
    private LienaProductCrawler lienaProductCrawler;

    @Override
    public void run() {
        try {
            categoryCrawler = new LienaCategoryCrawler(context);
            productsCrawler = new LienaProductsCrawler(context);
            lienaProductCrawler = new LienaProductCrawler(context);
            Map<String, String> categories = categoryCrawler.getCategories(ConstantsCrawler.LIENA);
            for (Map.Entry<String, String> categoryEntry : categories.entrySet()) {
                if (!categoryEntry.getKey().contains("tel")
                        && !categoryEntry.getKey().contains("ve-chung-toi")
                        && !categoryEntry.getKey().contains("nem-IKI")
                        && !categoryEntry.getKey().contains("cua-hang")
                        && !categoryEntry.getKey().contains("ngu-ngon")) {
                    //insert category
                    CategoryDTO categoryDb = CategoryDao.getInstance().create(new CategoryDTO(categoryEntry.getValue()));
                    Map<String, String> pages = productsCrawler.getPages(categoryEntry.getKey());
                    if (pages == null || pages.isEmpty()) {
                        Map<String, ProductDTO> products = productsCrawler.getProducts(categoryEntry.getKey());
                        for (Map.Entry<String, ProductDTO> productEntry : products.entrySet()) {
                            try {
                                getProduct(lienaProductCrawler, productEntry, categoryDb);
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            }
                        }
                    } else {
                        for (Map.Entry<String, String> pageEntry : pages.entrySet()) {
                            Map<String, ProductDTO> products = productsCrawler.getProducts(pageEntry.getKey());
                            for (Map.Entry<String, ProductDTO> productEntry : products.entrySet()) {
                                try {
                                    getProduct(lienaProductCrawler, productEntry, categoryDb);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            long CRAWLING_INTERVAL = 1;
            LienaThread.sleep(CRAWLING_INTERVAL);
            synchronized (BaseThread.getInstance()) {
                while (BaseThread.isSuspended()) {
                    BaseThread.getInstance().wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
