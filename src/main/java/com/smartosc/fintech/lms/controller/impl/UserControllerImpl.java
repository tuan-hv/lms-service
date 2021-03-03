package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.UserController;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.dto.UserDto;
import com.smartosc.fintech.lms.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Cacheable("users_cache")
    @Override
    public Response<Page<UserDto>> getUser(int page, int size) {
        Page<UserDto> response = userService.getUser(PageRequest.of(page, size));
        return Response.ok(response);
    }

    @Cacheable("user_cache")
    @Override
    public Response<UserDto> getUser(Long id) {
        UserDto response = userService.getUser(id);
        return Response.ok(response);
    }

    @SMFLogger
    @Override
    public Response<UserDto> createUser(UserDto userDto) {
        UserDto response = userService.createUser(userDto);
        return Response.ok(response);
    }

    @SMFLogger
    @Override
    public Response<UserDto> updateUser(Long id, UserDto userDto) {
        UserDto response = userService.updateUser(id, userDto);
        return Response.ok(response);
    }

    @SMFLogger
    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @CacheEvict(cacheNames = {"users_cache", "user_cache"}, allEntries = true)
    @Override
    public void evictCache() {
        log.info("User cache has been evicted");
    }
}
