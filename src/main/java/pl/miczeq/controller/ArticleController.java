package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

        List<Article> articles = articleService.findAll();
        boolean status;

        if (articles != null) {
            status = true;
            map.put("articles", articles);
        } else {
            status = false;
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getOne(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        Article article = articleService.findOne(id);
        boolean status;

        if (article != null) {
            status = true;
            map.put("article", article);
        } else {
            status = false;
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> save(@RequestBody Article article) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", articleService.save(article));

        return map;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> remove(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", articleService.remove(id));

        return map;
    }
}
