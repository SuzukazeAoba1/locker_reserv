package com.globalin.locker.Controller;

import com.globalin.locker.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class HelloController {

    // MyService 주입
    @Autowired
    private MyService myService;

    @GetMapping("/hello")
    public String hello(Model model) throws SQLException {

        // DB에서 조회한 데이터 출력
        myService.fetchAllTables();
        //myService.fetchDataAndInsert();
        //myService.getData();

        // 모델에 메시지 추가
        model.addAttribute("message", "헬로 월드!");
        return "hello";  // /WEB-INF/views/hello.jsp

    }
}
