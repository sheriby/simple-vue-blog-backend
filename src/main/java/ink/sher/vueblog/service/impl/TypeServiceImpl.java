package ink.sher.vueblog.service.impl;

import ink.sher.vueblog.entity.Type;
import ink.sher.vueblog.mapper.TypeMapper;
import ink.sher.vueblog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sher
 * @since 2020-08-08
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
