package com.controllers;

import com.models.Post;
import com.services.PostService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.repositories.PostRepository;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    // DI = Dependency Injection
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTours() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all post success", postRepository.findAll(), postRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListPosts(@RequestParam("pageIndex") int pageIndex,
                                                @RequestParam("pageSize") int pageSize,
                                                @RequestParam("sortColumn") String sortColumn,
                                                @RequestParam("sortOrder") String sortOrder,
                                                @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<Post> postResult = postService.getListPostWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Post> listPost = postResult.getListResult();
        long count = postResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list post success", listPost, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Post> fundPost = postRepository.findById(id);
        if (fundPost.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query post success", fundPost)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("false", "Can't find post with id " + id, "")
            );
        }
    }

    // insert new Post with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObject> insertPost(@RequestBody Post newPost) {
        List<Post> foundPosts = postRepository.findByCode(newPost.getCode().trim());
        if (foundPosts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Post code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert post success", postRepository.save(newPost))
        );
    }

    // update a Post => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> updatePost(@RequestBody Post newPost, @PathVariable Long id) {
        Post foundPost = postRepository.findById(id).map(post -> {
            post.setCode(newPost.getCode());
            post.setNote(newPost.getNote());
            post.setContent(newPost.getContent());
            return postRepository.save(post);
        }).orElseGet(() -> {
            newPost.setId(id);
            return postRepository.save(newPost);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("ok", "Upsert post success", foundPost)
        );
    }

    // Delete a Post => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> deletePost(@PathVariable Long id) {
        boolean isExist = postRepository.existsById(id);
        if (isExist) {
            postRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Delete post success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Can't find post " + id, "")
        );
    }

}













