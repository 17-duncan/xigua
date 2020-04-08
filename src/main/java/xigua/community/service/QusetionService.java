package xigua.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xigua.community.dto.QuestionDTO;
import xigua.community.mapper.QuestionMapper;
import xigua.community.mapper.UserMapper;
import xigua.community.model.Quesiton;
import xigua.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QusetionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> list() {
        List<Quesiton> quesitons = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Quesiton quesiton : quesitons) {
            User user =userMapper.findById(quesiton.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(quesiton,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }


        return questionDTOList;
    }
}
