package ink.sher.vueblog.service.impl;

import ink.sher.vueblog.entity.Type;
import ink.sher.vueblog.mapper.TypeMapper;
import ink.sher.vueblog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
