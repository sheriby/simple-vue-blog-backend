package ink.sher.vueblog.mapper;

import ink.sher.vueblog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {

    Integer getCommentCount(Integer id);

    List<Comment> getParentComments(Integer blog);

    List<Comment> getChildComments(Integer comment);

    Integer getMessageCount();

    List<Comment> getParentMessages();

    List<Comment> getChildMessages(Integer id);

    Integer saveMessage(Comment comment);
}
