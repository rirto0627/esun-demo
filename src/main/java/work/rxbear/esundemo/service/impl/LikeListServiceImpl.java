package work.rxbear.esundemo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.rxbear.esundemo.dto.LikeListRequest;
import work.rxbear.esundemo.exception.BadRequestException;
import work.rxbear.esundemo.exception.NotFoundException;
import work.rxbear.esundemo.repository.LikeListRepository;
import work.rxbear.esundemo.service.LikeListService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
@Service
@RequiredArgsConstructor
public class LikeListServiceImpl implements LikeListService {

    private final LikeListRepository likeListRepository;

    @Override
    @Transactional
    public void addLikeItem(LikeListRequest request) {
        try {
            likeListRepository.addLikeItem(request);
        } catch (BadRequestException e) {
            throw e;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotFoundException("找不到指定帳號或使用者");
        } catch (Exception e) {
            throw new RuntimeException("新增失敗：" + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getLikeListByUserId(String userId) {
        try {
            List<Map<String, Object>> resultList = likeListRepository.getLikeListByUserId(userId);
            if (resultList.isEmpty()) {
                throw new NotFoundException("查無該使用者或無資料");
            }
            return resultList;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("查詢失敗：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void updateLikeItem(int sn, LikeListRequest request) {
        try {
            likeListRepository.updateLikeItem(sn, request);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NotFoundException("找不到該 SN 或產品資訊");
        } catch (Exception e) {
            throw new RuntimeException("更新失敗：" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteLikeItem(int sn) {
        try {
            int affected = likeListRepository.deleteLikeItem(sn);
            if (affected == 0) {
                throw new NotFoundException("SN 不存在");
            }
        } catch (SQLException e) {
            throw new RuntimeException("刪除失敗：" + e.getMessage(), e);
        }
    }


}

