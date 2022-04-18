package com.wiken.example1.user.controller;

import com.wiken.example1.user.dto.UserDto;
import com.wiken.example1.user.exception.UserNotFoundException;
import com.wiken.example1.user.service.UserService;
import com.wiken.example1.user.vo.RequestUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.pretty.MessageHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(RequestUser requestUser) {
        return "user/signupForm";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute RequestUser requestUser,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws UserNotFoundException {

        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);

        redirectAttributes.addFlashAttribute("message", String.format("%s님 환영합니다.", userDto.getName()));

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "user/loginForm";
    }
}
