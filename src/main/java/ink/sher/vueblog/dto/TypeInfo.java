package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Type;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title TypeInfo
 * @Package ink.sher.vueblog.dto
 * @Description type info
 * @Author sher
 * @Date 2020/08/09 4:07 PM
 */
@Data
public class TypeInfo {

    private Integer id;
    private String name;
    private String desc;
    List<SimpleBlog> blogs;


    public static TypeInfo getInstance(Type type, List<Blog> blogs) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setId(type.getId());
        typeInfo.setName(type.getName());
        typeInfo.setDesc(type.getDescription());

        typeInfo.setBlogs(blogs.stream().map(SimpleBlog::blog2Simple).collect(Collectors.toList()));

        return typeInfo;
    }

}
