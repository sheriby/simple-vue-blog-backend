package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import lombok.Data;

import java.util.Date;

/**
 * @Title SimpleBlog
 * @Package ink.sher.vueblog.dto
 * @Description simple blog
 * @Author sher
 * @Date 2020/08/09 7:16 PM
 */
@Data
public class SimpleBlog {
    private Integer id;
    private String cover;
    private Date date;
    private Integer view;
    private String title;
    private String desc;

    public static SimpleBlog blog2Simple(Blog blog) {
        if (blog == null) {
            return null;
        }
        SimpleBlog simpleBlog = new SimpleBlog();
        simpleBlog.setId(blog.getId());
        simpleBlog.setCover(blog.getCover());
        simpleBlog.setDate(blog.getUpdateTime());
        simpleBlog.setView(blog.getView());
        simpleBlog.setTitle(blog.getTitle());
        simpleBlog.setDesc(blog.getDescription());

        return simpleBlog;
    }
}
