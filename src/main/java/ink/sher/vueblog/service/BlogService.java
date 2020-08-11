package ink.sher.vueblog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.sher.vueblog.dto.ArchiveBlog;
import ink.sher.vueblog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sher
 * @since 2020-08-08
 */
public interface BlogService extends IService<Blog> {

    void blogPage(Page<Blog> page, QueryWrapper<Blog> wrapper);

    Blog getSimpleById(Integer id);

    List<Blog> getBlogsByTypeId(Integer id, Page<Blog> page);

    Integer getBlogCountByTagId(Integer id);

    List<Blog> getBlogsByTadId(Integer tagId, Page<Blog> page);

    List<String> getAllYears();

    Map<String, List<ArchiveBlog>> getArchive(String year);
}
