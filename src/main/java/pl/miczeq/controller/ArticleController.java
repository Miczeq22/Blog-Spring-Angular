package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.exception.BadArticleException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Article;
import pl.miczeq.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();

        List<Article> articles = null;
        try {
             articles = articleService.findAll();
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }
        boolean status = false;

        if (articles != null) {
            status = true;
            map.put("articles", articles);
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getOne(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        Article article = null;
        boolean status = false;

        try {
            article = articleService.findOne(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        if (article != null) {
            status = true;
            map.put("article", article);
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> save(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();
        boolean status = false;

        try {
            articleService.save(article);
            status = true;
        } catch (BadArticleException e) {
            map.put("error", "Bad Article Exception: " + e.getMessage());
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> remove(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        boolean status = false;

        try {
            articleService.remove(id);
            status = true;
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }
}
