package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.miczeq.exception.BadUserException;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.User;
import pl.miczeq.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<>();

        List<User> users = null;
        boolean status = false;

        try {
            users = userService.findAll();
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        if (users != null) {
            status = true;
            map.put("users", users);
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getOneById(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        User user = null;
        boolean status = false;

        try {
            user = userService.findOne(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        if (user != null) {
            status = true;
            map.put("user", user);
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getOneByUsername(@PathVariable("username") String username) {
        Map<String, Object> map = new HashMap<>();

        User user = null;
        boolean status = false;

        try {
            user = userService.findOneByUsername(username);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        if (user != null) {
            status = true;
            map.put("user", user);
        }

        map.put("status", status);

        return map;
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> getOneByEmail(@PathVariable("email") String email) {
        Map<String, Object> map = new HashMap<>();

        User user = null;
        boolean status = false;

        try {
            user = userService.findOneByEmail(email);
        } catch (DatabaseException e) {
            map.put("status", "Database Exception: " + e.getMessage());
        }

        if (user != null) {
            status = true;
            map.put("user", user);
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    @PreAuthorize("permitAll()")
    public @ResponseBody Map<String, Object> save(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        boolean status = false;

        try {
            status = userService.save(user);
        } catch (BadUserException e) {
            map.put("error", "Bad User Exception: " + e.getMessage());
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
            status = userService.remove(id);
        } catch (DatabaseException e) {
            map.put("error", "Database Exception: " + e.getMessage());
        }

        map.put("status", status);

        return map;
    }
}
