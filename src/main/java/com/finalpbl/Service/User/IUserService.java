package com.finalpbl.Service.User;
import java.util.List;

import com.finalpbl.Dto.UserDto;
import com.finalpbl.Dto.UserRequest;


public interface IUserService {
    public List<UserDto> getAllUsers();
    public List<UserDto> getUsersSearch(String email);
    public UserDto getUserbyID(Long id);
    public String editUser(UserDto user);
    public String deleteUser(Long id);
    //public String deleteMultipleUsers(Long[] ids);
    public String createUser(UserRequest userRequest);
}
