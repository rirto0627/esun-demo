package work.rxbear.esundemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.rxbear.esundemo.model.ApiResponse;
import work.rxbear.esundemo.service.ProductListService;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product-list")
public class ProductListController {

    private final ProductListService productListService;

    public ProductListController(ProductListService productListService) {
        this.productListService = productListService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getList() {
        List<Map<String, Object>> products = productListService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("查詢成功", products));
    }


}
