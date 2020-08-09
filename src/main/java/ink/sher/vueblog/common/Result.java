package ink.sher.vueblog.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title Result
 * @Package ink.sher.vueblog.common
 * @Description result
 * @Author sher
 * @Date 2020/08/08 4:45 PM
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result {

    private int code;
    private String message;
    private Object data;

    public static Result success(String message, String data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);

        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMessage("OK");
        result.setData(data);

        return result;
    }

    public static Result failure(String message) {
        Result result = new Result();
        result.setCode(400);
        result.setMessage(message);

        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.setCode(404);
        result.setMessage("Not Found");

        return result;
    }
}
