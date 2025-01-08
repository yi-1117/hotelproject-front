package tw.com.ispan.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.com.ispan.domain.ProductBean;
import tw.com.ispan.dto.ProductResponse;
import tw.com.ispan.service.ProductService;
import tw.com.ispan.util.DatetimeConverter;


@RestController
@RequestMapping("/ajax/pages/products")
public class ProductAjaxController {
    @Autowired
    private ProductService productService;

    @PostMapping("/find")
    public ProductResponse find(@RequestBody String json) {
        ProductResponse responseBean = new ProductResponse();

        // JSONObject responseJson = new JSONObject();
        long count = productService.count(json);
        responseBean.setCount(count);
        // responseJson.put("count", count);

        List<ProductBean> products = productService.find(json);
        if (products != null && !products.isEmpty()){
            responseBean.setList(products);

        }else{
            responseBean.setList(new ArrayList<>());
        }

        return responseBean;
        // JSONArray array = new JSONArray();
        // List<ProductBean> products = productService.find(json);
        // if (products != null && !products.isEmpty()) {
        //     for (ProductBean product : products) {
        //         String make = DatetimeConverter.toString(product.getMake(), "yyyy-MM-dd");
        //         JSONObject item = new JSONObject()
        //                 .put("id", product.getId())
        //                 .put("name", product.getName())
        //                 .put("price", product.getPrice())
        //                 .put("make", make)
        //                 .put("expire", product.getExpire());
        //         array = array.put(item);
        //     }
        // }
        // responseJson.put("list", array);
        // return responseJson.toString();
    }

    @PostMapping
    public String create(@RequestBody String json) {
        JSONObject responseJson = new JSONObject();

        JSONObject obj = new JSONObject(json);
        Integer id = obj.isNull("id") ? null : obj.getInt("id");

        if (id == null) {
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");
        } else if (productService.exists(id)) {
            responseJson.put("success", false);
            responseJson.put("message", "id已存在");
        } else {
            ProductBean insert = productService.create(json);
            if (insert == null) {
                responseJson.put("success", false);
                responseJson.put("message", "新增失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "新增成功");
            }
        }
        return responseJson.toString();
    }


    @DeleteMapping("/{pk}")
    public String remove(@PathVariable("pk") Integer id) {
        JSONObject responseJson = new JSONObject();

        if (id == null) {
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");
        } else if (!productService.exists(id)) {
            responseJson.put("success", false);
            responseJson.put("message", "id不存在");
        } else {
            boolean delete = productService.remove(id);
            if (!delete) {
                responseJson.put("success", false);
                responseJson.put("message", "刪除失敗");
            } else {
                responseJson.put("success", true);
                responseJson.put("message", "刪除成功");
            }
        }

        return responseJson.toString();
    }




    @PutMapping("/{id}")
    public String modify(@PathVariable Integer id, @RequestBody String body) {
        JSONObject responseJson = new JSONObject();
        if(id == null){
            responseJson.put("success", false);
            responseJson.put("message", "id是必要欄位");

        }else if(!productService.exists(id)){
            responseJson.put("success", false);
            responseJson.put("message", "id不存在");
        }else{
            ProductBean product = productService.modify(body);
            if(product == null){
                responseJson.put("success", false);
                responseJson.put("message", "修改失敗");
            }else{
                responseJson.put("success", true);
                responseJson.put("message", "修改成功");
            }

        }
        
        return responseJson.toString();
    }



    @GetMapping("/{id}")
    public String findByPrimaryKey(@PathVariable(name = "id") Integer id) {
        JSONObject responseJson = new JSONObject();
        JSONArray array = new JSONArray();
        if (id != null) {
            ProductBean product = productService.findById(id);
            if (product != null) {
                String make = DatetimeConverter.toString(product.getMake(), "yyyy-MM-dd");
                JSONObject item = new JSONObject()
                        .put("id", product.getId())
                        .put("name", product.getName())
                        .put("price", product.getPrice())
                        .put("make", make)
                        .put("expire", product.getExpire());

                array = array.put(item);
            }
        }
        responseJson = responseJson.put("list", array);
        return responseJson.toString();

    }
}
