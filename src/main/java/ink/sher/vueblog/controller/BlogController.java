package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.log.Log;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.*;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Comment;
import ink.sher.vueblog.service.BlogService;
import ink.sher.vueblog.service.CommentService;
import ink.sher.vueblog.service.TagService;
import ink.sher.vueblog.service.TypeService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/blog")
@CrossOrigin
public class BlogController {

    private final BlogService blogService;
    private final TagService tagService;
    private final TypeService typeService;
    private final CommentService commentService;

    public BlogController(BlogService blogService, TagService tagService,
                          TypeService typeService, CommentService commentService) {
        this.blogService = blogService;
        this.tagService = tagService;
        this.typeService = typeService;
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    public Result blogDetail(@PathVariable Integer id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            return Result.failure("No suck blog");
        }

        Blog pre = blogService.getSimpleById(id - 1);
        Blog next = blogService.getSimpleById(id + 1);


        BlogDetail blogDetail = BlogDetail.blogToBlogDetail(blog, pre, next,
               getCommentInfo(id), commentService.getCommentCount(id));

        return Result.success(blogDetail);
    }

    private List<CommentInfo> getCommentInfo(Integer id) {
        List<CommentInfo> commentInfos = null;
        List<Comment> comments = commentService.getParentComments(id);

        if (comments != null && !comments.isEmpty()) {
            commentInfos = comments.stream().map(comment -> {
                List<Comment> childs = commentService.getChildComments(comment.getId());
                return CommentInfo.comment2info(comment, childs);
            }).collect(Collectors.toList());
        }

        return commentInfos;
    }

    @GetMapping("comment/{id}")
    public Result comment(@PathVariable Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("comments", getCommentInfo(id));
        map.put("commentCount", commentService.getCommentCount(id));

        return Result.success(map);
    }


    @GetMapping("page/{page}")
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

    @GetMapping("/archive")
    public Result archive() throws JsonProcessingException {
        List<String> years = blogService.getAllYears();
        Map<String, Object> map = new LinkedHashMap<>();
        for (String year : years ) {
            Map<String, List<ArchiveBlog>> blogs = blogService.getArchive(year);
            map.put(year+" ", blogs);
        }

        return Result.success(map);
    }

    @PostMapping
    public Result postBlog(@RequestBody BlogInfo blog) {

        System.out.println(blog);
        blogService.updateBlogAndTag(blog.toBlog(), blog.getTags());

        return Result.success(null);
    }

}
