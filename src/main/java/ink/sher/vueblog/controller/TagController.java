package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @GetMapping
    public Result tagInfo(@RequestParam(required = false) Integer id) {
        List<Tag> tags = tagService.list();
        Integer tagId = null;
        TagInfo tag = null;
        if (id != null) {
            tagId = id;
            Tag t = tagService.getById(tagId);
            if (t == null) {
                return Result.failure();
            }
            tag = TagInfo.tag2TagInfo(t, 0);
        } else {
            tagId = tags.get(0).getId();
        }

        List<TagInfo> tagInfos = tags.stream().
                map(t -> TagInfo.tag2TagInfo(t, blogService.getBlogCountByTagId(t.getId()))).
                collect(Collectors.toList());
        if (tag == null) {
            tag = tagInfos.get(0);
        }

        Page<Blog> page = new Page<>(1, 1);
        List<SimpleBlog> simpleBlogs = null;
        List<Blog> blogs = blogService.getBlogsByTadId(tagId, page);
        if (blogs != null) {
            simpleBlogs = blogs.stream().map(SimpleBlog::blog2Simple)
                    .collect(Collectors.toList());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("tag", tag);
        map.put("tags", tagInfos);
        map.put("blogs", simpleBlogs);

        return Result.success(map);
    }

    @GetMapping("/{tagId}/{page}")
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

    @GetMapping("/tags")
    public Result listTags() {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "description").orderByAsc("id");

        List<Tag> tags = tagService.list(wrapper);

        return Result.success(tags);
    }

    @PostMapping
    public Result postTag(@RequestBody Tag tag) {
        tagService.save(tag);

        return Result.success("success");
    }

    @PutMapping
    public Result editTag(@RequestBody Tag tag) {
        tagService.updateById(tag);

        return Result.success("success");
    }

}
