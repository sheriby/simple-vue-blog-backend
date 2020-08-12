package ink.sher.vueblog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.TypeInfo;
import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Type;
import ink.sher.vueblog.service.BlogService;
import ink.sher.vueblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/type")
@CrossOrigin
public class TypeController {

    private final TypeService typeService;
    private final BlogService blogService;

    @Autowired
    public TypeController(TypeService typeService, BlogService blogService) {
        this.typeService = typeService;
        this.blogService = blogService;
    }

    @GetMapping("{id}/{page}")
    public Result blogs(@PathVariable Integer id, @PathVariable Integer page) {
        Type type = typeService.getById(id);
        Page<Blog> pageable = new Page<>(page, 2);
        List<Blog> blogs = blogService.getBlogsByTypeId(id, pageable);

        return Result.success(TypeInfo.getInstance(type, blogs));
    }

    @GetMapping
    public Result listTypes() {
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name").orderByAsc("id");
        List<Type> types = typeService.list(wrapper);

        return Result.success(types);
    }
}
