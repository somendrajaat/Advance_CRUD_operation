package com.dailype.task.Service;


import com.dailype.task.Model.BulkUpdateRequest;
import com.dailype.task.Model.UserRequest;
import com.dailype.task.Model.people;
import com.dailype.task.Model.Manager;
import com.dailype.task.Repository.UserRepository;
import com.dailype.task.Repository.managerRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final managerRepository managerRepository;

    @Autowired
    public UserService(UserRepository userRepository, managerRepository managerRepository) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
    }



    public void createUser(UserRequest userRequest) {
        /*
        validate username is not empty
         */

        if (userRequest.getFullName().isEmpty()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }


        //it checks the format of the mobile number using regex

        String mobNum = userRequest.getMobNum();
        if (!Pattern.matches("^(0|\\+91)?[6789]\\d{9}$", mobNum)) {
            throw new IllegalArgumentException("Invalid mobile number");
        }
        mobNum = mobNum.replaceFirst("^(0|\\+91)", "");


        //this checks the format of the PAN number using regex
        String panNum = userRequest.getPanNum().toUpperCase();
        if (!Pattern.matches("[A-Z]{5}\\d{4}[A-Z]", panNum)) {
            throw new IllegalArgumentException("Invalid PAN number");
        }

        //all the data is stored in the people object
        UUID managerId = userRequest.getManagerId();
        people user = new people();
        user.setFullName(userRequest.getFullName());
        user.setMobNum(mobNum);
        user.setPanNum(panNum);
        // it checks the manager id is valid, active and present in the database already if it is not present then simply ignore
        if (managerId != null) {
            Optional<Manager> manager = managerRepository.findByIdAndIsActive(managerId, true);
            if (manager.isEmpty()) {
                throw new IllegalArgumentException("Invalid or inactive manager ID");
            }
        user.setManager(manager.get());
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);


    }

    //this function is used to get the users based on the parameters passed

    public List<people> getUsers(String mobNum, UUID userId, UUID managerId) {

        if (mobNum != null) {
            return new ArrayList<>(userRepository.findByMobNum(mobNum));
        } else if (userId != null) {
            return userRepository.findById(userId).map(Collections::singletonList).orElse(Collections.emptyList());
        } else if (managerId != null) {
            return userRepository.findByManagerId(managerId);
        } else {
            return userRepository.findAll();
        }
    }


//this function is used to delete the user based on the parameters passed
    public Map<String, String> deleteUser(Map<String, String> params) {

        String userId = params.get("userId");
        String mobNum = params.get("mobNum");
        Map<String, String> response = new HashMap<>();

        if (userId != null) {
            UUID id = UUID.fromString(userId);
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                response.put("message", "User deleted successfully");
            } else {
                response.put("error", "User not found");
            }
        } else if (mobNum != null) {
            List<people> users = userRepository.findByMobNum(mobNum);
            if (!users.isEmpty()) {
                userRepository.deleteAll(users);
                response.put("message", "User(s) deleted successfully");
            } else {
                response.put("error", "User not found");
            }
        } else {
            response.put("error", "Invalid parameters");
        }

        return response;

    }


    //it checks if the user is present in the database and then updates the user based on the parameters passed
    @Transactional
    public ResponseEntity<?> bulkUpdateUsers(BulkUpdateRequest bulkUpdateRequest) {
        List<UUID> userIds = bulkUpdateRequest.getUserIds();
        BulkUpdateRequest.UpdateData updateData = bulkUpdateRequest.getUpdateData();

        // Check if only manager_id is being updated when updating in bulk
        if (userIds.size() > 1 && (updateData.getFullName() != null || updateData.getMobNum() != null || updateData.getPanNum() != null)) {
            return ResponseEntity.badRequest().body("full_name, mob_num, and pan_num can be updated on an individual basis only and not in bulk");
        }

        // Validate and update users
        for (UUID userId : userIds) {
            Optional<people> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                people user = optionalUser.get();

                // Validate and set full_name
                if (updateData.getFullName() != null) {
                    if (updateData.getFullName().isEmpty()) {
                        return ResponseEntity.badRequest().body("Full name must not be empty");
                    }
                    user.setFullName(updateData.getFullName());
                }

                // Validate and set mob_num
                if (updateData.getMobNum() != null) {
                    String mobNum = updateData.getMobNum().replaceFirst("^(0|\\+91)", "");
                    if (!Pattern.matches("^(0|\\+91)?[6789]\\d{9}$", mobNum)) {
                        return ResponseEntity.badRequest().body("Invalid mobile number");
                    }
                    user.setMobNum(mobNum);
                }

                // Validate and set pan_num
                if (updateData.getPanNum() != null) {
                    String panNum = updateData.getPanNum().toUpperCase();
                    if (!Pattern.matches("[A-Z]{5}\\d{4}[A-Z]", panNum)) {
                        return ResponseEntity.badRequest().body("Invalid PAN number");
                    }
                    user.setPanNum(panNum);
                }

                // Validate and set manager_id
                if (updateData.getManagerId() != null) {
                    Optional<Manager> optionalManager = managerRepository.findByIdAndIsActive(updateData.getManagerId(), true);
                    if (optionalManager.isEmpty()) {
                        return ResponseEntity.badRequest().body("Invalid or inactive manager ID");
                    }
                    Manager manager = optionalManager.get();

                    // If the user already has a manager, make the current database entry inactive
                    if (user.getManager() != null) {
                        user.setActive(false);
                        userRepository.save(user);

                        // Create a new entry for the user with the old data but the new manager_id
                        people newUser = new people();
                        newUser.setFullName(user.getFullName());
                        newUser.setMobNum(user.getMobNum());
                        newUser.setPanNum(user.getPanNum());
                        newUser.setManager(manager);
                        newUser.setCreatedAt(user.getCreatedAt());
                        newUser.setUpdatedAt(LocalDateTime.now());
                        newUser.setActive(true);
                        userRepository.save(newUser);
                    } else {
                        // If the user doesn't have a manager, just set the manager_id
                        user.setManager(manager);
                        user.setUpdatedAt(LocalDateTime.now());
                        userRepository.save(user);
                    }
                }
            } else {
                return ResponseEntity.badRequest().body("User with id " + userId + " not found");
            }
        }

        return ResponseEntity.ok("Users updated successfully");
    }
}