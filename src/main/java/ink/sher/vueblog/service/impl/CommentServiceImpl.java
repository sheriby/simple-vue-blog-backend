package ink.sher.vueblog.service.impl;

import ink.sher.vueblog.entity.Comment;
import ink.sher.vueblog.mapper.CommentMapper;
import ink.sher.vueblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sher
 * @since 2020-08-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
