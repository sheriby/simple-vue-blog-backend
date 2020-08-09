package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.SimpleBlog;
import ink.sher.vueblog.dto.TagInfo;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Tag;
import ink.sher.vueblog.service.BlogService;
import ink.sher.vueblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tag")
@CrossOrigin
public class TagController {

    private final TagService tagService;
    private final BlogService blogService;

    @Autowired
    public TagController(TagService tagService, BlogService blogService) {
        this.tagService = tagService;
        this.blogService = blogService;
    }

    @PostMapping
    public Result tagInfo() {
        List<Tag> tags = tagService.list();
        Integer tagId = tags.get(0).getId();

        List<TagInfo> tagInfos = tags.stream().
                map(tag -> TagInfo.tag2TagInfo(tag, blogService.getBlogCountByTagId(tag.getId()))).
                collect(Collectors.toList());

        Page<Blog> page = new Page<>(1, 1);
        List<SimpleBlog> simpleBlogs = null;
        List<Blog> blogs = blogService.getBlogsByTadId(tagId, page);
        if (blogs != null) {
            simpleBlogs = blogs.stream().map(SimpleBlog::blog2Simple)
                    .collect(Collectors.toList());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("tag", tagInfos.get(0));
        map.put("tags", tagInfos);
        map.put("blogs", simpleBlogs);

        return Result.success(map);
    }

    @PostMapping("/{tagId}/{page}")
    public Result tagInfo(@PathVariable Integer tagId, @PathVariable Integer page) {
        Page<Blog> pageable = new Page<>(page, 1);
        List<Blog> blogs = blogService.getBlogsByTadId(tagId, pageable);
        if (blogs == null) {
            return Result.failure();
        }
        List<SimpleBlog> simpleBlogs = blogs.stream().map(SimpleBlog::blog2Simple)
                            .collect(Collectors.toList());

        return Result.success(simpleBlogs);
    }

}
