package com.finalpbl.Service.User;
import java.util.List;

import com.finalpbl.Dto.UserDto;
import com.finalpbl.Dto.UserRequest;


public interface IUserService {
    //public List<User> getUsers();
    public List<UserDto> getAllUsers();
    public UserDto getUserbyID(Long id);
    // public void deleteUser(Long id);
    // public void deleteMultipleUsers(Long[] ids);
    public String createUser(UserRequest userRequest);
    // public Boolean updateUser(Long id, UserDto userDto);
}
