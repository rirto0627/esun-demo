package work.rxbear.esundemo.service;

import work.rxbear.esundemo.dto.LikeListRequest;

import java.util.List;
import java.util.Map;

public interface LikeListService {
    void addLikeItem(LikeListRequest request);
    List<Map<String, Object>> getLikeListByUserId(String userId);
    void updateLikeItem(int sn, LikeListRequest request);
    void deleteLikeItem(int sn);
}
