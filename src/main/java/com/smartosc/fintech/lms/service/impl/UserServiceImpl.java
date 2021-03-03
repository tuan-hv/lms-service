package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.dto.UserDto;
import com.smartosc.fintech.lms.entity.UserEntity;
import com.smartosc.fintech.lms.repository.UserRepository;
import com.smartosc.fintech.lms.service.UserService;
import com.smartosc.fintech.lms.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<UserDto> getUser(Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(pageable);
        return userEntities.map(UserMapper.getInstance()::mapToDto);
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException();
        }

        return UserMapper.getInstance().mapToDto(user.get());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.getInstance().mapToEntity(userDto);
        userRepository.save(userEntity);

        return UserMapper.getInstance().mapToDto(userEntity);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }

        UserEntity userEntity = UserMapper.getInstance().mapToEntity(userDto);
        userEntity.setId(id);
        userRepository.save(userEntity);

        return UserMapper.getInstance().mapToDto(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
