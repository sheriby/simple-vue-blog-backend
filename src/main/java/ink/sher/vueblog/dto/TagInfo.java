package ink.sher.vueblog.dto;

import ink.sher.vueblog.entity.Blog;
import ink.sher.vueblog.entity.Tag;
import lombok.Data;

import java.util.List;

/**
 * @Title TagInfo
 * @Package ink.sher.vueblog.dto
 * @Description tag info
 * @Author sher
 * @Date 2020/08/09 6:50 PM
 */
@Data
public class TagInfo {

    private Integer id;
    private String name;
    private String desc;
    private Integer blogCount;

    public static TagInfo tag2TagInfo(Tag tag, Integer blogCount) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setId(tag.getId());
        tagInfo.setName(tag.getName());
        tagInfo.setDesc(tag.getDescription());
        tagInfo.setBlogCount(blogCount);

        return tagInfo;
    }
}
