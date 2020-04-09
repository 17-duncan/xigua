package xigua.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;
import xigua.community.dto.PaginationDTO;
import xigua.community.dto.QuestionDTO;
import xigua.community.mapper.QuestionMapper;
import xigua.community.mapper.UserMapper;
import xigua.community.model.Quesiton;
import xigua.community.model.User;
import xigua.community.service.QusetionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QusetionService qusetionService;


    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        PaginationDTO pagination = qusetionService.list(page,size);
            model.addAttribute("pagination",pagination);
        return "index";

    }
}
