package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.exception.BadCategoryException;
import pl.miczeq.exception.DatabaseException;
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

        List<Category> categories = null;
        boolean status = false;

        try {
            categories = categoryService.findAll();
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

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

        Category category = null;
        boolean status = false;

        try {
            category = categoryService.findOne(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

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
        boolean status = false;

        try {
            categoryService.save(category);
            status = true;
        } catch (BadCategoryException e) {
            map.put("error", "Bad Category Exception: " + e.getMessage());
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
            categoryService.remove(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }
}
