package xigua.community.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xigua.community.dto.QuestionDTO;
import xigua.community.mapper.QuestionMapper;
import xigua.community.model.Quesiton;
import xigua.community.model.User;
import xigua.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping ("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        QuestionDTO quesiton = questionService.getById(id);
        model.addAttribute("title",quesiton.getTitle());
        model.addAttribute("description",quesiton.getDescription());
        model.addAttribute("tag",quesiton.getTag());
        model.addAttribute("id",quesiton.getId());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPubulish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
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

        User user = (User) request.getSession().getAttribute("user");
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
        quesiton.setId(id);
        questionService.createOrUpdate(quesiton);
        return "redirect:/";
    }

}
