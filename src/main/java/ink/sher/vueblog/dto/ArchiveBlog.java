package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Title ArchiveBlog
 * @Package ink.sher.vueblog.dto
 * @Description archive blog
 * @Author sher
 * @Date 2020/08/11 11:41 AM
 */
@Data
public class ArchiveBlog {

    private Integer id;
    private String title;
    private String time;

    public static ArchiveBlog blog2Archive(Blog blog) {
        if (blog == null) {
            return null;
        }

        ArchiveBlog archiveBlog = new ArchiveBlog();
        archiveBlog.setId(blog.getId());
        archiveBlog.setTitle(blog.getTitle());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd日 HH:mm:ss");
        archiveBlog.setTime(simpleDateFormat.format(blog.getCreateTime()));

        return archiveBlog;
    }
}
