package com.helmes.assignment.integration;

import com.helmes.assignment.controllers.UserController;
import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTests {

    private MockMvc mockMvc;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        UserController userController = new UserController(userService);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testNewUser() throws Exception {
        mockMvc.perform(get("/users/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/user/new"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("roles"));

    }

    @Test
    void testSaveUserSuccess() throws Exception {
        doNothing().when(userService).createUser(any(MyUser.class));

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "John Doe")
                .param("role", "ADMIN"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/user/new"))
            .andExpect(model().attributeExists("alertType"))
            .andExpect(model().attributeExists("alertMessage"));

        verify(userService, times(1)).createUser(any(MyUser.class));
    }

    @Test
    void testSaveUserFailure() throws Exception {
        doThrow(new RuntimeException("Error"))
            .when(userService).createUser(any(MyUser.class));

        mockMvc.perform(post("/users")
                .param("name", "John Doe")
                .param("role", "ADMIN"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/user/new"))
            .andExpect(model().attributeExists("alertType"))
            .andExpect(model().attributeExists("alertMessage"));

        verify(userService, times(1)).createUser(any(MyUser.class));
    }

    @Test
    void testEditUser() throws Exception {
        MyUser mockUser = new MyUser();
        when(userService.loadUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/users/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/user/edit"))
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attributeExists("roles"));

        verify(userService, times(1)).loadUserById(1L);
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        doNothing().when(userService).updateUser(any(MyUser.class));

        mockMvc.perform(post("/admin/user/update")
                .param("id", "1")
                .param("name", "John Doe")
                .param("role", "ADMIN"))
            .andExpect(status().is3xxRedirection())
            .andExpect(
                redirectedUrl("/admin/home?alertType=success&alertMessage=User+updated+successfully"));


        verify(userService, times(1)).updateUser(any(MyUser.class));
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).deleteUserById(1L);

        mockMvc.perform(get("/users/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(flash().attributeExists("alertType"))
            .andExpect(flash().attributeExists("alertMessage"))
            .andExpect(redirectedUrl("/admin/home"));

        verify(userService, times(1)).deleteUserById(1L);
    }

    @Test
    void testDeleteUserFailure() throws Exception {
        doThrow(new RuntimeException("Error"))
            .when(userService).deleteUserById(1L);

        mockMvc.perform(get("/users/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(flash().attributeExists("alertType"))
            .andExpect(flash().attributeExists("alertMessage"))
            .andExpect(redirectedUrl("/admin/home"));

        verify(userService, times(1)).deleteUserById(1L);
    }
}
