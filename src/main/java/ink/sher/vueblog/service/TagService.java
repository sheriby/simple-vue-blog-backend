package ink.sher.vueblog.service;

import ink.sher.vueblog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sher
 * @since 2020-08-08
 */
public interface TagService extends IService<Tag> {

    List<Tag> getTagsByBlogId(Integer id);
}
