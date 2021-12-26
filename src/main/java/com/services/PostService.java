package com.services;

import com.models.Post;
import com.repositories.PostRepository;;
import com.ultils.modelHelper.ModelResult;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ModelResult<Post> getListPostWithSearch(int pageIndex, int pageSize, String sortColumn, String sortOrder,
                                                   List<SearchCriteria> searchCriteriaList) {
        ModelService<Post> modelService = new ModelService<>(postRepository);
        return modelService.getListWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
    }
}