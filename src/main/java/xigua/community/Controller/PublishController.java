package xigua.community.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xigua.community.mapper.QuestionMapper;
import xigua.community.mapper.UserMapper;
import xigua.community.model.Quesiton;
import xigua.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPubulish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }


        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0)
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
                }
            }
            if (user == null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }

        Quesiton quesiton = new Quesiton();
        quesiton.setTitle(title);
        quesiton.setDescription(description);
        quesiton.setTag(tag);
        quesiton.setCreator(user.getId());
        quesiton.setGmtCreate(System.currentTimeMillis());
        quesiton.setGmtModified(quesiton.getGmtCreate());
        questionMapper.create(quesiton);
        return "redirect:/";
    }

}
