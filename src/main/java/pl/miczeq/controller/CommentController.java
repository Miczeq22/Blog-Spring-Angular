package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.model.Comment;
import pl.miczeq.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/article/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getAllForArticle(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        List<Comment> comments = commentService.findAllForArticle(id);
        boolean status = false;

        if (comments != null) {
            status = true;
            map.put("comments", comments);
        }

        map.put("status", status);

        return map;
    }
}
