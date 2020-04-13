package xigua.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xigua.community.dto.QuestionDTO;
import xigua.community.service.QuestionService;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute( "question",questionDTO);
        return "question";
    }
}
