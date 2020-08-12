package ink.sher.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.sher.vueblog.dto.ArchiveBlog;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Tag;
import ink.sher.vueblog.entity.Type;
import ink.sher.vueblog.mapper.BlogMapper;
import ink.sher.vueblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Override
    public void blogPage(Page<Blog> page, QueryWrapper<Blog> wrapper) {
        this.baseMapper.selectPage(page, wrapper);
    }

    @Override
    public Blog getSimpleById(Integer id) {
        return this.baseMapper.getSimpleById(id);
    }

    @Override
    public List<Blog> getBlogsByTypeId(Integer id, Page<Blog> page) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id", "cover", "update_time", "view", "title", "description")
                .eq("type_id", id).orderByDesc("update_time");
        return this.baseMapper.selectPage(page, wrapper).getRecords();
    }

    @Override
    public Integer getBlogCountByTagId(Integer id) {
        return this.baseMapper.getBlogCountByTagId(id);
    }

    @Override
    public List<Blog> getBlogsByTadId(Integer tagId, Page<Blog> page) {
        long current = page.getCurrent();
        int size = Math.toIntExact(page.getSize());
        int start = Math.toIntExact((current - 1) * size);
        List<Integer> blogIds = this.baseMapper.getBlogIdsByTagId(tagId, start, size);
        if (blogIds.isEmpty()) {
            return null;
        }

        return this.baseMapper.selectBatchIds(blogIds);
    }

    @Override
    public List<String> getAllYears() {
        return this.baseMapper.getAllYears();
    }

    @Override
    public Map<String, List<ArchiveBlog>> getArchive(String year) {
        List<String> months = this.baseMapper.getAllMonths(year);
        HashMap<String, List<ArchiveBlog>> map = new LinkedHashMap<>();
        for (String month : months) {
            List<ArchiveBlog> blog = this.baseMapper.getBlogByTime(year + month).
                    stream().map(ArchiveBlog::blog2Archive).collect(Collectors.toList());
            map.put(month, blog);
        }

        return map;
    }


    @Override
    public void updateBlogAndTag(Blog blog, List<Tag> tags) {
        Integer blogid = blog.getId();
        blog.setUpdateTime(new Date());

        if (blogid == null) {
            this.baseMapper.insert(blog);
            blogid = blog.getId();
        } else {
            this.baseMapper.removeAllTagsByBlogId(blog.getId());
        }

        if (tags != null && !tags.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("blogid", blogid);
            map.put("tags", tags);
            this.baseMapper.setBlogTags(map);
        }
    }
}
