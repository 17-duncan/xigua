package xigua.community.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xigua.community.dto.CommentCreateDTO;
import xigua.community.dto.CommentDTO;
import xigua.community.dto.QuestionDTO;
import xigua.community.service.CommentService;
import xigua.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> comments = commentService.listByQuestionId(id);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute( "question",questionDTO);
        model.addAttribute( "comments",comments);
        return "question";
    }
}
