/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.contanst.ContanstCrawler;
import dongtv.crawler.DemxanhCategoryCrawler;
import dongtv.crawler.DemxanhProductCrawler;
import dongtv.crawler.DemxanhProductsCrawler;
import dongtv.dao.CategoryDao;
import dongtv.dao.ProductDao;
import dongtv.dao.ProductRawDao;
import dongtv.dao.VolumeDao;
import dongtv.dao.VolumeRawDao;
import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductRawDTO;
import dongtv.dto.VolumeDTO;
import dongtv.dto.VolumeRawDTO;
import dongtv.util.ImageUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 *
 * @author Tran Dong
 */
public class DemxanhThread extends BaseThread {

    private final ServletContext context;
    public static String DESCRIPTION_TAG = "DESCRIPTION";
    private final String IMAGE_PATH = "images/demxinh/";

    private int keep = 1;

    public DemxanhThread(ServletContext context) {
        this.context = context;
    }

    private void getProduct(DemxanhProductCrawler productCrawler, Map.Entry<String, ProductRawDTO> pEntry, CategoryDTO categoryDb) {
        keep++;
//        if (keep > 2) {
//            return;
//        }
        Map<String, String> productInfo = productCrawler.getProduct(pEntry.getKey());
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
//        ProductDTO productDb = ProductDao.getInstance().create(pEntry.getValue());
        ProductRawDTO productRawDTO = ProductRawDao.getInstance().create(pEntry.getValue());

        List<VolumeRawDTO> volumeDTOs = productCrawler.getProductVolume( pEntry.getKey());
        for (VolumeRawDTO volumeDTO : volumeDTOs) {
            volumeDTO.setProductRawId(productRawDTO);
//            //insert volume
            VolumeRawDao.getInstance().create(volumeDTO);
        }
    }
    private DemxanhCategoryCrawler categoryCrawler;
    private DemxanhProductsCrawler productsCrawler;
    private DemxanhProductCrawler productCrawler;

    @Override
    public void run() {
        try {
            categoryCrawler = new DemxanhCategoryCrawler(context);
            productsCrawler = new DemxanhProductsCrawler(context);
            productCrawler = new DemxanhProductCrawler(context);
            Map<String, String> categories = categoryCrawler.getCategories(ContanstCrawler.DEMXANH);
            for (Map.Entry<String, String> entry : categories.entrySet()) {
                //insert category
                CategoryDTO categoryDb = CategoryDao.getInstance().create(new CategoryDTO(entry.getValue()));
                Map<String, ProductRawDTO> products = productsCrawler.getProducts(entry.getKey());
                for (Map.Entry<String, ProductRawDTO> pentry : products.entrySet()) {
                    try {
                        this.getProduct(productCrawler, pentry, categoryDb);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
            long CRAWLING_INTERVAL = 1;
            DemxanhThread.sleep(CRAWLING_INTERVAL);
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
