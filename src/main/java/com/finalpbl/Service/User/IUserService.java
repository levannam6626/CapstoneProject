package com.finalpbl.Service.User;
import java.util.List;

import com.finalpbl.Dto.User.UserDto;
import com.finalpbl.Dto.User.UserRequest;


public interface IUserService {
    //public List<User> getUsers();
    public List<UserDto> getAllUsers();
    public List<UserDto> getUsersSearch(String email);
    public UserDto getUserbyID(Long id);
    public String deleteUser(Long id);
    //public String deleteMultipleUsers(Long[] ids);
    public String createUser(UserRequest userRequest);
    public String updateUser(String email, UserDto userDto);
}
