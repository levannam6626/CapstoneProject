package com.finalpbl.Service.User;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.finalpbl.Constant.Role;
import com.finalpbl.Dto.User.UserDto;
import com.finalpbl.Dto.User.UserRequest;
import com.finalpbl.Mapper.UserResponseMapper;
import com.finalpbl.Model.User;
import com.finalpbl.Repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements IUserService{

    private final boolean RESPONSE_OK = true;

    private final boolean RESPONSE_REJECT = false;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserResponseMapper userMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override 
    public List<UserDto> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        List<UserDto> AllUserResponse = users.stream().map(userMapper).collect(Collectors.toList());
        return AllUserResponse;
    }

    @Override 
    public List<UserDto> getUsersSearch(String email)
    {
        List<User> users = userRepository.findUserSearch(email, 20);
        List<UserDto> AllUserResponse = users.stream().map(userMapper).collect(Collectors.toList());
        return AllUserResponse;
    }

    @Override
    public UserDto getUserbyID(Long id) {
       UserDto userDto = userRepository.findById(id).filter(user -> !user.getIsDeleted()).map(userMapper).orElseThrow(null);
        return userDto;
    }

    @Override
    public String deleteUser(Long id) {
       User user = userRepository.findById(id).orElseThrow(null);
       user.setIsDeleted(true);
       userRepository.save(user);
       return "Delete Success";
    }

    // @Override
    // public String deleteMultipleUsers(Long[] ids) {
    //     for (Long id : ids)
    //     {
    //         User user = userRepository.findById(id).orElseThrow(null);
    //         user.setIsDeleted(true);
    //         userRepository.save(user);   
    //     }
    //     return "Delete Success";
    // }

    @Override
    public String createUser(UserRequest userRequest) {
        User userValidate = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
        if(userValidate == null)
        {
            if(userRequest.validatePassword(userRequest))
            {
                User user = modelMapper.map(userRequest, User.class);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setIsDeleted(false);
                userRepository.save(user);
                return "Validation OK";
            }
            return "Password Invalid";
        }
        return "Email Invalid";
    }

    @Override
    public String editUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(null);
        if(user != null)
        {
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());
            user.setGender(userDto.getGender());
            userRepository.save(user);
            return "Edit Success";
        }
        return "Edit Failed";
    }

}
