/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service.test;

import dongtv.dto.ProductDTO;
import dongtv.service.CrawlService;
import dongtv.service.ProductService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class Test {

    public static void main(String[] args) throws Exception {
        CrawlService crawlService = new CrawlService();
        ProductService productService = new ProductService();
       
//        boolean a = crawlService.compareName(
//                "Sony Alpha A7S Mark II, Mới 100% (Chính hãng)" , 
//                "Sony A7S Mark II ( Hàng chính hãng )"
//        );
//        System.out.println(a);
//        List<ProductDTO> products = productService.getPage("", 1);
//        for (int i = 0; i < products.size() - 1; i++) {
//            System.out.println(products.get(0));
//
//            System.out.println(products.get(i + 1));
//
//            System.out.println(productService.cosinOf2Vector(products.get(0), products.get(i + 1)));
//            System.out.println("");
//        }
    }
}
