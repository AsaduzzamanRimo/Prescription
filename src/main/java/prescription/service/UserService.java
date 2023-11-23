package prescription.service;

import java.util.List;

import org.springframework.stereotype.Service;

import prescription.dto.UserDto;
import prescription.model.User;

@Service
public interface UserService {
	
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
