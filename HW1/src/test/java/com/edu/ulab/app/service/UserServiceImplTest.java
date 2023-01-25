package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void savePerson_Test() {

        //given
        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");

        Person person  = new Person();
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");

        //when
        when(userMapper.userDtoToPerson(userDto)).thenReturn(person);
        when(userRepository.save(person)).thenReturn(savedPerson);
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);

        //then
        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(1L, userDtoResult.getId());
    }

    //update
    @Test
    @DisplayName("Изменить пользователя. Должно пройти успешно.")
    void updatePerson_Test() {

        //given
        UserDto updateDto = new UserDto();
        updateDto.setId(1L);
        updateDto.setFullName("Alice");
        updateDto.setTitle("Title");
        updateDto.setAge(10);

        Person updatePerson  = new Person();
        updatePerson.setId(1L);
        updatePerson.setFullName("Alice");
        updatePerson.setTitle("Title");
        updatePerson.setAge(10);

        UserDto result = new UserDto();
        result.setId(1L);
        result.setFullName("Alice");
        result.setTitle("Title");
        result.setAge(10);

        //when
        when(userRepository.findById(updatePerson.getId())).thenReturn(Optional.of(updatePerson));
        when(userRepository.save(updatePerson)).thenReturn(updatePerson);
        when(userMapper.personToUserDto(updatePerson)).thenReturn(result);
        when(userMapper.userDtoToPerson(updateDto)).thenReturn(updatePerson);

        //then
        UserDto userDtoResult = userService.updateUser(updateDto);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("Alice", userDtoResult.getFullName());
        assertEquals("Title", userDtoResult.getTitle());
    }

    //get
    @Test
    @DisplayName("Выдать пользователя. Должно пройти успешно.")
    void getPerson_Test() {

        //given
        Long userId = 1L;

        Person savedPerson  = new Person();
        savedPerson.setId(1L);
        savedPerson.setFullName("Alice");
        savedPerson.setTitle("test title");
        savedPerson.setAge(100);

        UserDto result = new UserDto();
        result.setId(1L);
        result.setFullName("Alice");
        result.setTitle("test title");
        result.setAge(100);

        //when
        when(userMapper.personToUserDto(savedPerson)).thenReturn(result);
        when(userRepository.findById(userId)).thenReturn(Optional.of(savedPerson));

        //then
        UserDto userDtoResult = userService.getUserById(userId);
        assertEquals(1L, userDtoResult.getId());
        assertEquals("Alice", userDtoResult.getFullName());
        assertEquals(100, userDtoResult.getAge());
    }

    //delete
    @Test
    @DisplayName("Удалить пользователя. Должно пройти успешно.")
    void deletePerson_Test() {

        //given
        Long userId = 1L;

        //when
        doNothing().when(userRepository).deleteById(userId);

        //then
        userService.deleteUserById(userId);
    }
}
