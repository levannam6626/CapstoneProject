package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Dto.User.UserDto;
import com.finalpbl.Dto.User.UserRequest;
import com.finalpbl.Service.User.IUserService;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin
public class UserController {
    
    @Autowired
    private IUserService userService;

    @GetMapping(path = "view/get-by-search/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUsersSearch(@PathVariable(name = "email") String email)
    {
        List<UserDto> userDtos;
        if(email.equals("all")){
            userDtos = userService.getUsersSearch("");
        }
        else{
            userDtos = userService.getUsersSearch(email);
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping(path = "view/{id}")
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

    @RequestMapping(path = "/edit", method = RequestMethod.PATCH, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> editUser(@ModelAttribute UserDto user)
    {
        String msg = userService.editUser(user);
        if(msg.equals("Edit Success"))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(msg);
    }

    @RequestMapping(path = "/edit-password", method = RequestMethod.PATCH, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> editPassword(@ModelAttribute UserDto user)
    {
        String msg = userService.editPassword(user);
        if(msg.equals("Change Password Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }


    @DeleteMapping(path = "delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id)
    {
        String msg = userService.deleteUser(id);
        if(msg.equals("Delete Success"))
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
