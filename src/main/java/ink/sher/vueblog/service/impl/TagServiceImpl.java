package ink.sher.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ink.sher.vueblog.entity.Tag;
import ink.sher.vueblog.mapper.TagMapper;
import ink.sher.vueblog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public List<Tag> getTagsByBlogId(Integer id) {
        List<Integer> tagids = this.baseMapper.getTagIdByBlogId(id);
        return this.baseMapper.selectBatchIds(tagids);
    }

    @Override
    public List<Tag> findTagByKeyword(String keyword) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name")
                .like("name", keyword);

        return this.baseMapper.selectList(wrapper);
    }
}
