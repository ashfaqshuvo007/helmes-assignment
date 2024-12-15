package com.helmes.assignment.unit;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.repositories.MyUserRepository;
import com.helmes.assignment.enums.Role;
import com.helmes.assignment.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private MyUserRepository myUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserById_Success() {
        MyUser mockUser = new MyUser();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        when(myUserRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        MyUser loadedUser = userService.loadUserById(1L);

        assertNotNull(loadedUser);
        assertEquals(mockUser.getId(), loadedUser.getId());
        assertEquals(mockUser.getUsername(), loadedUser.getUsername());
        verify(myUserRepository, times(1)).findById(1L);
    }

    @Test
    void testLoadUserById_NotFound() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.loadUserById(1L));
        assertNotNull(exception);
        assertEquals("User not found!", exception.getMessage());

        verify(myUserRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveUserWithUserRole_Success() {
        MyUser mockUser = new MyUser();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");
        mockUser.setPassword("123456");
        mockUser.setRole(Role.USER);

        when(passwordEncoder.encode("123456")).thenReturn("123456");
        when(myUserRepository.save(mockUser)).thenReturn(mockUser);

        userService.createUser(mockUser);

        verify(passwordEncoder, times(1)).encode("123456");
        verify(myUserRepository, times(1)).save(mockUser);
        assertEquals(1L, mockUser.getId());
        assertEquals("testUser", mockUser.getUsername());
        assertEquals("123456", mockUser.getPassword());
        assertEquals(Role.USER, mockUser.getRole());
        assertEquals("123456", mockUser.getPassword());
        verifyNoMoreInteractions(myUserRepository);
    }

    @Test
    void testSaveUserWithAdminRole_Success() {
        MyUser mockUser = new MyUser();
        mockUser.setId(1L);
        mockUser.setUsername("testAdmin");
        mockUser.setPassword("123456");
        mockUser.setRole(Role.ADMIN);

        when(passwordEncoder.encode("123456")).thenReturn("123456");
        when(myUserRepository.save(mockUser)).thenReturn(mockUser);

        userService.createUser(mockUser);

        verify(passwordEncoder, times(1)).encode("123456");
        verify(myUserRepository, times(1)).save(mockUser);
        assertEquals(1L, mockUser.getId());
        assertEquals("testAdmin", mockUser.getUsername());
        assertEquals("123456", mockUser.getPassword());
        assertEquals(Role.ADMIN, mockUser.getRole());
        assertEquals("123456", mockUser.getPassword());
        verifyNoMoreInteractions(myUserRepository);
    }

    @Test
    void testUpdateUser_Success() {
        MyUser existingUser = new MyUser();
        existingUser.setId(1L);
        existingUser.setUsername("existingUser");
        existingUser.setPassword("oldPassword");

        MyUser updatedUser = new MyUser();
        updatedUser.setId(1L);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("newPassword");

        when(myUserRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(myUserRepository.save(any(MyUser.class))).thenReturn(existingUser);

        userService.updateUser(updatedUser);

        verify(myUserRepository, times(1)).findById(1L);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(myUserRepository, times(1)).save(existingUser);

        assertEquals("updatedUser", existingUser.getUsername());
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void testUpdateUser_NotFound() {
        MyUser updatedUser = new MyUser();
        updatedUser.setId(1L);

        when(myUserRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(updatedUser));

        assertEquals("User not found", exception.getMessage());
        verify(myUserRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUserById_Success() {
        when(myUserRepository.existsById(1L)).thenReturn(true);
        doNothing().when(myUserRepository).deleteById(1L);

        userService.deleteUserById(1L);

        verify(myUserRepository, times(1)).existsById(1L);
        verify(myUserRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(myUserRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.deleteUserById(1L));

        assertEquals("User not found!", exception.getMessage());
        verify(myUserRepository, times(1)).existsById(1L);
        verify(myUserRepository, times(0)).deleteById(1L);
    }
}
