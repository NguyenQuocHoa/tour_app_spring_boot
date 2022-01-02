package com.services;

import com.models.Comment;
import com.repositories.CommentRepository;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public ModelResult<Comment> getListCommentWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                         List<SearchCriteria> searchCriteriaList) {
        ModelService<Comment> modelService = new ModelService<>(commentRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}
