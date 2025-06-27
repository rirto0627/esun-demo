package work.rxbear.esundemo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
@Repository
@RequiredArgsConstructor
public class ProductListRepository {
    private final DataSource dataSource;

    public List<Map<String, Object>> getAllProducts() throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL GetAllProducts()}")) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("productNo", rs.getInt("productNo"));
                    row.put("productName", rs.getString("ProductName"));
                    row.put("price", rs.getBigDecimal("Price"));
                    row.put("feeRate", rs.getBigDecimal("FeeRate"));
                    resultList.add(row);
                }
            }
        }

        return resultList;
    }

}
