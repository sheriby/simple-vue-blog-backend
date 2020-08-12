package ink.sher.vueblog.mapper;

import ink.sher.vueblog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ink.sher.vueblog.entity.Tag;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Sher
 * @since 2020-08-08
 */
public interface BlogMapper extends BaseMapper<Blog> {

    Blog getSimpleById(Integer id);

    Integer getBlogCountByTagId(Integer id);

    List<Integer> getBlogIdsByTagId(Integer id, int start, int size);

    List<String> getAllYears();

    List<String> getAllMonths(String year);

    List<Blog> getBlogByTime(String yearmonth);

    Integer removeAllTagsByBlogId(Integer id);

    Integer setBlogTags(HashMap<String, Object> map);
}
