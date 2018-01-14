package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.model.Category;
import pl.miczeq.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();

        List<Category> categories = categoryService.findAll();
        boolean status = false;

        if (categories != null) {
            status = true;
            map.put("categories", categories);
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getAllById(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        Category category = categoryService.findOne(id);
        boolean status = false;

        if (category != null) {
            status = true;
            map.put("category", category);
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> save(@RequestBody Category category) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", categoryService.save(category));

        return map;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> remove(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", categoryService.remove(id));

        return map;
    }
}
