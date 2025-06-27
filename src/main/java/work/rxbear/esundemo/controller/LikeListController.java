package work.rxbear.esundemo.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.rxbear.esundemo.dto.LikeListRequest;
import work.rxbear.esundemo.model.ApiResponse;
import work.rxbear.esundemo.service.LikeListService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/like-list")
public class LikeListController {

    private final LikeListService likeListService;

    public LikeListController(LikeListService likeListService) {
        this.likeListService = likeListService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> add(@Valid @RequestBody LikeListRequest request) {
        likeListService.addLikeItem(request);
        return ResponseEntity.ok(ApiResponse.success("新增成功"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getList(@RequestParam String userId) {
        List<Map<String, Object>> result = likeListService.getLikeListByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("查詢成功", result));
    }

    @PutMapping("/{sn}")
    public ResponseEntity<ApiResponse<Object>> update(@PathVariable int sn, @RequestBody LikeListRequest request) {
        likeListService.updateLikeItem(sn, request);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

    @DeleteMapping("/{sn}")
    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable int sn) {
        likeListService.deleteLikeItem(sn);
        return ResponseEntity.ok(ApiResponse.success("刪除成功"));
    }
}
