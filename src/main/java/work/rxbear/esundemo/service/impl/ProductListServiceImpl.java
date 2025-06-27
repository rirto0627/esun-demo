package work.rxbear.esundemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.rxbear.esundemo.exception.NotFoundException;
import work.rxbear.esundemo.repository.LikeListRepository;
import work.rxbear.esundemo.repository.ProductListRepository;
import work.rxbear.esundemo.service.ProductListService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductListServiceImpl implements ProductListService {

    private final ProductListRepository productListRepository;

    @Override
    public List<Map<String, Object>> getAllProducts() {
        try {
            List<Map<String, Object>> resultList = productListRepository.getAllProducts();
            if (resultList.isEmpty()) {
                throw new NotFoundException("查無商品資料");
            }
            return resultList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("查詢商品失敗：" + e.getMessage(), e);
        }
    }

}
