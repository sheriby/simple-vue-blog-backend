package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.BlogDetail;
import ink.sher.vueblog.dto.BlogInfo;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.service.BlogService;
import ink.sher.vueblog.service.TagService;
import ink.sher.vueblog.service.TypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/blog")
@CrossOrigin
public class BlogController {

    private final BlogService blogService;
    private final TagService tagService;
    private final TypeService typeService;

    public BlogController(BlogService blogService, TagService tagService,
                          TypeService typeService) {
        this.blogService = blogService;
        this.tagService = tagService;
        this.typeService = typeService;
    }

    @PostMapping("{id}")
    public Result blogDetail(@PathVariable Integer id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            return Result.failure("No suck blog");
        }

        Blog pre = blogService.getSimpleById(id - 1);
        Blog next = blogService.getSimpleById(id + 1);

        BlogDetail blogDetail = BlogDetail.blogToBlogDetail(blog, pre, next, null);

        return Result.success(blogDetail);
    }


    @PostMapping("/page/{page}")
    public Result page(@PathVariable Integer page) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.select("id", "cover", "update_time", "title", "type_id", "description", "view")
                .orderByDesc("update_time");

        Page<Blog> pages = new Page<>(page, 2);
        blogService.blogPage(pages, blogQueryWrapper);
        List<Blog> blogs = pages.getRecords();

        if (blogs.size() == 0) {
            return Result.failure("No Data");
        }

        List<BlogInfo> blogInfos = blogs.stream()
                .map(blog -> BlogInfo.blogToBlogInfo(blog,
                        tagService.getTagsByBlogId(blog.getId()), typeService.getById(blog.getTypeId())))
                .collect(Collectors.toList());

        return Result.success(blogInfos);

    }



}