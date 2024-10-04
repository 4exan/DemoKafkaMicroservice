package ua.kusakabe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.kusakabe.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        assert userRepository.findByUsername("4exan").isPresent();
    }

}
