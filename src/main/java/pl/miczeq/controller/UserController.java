package pl.miczeq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

        List<User> users = userService.findAll();
        boolean status = false;

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

        User user = userService.findOne(id);
        boolean status = false;

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

        User user = userService.findOneByUsername(username);
        boolean status = false;

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

        User user = userService.findOneByEmail(email);
        boolean status = false;

        if (user != null) {
            status = true;
            map.put("user", user);
        }

        map.put("status", status);

        return map;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public @ResponseBody Map<String, Object> save(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", userService.save(user));

        return map;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody Map<String, Object> remove(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();

        map.put("status", userService.remove(id));

        return map;
    }
}
