package ink.sher.vueblog.mapper;

import ink.sher.vueblog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    List<Integer> getTagIdByBlogId(Integer id);

}
