package xigua.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xigua.community.dto.PaginationDTO;
import xigua.community.dto.QuestionDTO;
import xigua.community.mapper.QuestionMapper;
import xigua.community.mapper.UserMapper;
import xigua.community.model.Quesiton;
import xigua.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        //size*(page - 1)
        Integer offset = size * (page - 1);
        List<Quesiton> quesitons = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Quesiton quesiton : quesitons) {
            User user =userMapper.findById(quesiton.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(quesiton,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);

        //size*(page - 1)
        Integer offset = size * (page - 1);
        List<Quesiton> quesitons = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Quesiton quesiton : quesitons) {
            User user =userMapper.findById(quesiton.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(quesiton,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Quesiton quesiton = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(quesiton,questionDTO);
        User user = userMapper.findById(quesiton.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Quesiton quesiton) {
        if (quesiton.getId() == null){
            //创建
            quesiton.setGmtCreate(System.currentTimeMillis());
            quesiton.setGmtModified(quesiton.getGmtCreate());
            questionMapper.create(quesiton);
        }else {
            //更新
            quesiton.setGmtModified(quesiton.getGmtCreate());
            questionMapper.update(quesiton);
        }
    }
}
