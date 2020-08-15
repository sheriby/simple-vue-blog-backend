package ink.sher.vueblog.controller;


import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.entity.User;
import ink.sher.vueblog.service.UserService;
import ink.sher.vueblog.util.JwtUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public Result login(@RequestBody User user) {
        user = userService.verifyUser(user);
        if (user != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", user.getId().toString());
            map.put("name", user.getName());
            String token = JwtUtils.getToken(map);

            return Result.success(token);
        } else {
            return Result.failure("认证失败");
        }
    }

    @RequestMapping("token")
    public Result token(String token) {
        try {
            JwtUtils.verifyToken(token);
            return Result.success("Verify Success");
        } catch (Exception e) {
            return Result.failure("Verify Failure");
        }
    }
}
