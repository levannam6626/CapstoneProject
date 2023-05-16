package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Dto.UserDto;
import com.finalpbl.Dto.UserRequest;
import com.finalpbl.Service.User.IUserService;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin
public class UserController {
    
    @Autowired
    private IUserService userService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllUser()
    {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserbyID(@PathVariable(name = "id") Long id)
    {
        UserDto userDto = userService.getUserbyID(id);
        return ResponseEntity.ok(userDto);
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createUser(@ModelAttribute @Validated UserRequest userRequest)
    {
        String msg = userService.createUser(userRequest);
        if(msg.equals("Validation OK"))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
