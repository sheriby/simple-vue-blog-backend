package ink.sher.vueblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ink.sher.vueblog.entity.Type;

import java.util.List;

public interface TypeService extends IService<Type> {

    List<Type> findTypeByKeyword(String keyword);

}
