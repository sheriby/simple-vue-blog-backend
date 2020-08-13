package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Title BlogDetail
 * @Package ink.sher.vueblog.dto
 * @Description blog detail
 * @Author sher
 * @Date 2020/08/09 11:36 AM
 */
@Data
public class BlogDetail {

    private Integer id;
    private String title;
    private Date date;
    private Integer view;
    private String cover;
    private String content;
    private SimpleBlogInfo previous;
    private SimpleBlogInfo next;
    private Integer commentCount;

    private List<CommentInfo> comments;

    @Data
    private static class SimpleBlogInfo {
        private Integer id;
        private String title;
        private String cover;

        public static SimpleBlogInfo blog2Simple(Blog blog) {
            if (blog == null) {
                return null;
            }
            SimpleBlogInfo simpleBlogInfo = new SimpleBlogInfo();
            simpleBlogInfo.setId(blog.getId());
            simpleBlogInfo.setCover(blog.getCover());
            simpleBlogInfo.setTitle(blog.getTitle());

            return simpleBlogInfo;
        }
    }

    public static BlogDetail blogToBlogDetail(Blog blog, Blog previous,
                                              Blog next, List<CommentInfo> comments, Integer commentCount) {
        BlogDetail blogDetail = new BlogDetail();
        blogDetail.setId(blog.getId());
        blogDetail.setTitle(blog.getTitle());
        blogDetail.setDate(blog.getUpdateTime());
        blogDetail.setView(blog.getView());
        blogDetail.setCover(blog.getCover());
        blogDetail.setContent(blog.getContent());
        blogDetail.setCommentCount(commentCount);

        SimpleBlogInfo pre = SimpleBlogInfo.blog2Simple(previous);
        SimpleBlogInfo nxt = SimpleBlogInfo.blog2Simple(next);
        blogDetail.setPrevious(pre);
        blogDetail.setNext(nxt);

        blogDetail.setComments(comments);

        return blogDetail;
    }
}
