package work.rxbear.esundemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private Integer code;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, 200, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("success", message, 200, null);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>("error", message, code, null);
    }
}
