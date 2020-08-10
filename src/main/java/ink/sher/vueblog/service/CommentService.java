package ink.sher.vueblog.service;

import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CommentService extends IService<Comment> {

    Integer getCommentCount(Integer blog);

    List<Comment> getParentComments(Integer blog);

    List<Comment> getChildComments(Integer comment);

    Integer getMessageCount();

    List<Comment> getParentMessages();

    List<Comment> getChildMessages(Integer message);

    Integer saveMessage(Comment comment);
}
