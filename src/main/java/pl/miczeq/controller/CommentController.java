package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.exception.BadCommentException;
import pl.miczeq.exception.DatabaseException;
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

        List<Comment> comments = null;
        boolean status = false;

        try {
            comments = commentService.findAllForArticle(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        if (comments != null) {
            status = true;
            map.put("comments", comments);
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    public @ResponseBody Map<String, Object> save(@RequestBody Comment comment) {
        Map<String, Object> map = new HashMap<>();
        boolean status = false;

        try {
            status = commentService.save(comment);
        } catch (BadCommentException e) {
            map.put("error", "Bad Comment Exception: " + e.getMessage());
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }


    @DeleteMapping("/{id}")
    public @ResponseBody Map<String, Object> delete(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        boolean status;

        try {
            status = commentService.remove(id);
        } catch (DatabaseException e) {
            status = false;
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }
}
