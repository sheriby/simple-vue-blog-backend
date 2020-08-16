package ink.sher.vueblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ink.sher.vueblog.entity.Comment;
import ink.sher.vueblog.mapper.CommentMapper;
import ink.sher.vueblog.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Integer getCommentCount(Integer blog) {
        return this.baseMapper.getCommentCount(blog);
    }

    @Override
    public List<Comment> getParentComments(Integer blog) {
        return this.baseMapper.getParentComments(blog);
    }

    @Override
    public List<Comment> getChildComments(Integer comment) {
       return this.baseMapper.getChildComments(comment);
    }

    @Override
    public Integer getMessageCount() {
        return this.baseMapper.getMessageCount();
    }

    @Override
    public List<Comment> getParentMessages() {
        return this.baseMapper.getParentMessages();
    }

    @Override
    public List<Comment> getChildMessages(Integer message) {
        return this.baseMapper.getChildMessages(message);
    }

    @Override
    @Transactional
    @SuppressWarnings("all")
    public Integer saveMessage(Comment comment) {
        return this.baseMapper.saveMessage(comment);
    }
}
