package com.example.unityconnect.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.unityconnect.DTO.UserDTO;
import com.example.unityconnect.entity.User;
import com.example.unityconnect.repository.UserRepository;

@RestController
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/unity/signup")
    public String registerUser(@RequestBody User user) {
             
        if (userRepository.findByName(user.getName()) != null) {
            return "이미 존재하는 사용자입니다!";
        }

        // 해시값으로 변환
        String encodedPw = passwordEncoder.encode(user.getPw());
        user.setPw(encodedPw);
        
        // DB에 저장
        userRepository.save(user);

        return "회원가입 성공!";
    }

    @PostMapping("/unity/signin")
    public UserDTO userSignin(@RequestBody User user) {


        User existingUser = userRepository.findByName(user.getName());
        if (existingUser == null) {
            return null;
        }

         // 입력한 평문 비밀번호와 해시된 비밀번호 비교
        boolean isPasswordMatch = passwordEncoder.matches(user.getPw(), existingUser.getPw());        

        if(isPasswordMatch){
            
            UserDTO userDTO = new UserDTO(existingUser.getPlayerId(), existingUser.getName(), existingUser.getBestScore());
            
            return  userDTO;
        }

        // 로그인 실패
        return  null;
    }

     @PostMapping("/unity/setscore")
     public String setBestScore(@RequestParam Long id,
     @RequestParam Long score) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
        return "사용자가 존재하지 않습니다.";
        }        
        user.setBestScore(score);

        // DB에 저장
        userRepository.save(user);

        return "저장 성공";
     }
}
