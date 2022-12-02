package com.livk.json.controller;

import com.livk.json.entity.User;
import com.livk.json.mapper.UserMapper;
import com.livk.commons.util.JacksonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * UserController
 * </p>
 *
 * @author livk
 * @date 2022/5/26
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;

    @PostMapping("user")
    public HttpEntity<Boolean> save() {
        String json = """
                {
                  "mark": "livk"
                }""";
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setDes(JacksonUtils.readTree(json));
        return ResponseEntity.ok(userMapper.insert(user) != 0);
    }

    @GetMapping("user")
    public HttpEntity<List<User>> users() {
        return ResponseEntity.ok(userMapper.selectList());
    }

}
