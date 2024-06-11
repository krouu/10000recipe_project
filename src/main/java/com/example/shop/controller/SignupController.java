package com.example.shop.controller;

import com.example.shop.database.Users; // Users 엔티티를 사용하기 위해 임포트
import com.example.shop.repository.UsersRepository; // UsersRepository 인터페이스를 사용하기 위해 임포트
import org.springframework.beans.factory.annotation.Autowired; // 스프링의 의존성 주입을 위해 임포트
import org.springframework.stereotype.Controller; // 스프링의 컨트롤러로 사용하기 위해 임포트
import org.springframework.ui.Model; // 뷰로 데이터를 전달하기 위해 Model 객체를 사용하기 위해 임포트
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 처리하기 위해 임포트
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하기 위해 임포트
import org.springframework.web.bind.annotation.RequestParam; // 요청 매개변수를 처리하기 위해 임포트
import org.springframework.web.bind.annotation.ResponseBody; // 응답 본문을 반환하기 위해 임포트

import java.sql.Date; // 날짜 데이터를 사용하기 위해 임포트

@Controller // 이 클래스가 스프링 MVC의 컨트롤러임을 나타냄
public class SignupController {

    private final UsersRepository usersRepository; // UsersRepository 객체 선언

    @Autowired // UsersRepository를 자동으로 주입하기 위해 사용
    public SignupController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository; // 주입된 UsersRepository를 로컬 변수에 할당
    }

    @GetMapping("/signUp") // GET 요청이 "/signUp"으로 들어오면 실행됨
    public String showSignUpForm(Model model) {
        model.addAttribute("signUpPage", "Welcome to the signUp!"); // 모델에 데이터 추가
        return "signUp"; // signUp이라는 뷰 이름을 반환하여 회원가입 페이지를 표시
    }

    @GetMapping("/checkUserID") // GET 요청이 "/checkUserID"로 들어오면 실행됨
    @ResponseBody // 응답 본문으로 반환하기 위해 사용
    public String checkUserID(@RequestParam("userID") String userID) {
        boolean exists = usersRepository.existsByUserID(userID); // 주어진 userID가 존재하는지 확인
        return exists ? "exists" : "not exists"; // 존재 여부에 따라 문자열 반환
    }

    @PostMapping("/signup") // POST 요청이 "/signup"으로 들어오면 실행됨
    public String signupUser(@RequestParam String id, // 요청 매개변수 id를 처리
                             @RequestParam String username, // 요청 매개변수 username을 처리
                             @RequestParam String password, // 요청 매개변수 password를 처리
                             @RequestParam Date age, // 요청 매개변수 age를 처리
                             @RequestParam String phoneNumber, // 요청 매개변수 phoneNumber를 처리
                             @RequestParam String email, // 요청 매개변수 email을 처리
                             Model model) { // 뷰로 데이터를 전달하기 위해 Model 객체 사용
        Users user = new Users(); // 새로운 Users 객체 생성
        user.setUserID(id); // user 객체에 ID 설정
        user.setUsername(username); // user 객체에 username 설정
        user.setPassword(password); // user 객체에 password 설정
        user.setAge(age); // user 객체에 age 설정
        user.setPhoneNumber(phoneNumber); // user 객체에 phoneNumber 설정
        user.setEmail(email); // user 객체에 email 설정

        usersRepository.save(user); // 데이터베이스에 user 객체 저장
        model.addAttribute("message", "회원가입이 완료되었습니다."); // 모델에 메시지 추가
        return "login"; // login이라는 뷰 이름을 반환하여 로그인 페이지로 이동
    }
}
