package ink.sher.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ink.sher.vueblog.entity.User;
import ink.sher.vueblog.mapper.UserMapper;
import ink.sher.vueblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sher
 * @since 2020-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User verifyUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", user.getName()).eq("password", user.getPassword());

        return this.baseMapper.selectOne(wrapper);
    }
}
