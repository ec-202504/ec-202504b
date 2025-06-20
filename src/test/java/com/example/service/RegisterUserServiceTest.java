package com.example.service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ユーザー登録のテスト.
 */
@SpringBootTest
class RegisterUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserService registerUserService;


    @Test
    void registerUserTest1() {
        RegisterUserForm mockUserForm = new RegisterUserForm();
        String mockLastName = "Mock";
        String mockFirstName = "User";
        String mockEmail = "mock@mock.com";
        String mockPassword = "mockPassword";
        String mockConfirmPassword = "mockPassword";
        String mockZipcode = "123-1234";
        String mockPrefecture = "東京都";
        String mockMunicipalities = "新宿区";
        String mockAddress = "新宿";
        String mockTelephone = "01001010101";

        mockUserForm.setLastName(mockLastName);
        mockUserForm.setFirstName(mockFirstName);
        mockUserForm.setEmail(mockEmail);
        mockUserForm.setPassword(mockPassword);
        mockUserForm.setConfirmPassword(mockConfirmPassword);
        mockUserForm.setZipcode(mockZipcode);
        mockUserForm.setPrefecture(mockPrefecture);
        mockUserForm.setMunicipalities(mockMunicipalities);
        mockUserForm.setAddress(mockAddress);
        mockUserForm.setTelephone(mockTelephone);

        when(passwordEncoder.encode(mockUserForm.getPassword())).thenReturn("encoded" + mockPassword);

        User user = new User();
        user.setName(mockUserForm.getLastName() + " " + mockUserForm.getFirstName());
        user.setEmail(mockUserForm.getEmail());
        String encodePassword = passwordEncoder.encode(mockUserForm.getPassword());
        user.setPassword(encodePassword);
        user.setZipcode(mockUserForm.getZipcode());
        user.setPrefecture(mockUserForm.getPrefecture());
        user.setMunicipalities(mockUserForm.getMunicipalities());
        user.setAddress(mockUserForm.getAddress());
        user.setTelephone(mockUserForm.getTelephone());
        user.setTelephone(mockUserForm.getTelephone());

        registerUserService.registerUser(mockUserForm);

        // ArgumentCaptor を使って insert メソッドに渡された User オブジェクトをキャプチャ
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).insert(userCaptor.capture());

        User actualUser = userCaptor.getValue(); // 実際に渡されたUserオブジェクト

        // 各フィールドの値を個別に検証
        assertEquals(mockUserForm.getLastName() + " " + mockUserForm.getFirstName(), actualUser.getName());
        assertEquals(mockUserForm.getEmail(), actualUser.getEmail());
        assertEquals(encodePassword, actualUser.getPassword()); // エンコードされたパスワードを検証
        assertEquals(mockUserForm.getZipcode(), actualUser.getZipcode());
        assertEquals(mockUserForm.getPrefecture(), actualUser.getPrefecture());
        assertEquals(mockUserForm.getMunicipalities(), actualUser.getMunicipalities());
        assertEquals(mockUserForm.getAddress(), actualUser.getAddress());
        assertEquals(mockUserForm.getTelephone(), actualUser.getTelephone());

    }
}