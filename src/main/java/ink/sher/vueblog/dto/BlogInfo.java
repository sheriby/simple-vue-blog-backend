package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Tag;
import ink.sher.vueblog.entity.Type;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Title BlogInfo
 * @Package ink.sher.vueblog.dto
 * @Description blog info for index
 * @Author sher
 * @Date 2020/08/08 7:38 PM
 */
@Data
public class BlogInfo {

    private Integer id;
    private String cover;
    private Date date;
    private String content;
    private String title;
    private String desc;
    private Integer view;
    private Integer commentCount;
    private List<Tag> tags;
    private Type type;

    public static BlogInfo blogToBlogInfo(Blog blog, List<Tag> tags, Type type, Integer commentCount) {
        BlogInfo blogInfo = new BlogInfo();
        blogInfo.setId(blog.getId());
        blogInfo.setCover(blog.getCover());
        blogInfo.setDate(blog.getUpdateTime());
        blogInfo.setContent(blog.getContent());
        blogInfo.setTitle(blog.getTitle());
        blogInfo.setDesc(blog.getDescription());
        blogInfo.setView(blog.getView());
        blogInfo.setCommentCount(commentCount);

        blogInfo.setTags(tags);
        blogInfo.setType(type);

        return blogInfo;
    }

    public Blog toBlog() {
        Blog blog = new Blog();
        blog.setId(this.id);
        blog.setContent(this.content);
        blog.setCover(this.cover);
        blog.setTitle(this.title);
        blog.setDescription(this.desc);
        blog.setTypeId(this.type.getId());

        return blog;
    }
}
