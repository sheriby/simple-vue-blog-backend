package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.ArchiveBlog;
import ink.sher.vueblog.dto.BlogDetail;
import ink.sher.vueblog.dto.BlogInfo;
import ink.sher.vueblog.dto.CommentInfo;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Comment;
import ink.sher.vueblog.entity.Tag;
import ink.sher.vueblog.service.BlogService;
import ink.sher.vueblog.service.CommentService;
import ink.sher.vueblog.service.TagService;
import ink.sher.vueblog.service.TypeService;
import ink.sher.vueblog.util.MarkDownUtils;
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

        blog.setContent(MarkDownUtils.markToHtmlExt(blog.getContent()));

        Blog pre = blogService.getSimpleById(id - 1);
        Blog next = blogService.getSimpleById(id + 1);


        BlogDetail blogDetail = BlogDetail.blogToBlogDetail(blog, pre, next,
               getCommentInfo(id), commentService.getCommentCount(id));

        return Result.success(blogDetail);
    }

    @GetMapping("bloginfo/{id}")
    public Result blogInfo(@PathVariable Integer id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            return Result.failure("No suck blog");
        }

        return Result.success(BlogInfo.blogToBlogInfo(blog, tagService.getTagsByBlogId(blog.getId()),
                typeService.getById(blog.getTypeId()), null));
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
    public Result page(@PathVariable Integer page,
                       @RequestParam(required = false, defaultValue = "6") Integer size) {
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.select("id", "cover", "update_time", "title", "type_id", "description", "view")
                .orderByDesc("update_time");

        Page<Blog> pages = new Page<>(page, size);
        blogService.blogPage(pages, blogQueryWrapper);
        List<Blog> blogs = pages.getRecords();
        long total = pages.getTotal();

        if (blogs.size() == 0) {
            return Result.failure("No Data");
        }

        List<BlogInfo> blogInfos = blogs.stream()
                .map(blog -> BlogInfo.blogToBlogInfo(blog,
                        tagService.getTagsByBlogId(blog.getId()),
                        typeService.getById(blog.getTypeId()),
                        commentService.getCommentCount(blog.getId())))
                .collect(Collectors.toList());

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("blogs", blogInfos);

        return Result.success(map);
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
        List<Tag> tags = blog.getTags();
        for (Tag tag : tags) {
            if (tag.getId() == null) {
                tagService.save(tag);
            }
        }
        blogService.updateBlogAndTag(blog.toBlog(), tags);

        return Result.success(null);
    }

    @GetMapping("/search")
    public Result searchBlog(@RequestParam(required = false) String title,
                             @RequestParam(required = false) Integer typeId,
                             @RequestParam(required = false) Integer tagId) {
        List<Blog> blogs = blogService.searchBlog(title, typeId, tagId);

        List<BlogInfo> bloginfos = blogs.stream().map(blog -> BlogInfo.blogToBlogInfo(blog, null,
                typeService.getById(blog.getTypeId()),
                commentService.getCommentCount(blog.getId()))).collect(Collectors.toList());

        return Result.success(bloginfos);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Integer id) {
        try {
            blogService.deleteBlogById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure("Delete Failure");
        }
        return Result.success("Delete Success");
    }
}
