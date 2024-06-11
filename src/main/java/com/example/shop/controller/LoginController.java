package com.example.shop.controller;

import com.example.shop.database.UserService;
import com.example.shop.database.Users;
import com.example.shop.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UsersRepository usersRepository;
    private final UserService userService;

    @Autowired
    public LoginController(UsersRepository usersRepository, UserService userService) {
        this.usersRepository = usersRepository;
        this.userService = userService;
    }

    @GetMapping("/login") /*/login으로 들어오면 실행되는 곳이다./*/
    public String login(HttpSession session) { /*HttpSession 타입의 객체를 매개변수로 받는다. 이를통해 사용자의 세션정보를 얻을수있다.*/
        //세션이란 보통 사용자의 상태나 활동을 추적하기 위해 사용되는 데이터이다.

        // 세션에서 "loggedInUser"라는 속성을 가져와 Users객체로 캐스팅한다.
        // Users의 세션값을 loggedInUser객체에 담는다(USers타입으로)
        // 로그인을 안한 상태에서 로그인 버튼을 누르면 Users의 객체는 비어있다.


        /*  --설명--
            Users adminUser = new Users(); //DB와 맵핑되어있는 Users 객체타입으로 adminUSer 객체 생성
            adminUser.setUserID("admin"); //adminUser 객체안에 id 넣어주기
            adminUser.setUsername("Administrator"); // adminUser객체안에 Username 넣어주기
            session.setAttribute("loggedInUser", adminUser); // 세션에 loggedInUser이라는 객체 이름으로 adminUser값을 넣어준다.
                                                             // 즉 다른 유저들의 값들도 ("loggedInUser",User1) 이런식으로 넣어준다.
                                                             // 그리고 /login으로 받을때 세션값을 비교해서 유저인지 관리자인지 확인
                                                             // 로그아웃하면 세션값을 초기화해준다.
        */

        //
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            if ("admin".equals(loggedInUser.getUserID())) {
                return "redirect:/adminInfo"; // 관리자로 로그인된 상태면 회원관리 페이지로 리디렉션
            } else {
                return "redirect:/userInfo"; // 일반 사용자면 사용자 정보 페이지로 리디렉션
            }
        }
        return "login"; // 로그인되지 않은 상태면 로그인 페이지로 이동
    }

    //login페이지에서 데이터를 보냈을때 실행된다.
    @PostMapping("/login")
    //@RequestParam 파라미터를 메서드의 매개변수로 바인딩하는데 사용된다.
    public String loginUser(@RequestParam String id, //사용자가 입력한 id를 id로 바인딩
                            @RequestParam String pw, //사용자가 입력한 pw를 pw로 바인딩
                            Model model, //뷰에 데이터를 전달하기 위해 사용된다. 
                            HttpSession session) { // 세션을 사용하기위해 세션을 만들어놓았다.
        if ("admin".equals(id) && "admin".equals(pw)) { //만약 아이디와 비밀번호가 admin이라면
            Users adminUser = new Users(); //Users 타입으로 adminUser 객체 생성
            adminUser.setUserID("admin"); // adminUser 객체의 userID 속성을 admin으로 설정
            adminUser.setUsername("Administrator");// adminUser 객체의 setUsername속성을 Administrator으로 설정

            //loggedInUser은 세션에 저장될 속성의 이름이다.
            //adminUSer은 저장할 객체이다.
            session.setAttribute("loggedInUser", adminUser);
            return "/adminHome"; // 아이디와 비밀번호가 모두 "admin"이면 관리자 페이지로 리디렉션
        }

        //사용자 레포지토리를 통해 아이디와 비밀번호로 사용자를 조회
        //입력한 아이디와 비밀번호를 메서드에 넣는다.
        //DB에 사용자의 아이디와 비밀번호가 있으면 사용자의 정보를 user객체에 담고 없으면 null값을 담는다.
        Users user = usersRepository.findByUserIDAndPassword(id, pw);


        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; // 로그인 성공 시 메인 페이지로 리디렉션
        } else {
            model.addAttribute("message", "로그인 실패: 아이디나 비밀번호를 확인하세요.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/userInfo")
    public String userInfo(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // 로그인되지 않은 상태면 로그인 페이지로 리디렉션
        }
        model.addAttribute("loggedInUser", loggedInUser);
        return "userInfo"; // 사용자 정보 페이지로 이동
    }

    @GetMapping("/adminInfo")
    public String adminInfo(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !"admin".equals(loggedInUser.getUserID())) {
            return "redirect:/login"; // 로그인되지 않았거나 관리자가 아니면 로그인 페이지로 리디렉션
        }
        // 사용자 리스트를 모델에 추가
        List<Users> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);

        // 로그인된 사용자 정보를 모델에 추가
        model.addAttribute("loggedInUser", loggedInUser);

        return "adminInfo"; // JSP 파일로 이동
    }
}
