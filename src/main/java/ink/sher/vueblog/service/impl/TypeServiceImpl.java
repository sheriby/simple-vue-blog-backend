package ink.sher.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ink.sher.vueblog.entity.Type;
import ink.sher.vueblog.mapper.TypeMapper;
import ink.sher.vueblog.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Override
    public List<Type> findTypeByKeyword(String keyword) {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name")
                .like("name", keyword);

        return this.baseMapper.selectList(wrapper);
    }
}
