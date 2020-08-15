package ink.sher.vueblog.service;

import ink.sher.vueblog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {

    User verifyUser(User user);
}
