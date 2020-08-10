package ink.sher.vueblog.controller;


import com.mysql.cj.log.Log;
import ink.sher.vueblog.common.Result;
import ink.sher.vueblog.dto.BlogInfo;
import ink.sher.vueblog.dto.CommentInfo;
import ink.sher.vueblog.entity.Comment;
import ink.sher.vueblog.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Result saveComment(@RequestBody Comment comment) {
        commentService.save(comment);

        return Result.success(null);
    }

    @GetMapping("/message")
    public Result message() {
        List<Comment> messages = commentService.getParentMessages();
        if (messages == null) {
            return Result.success(null);
        }

        List<CommentInfo> messageInfos = messages.stream().map(message -> {
            List<Comment> childs = commentService.getChildMessages(message.getId());
            return CommentInfo.comment2info(message, childs);
        }).collect(Collectors.toList());

        Integer count = commentService.getMessageCount();

        HashMap<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("messages", messageInfos);

        return Result.success(map);
    }

    @PostMapping("/message")
    public Result saveMessage(@RequestBody Comment comment) {
        System.out.println(comment);
        commentService.saveMessage(comment);

        return Result.success(null);
    }

}
