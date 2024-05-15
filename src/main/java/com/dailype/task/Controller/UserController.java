package com.dailype.task.Controller;

import com.dailype.task.Model.BulkUpdateRequest;
import com.dailype.task.Model.UserRequest;
import com.dailype.task.Model.people;


import com.dailype.task.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {


        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }


    @PostMapping("/get_users")
    public ResponseEntity<Map<String, List<people>>> getUsers(@RequestParam(required = false) String mobNum,
                                                              @RequestParam(required = false) UUID userId,
                                                              @RequestParam(required = false) UUID managerId) {
        List<people> users = userService.getUsers(mobNum, userId, managerId);
        Map<String, List<people>> response = new HashMap<>();
        response.put("users", users);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/delete_user")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody Map<String, String> params) {
        Map<String, String> result = userService.deleteUser(params);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update_user")
    public ResponseEntity<?> bulkUpdateUsers(@RequestBody BulkUpdateRequest bulkUpdateRequest) {
        return userService.bulkUpdateUsers(bulkUpdateRequest);
    }



}
