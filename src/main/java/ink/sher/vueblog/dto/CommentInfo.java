package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Comment;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title CommentInfo
 * @Package ink.sher.vueblog.dto
 * @Description comment info
 * @Author sher
 * @Date 2020/08/10 11:48 AM
 */
@Data
public class CommentInfo {

    private Integer id;
    private String name;
    private Long qq;
    private String content;
    private Date date;
    private String at;

    private List<CommentInfo> childs;

    public static CommentInfo comment2info(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setId(comment.getId());
        commentInfo.setName(comment.getName());
        commentInfo.setQq(Long.parseLong(comment.getQq()));
        commentInfo.setContent(comment.getContent());
        commentInfo.setDate(comment.getCreateTime());
        commentInfo.setAt(comment.getAt());

        return commentInfo;
    }

    public static CommentInfo comment2info(Comment comment, List<Comment> childs) {
        if (comment == null) {
            return null;
        }
        CommentInfo commentInfo = CommentInfo.comment2info(comment);
        List<CommentInfo> list = null;
        if (childs != null && !childs.isEmpty()) {
            list = childs.stream().map(CommentInfo::comment2info).collect(Collectors.toList());
        }

        commentInfo.setChilds(list);

        return commentInfo;
    }
}
