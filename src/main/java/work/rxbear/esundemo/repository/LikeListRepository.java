package work.rxbear.esundemo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import work.rxbear.esundemo.dto.LikeListRequest;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class LikeListRepository {

    private final DataSource dataSource;

    public void addLikeItem(LikeListRequest request) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL AddLikeItem(?, ?, ?, ?)}")) {

            stmt.setString(1, request.getUserId());
            stmt.setInt(2, request.getProductNo());
            stmt.setString(3, request.getAccount());
            stmt.setInt(4, request.getOrderNum());

            stmt.execute();
        }
    }


    public List<Map<String, Object>> getLikeListByUserId(String userId) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL GetLikeListByUserId(?)}")) {

            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("sn", rs.getInt("SN"));
                    row.put("productNo", rs.getInt("ProductNo"));
                    row.put("productName", rs.getString("ProductName"));
                    row.put("productPrice", rs.getString("ProductPrice"));
                    row.put("productFeeRate", rs.getString("ProductFeeRate"));
                    row.put("account", rs.getString("Account"));
                    row.put("orderNum", rs.getInt("OrderName"));
                    row.put("totalFee", rs.getBigDecimal("TotalFee"));
                    row.put("totalAmount", rs.getBigDecimal("TotalAmount"));
                    row.put("email", rs.getString("Email"));
                    resultList.add(row);
                }
            }
        }
        return resultList;
    }

    public void updateLikeItem(int sn, LikeListRequest request) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL UpdateLikeItem(?, ?, ?, ?)}")) {

            stmt.setInt(1, sn);
            stmt.setInt(2, request.getProductNo());
            stmt.setString(3, request.getAccount());
            stmt.setInt(4, request.getOrderNum());

            stmt.execute();
        }
    }


    public int deleteLikeItem(int sn) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL DeleteLikeItem(?, ?)}")) {

            stmt.setInt(1, sn);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.execute();

            return stmt.getInt(2);
        }
    }


}