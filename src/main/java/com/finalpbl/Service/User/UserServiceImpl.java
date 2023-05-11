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

import com.finalpbl.Dto.UserDto;
import com.finalpbl.Dto.UserRequest;
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

    // @Autowired
	// private MessageSource messageSource;
    
    // @Override
    // public List<User> getUsers() {
    //     List<User> users;
    //     users = userRepository.findAll();

    //     return users;
    // }

    @Override 
    public List<UserDto> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        List<UserDto> AllUserResponse = users.stream().map(userMapper).collect(Collectors.toList());
        return AllUserResponse;
    }

    @Override
    public UserDto getUserbyID(Long id) {
       UserDto userDto = userRepository.findById(id).filter(user -> !user.getIsDeleted()).map(userMapper).orElseThrow(null);
        return userDto;
    }

//     @Override
//     public void deleteUser(Long id) {
//        User user = userRepository.findById(id).orElseThrow(null);
//        user.setIsDeleted(true);
//        userRepository.save(user);
//     }

//     @Override
//     public void deleteMultipleUsers(Long[] ids) {
//         for (Long id : ids)
//         {
//             User user = userRepository.findById(id).orElseThrow(null);
//             user.setIsDeleted(true);
//             userRepository.save(user);   
//         }
//     }

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

//     @Override
//     public Boolean updateUser(Long id, UserDto userDto) {
//         User user = userRepository.findById(id).orElseThrow(null);
//         if(user != null)
//         {
//             user.setFirstname(userDto.getFirstname());
//             user.setLastname(userDto.getLastname());
//             user.setEmail(userDto.getEmail());
//             user.setRole(Role.valueOf(userDto.getRole()));
//             user.setPhone(userDto.getPhone());
//             user.setAddress(userDto.getAddress());
//             userRepository.save(user);
//             return RESPONSE_OK;
//         }
//         return RESPONSE_REJECT;
//     }

}
