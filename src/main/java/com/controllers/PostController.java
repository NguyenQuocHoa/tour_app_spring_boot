package com.controllers;

import com.models.Post;
import com.models.ResponseObject;
import com.repositories.PostRepository;
import com.services.PostService;
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
    private PostRepository repository;
    @Autowired
    private PostService service;

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListPosts(@RequestParam("pageIndex") int pageIndex,
                                               @RequestParam("pageSize") int pageSize,
                                               @RequestParam("sortColumn") String sortColumn,
                                               @RequestParam("sortOrder") String sortOrder) {
        List<Post> listPost = service.getListPosts(pageIndex, pageSize, sortColumn, sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list tour success",  listPost)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Post> fundPost = repository.findById(id);
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
        List<Post> foundPosts = repository.findByCode(newPost.getCode().trim());
        if (foundPosts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Post code already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert post success", repository.save(newPost))
        );
    }

    // update a Post => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> updatePost(@RequestBody Post newPost, @PathVariable Long id) {
        Post foundPost = repository.findById(id).map(post -> {
            post.setCode(newPost.getCode());
            post.setNote(newPost.getNote());
            post.setContent(newPost.getContent());
            return repository.save(post);
        }).orElseGet(() -> {
            newPost.setId(id);
            return repository.save(newPost);
        });
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("ok", "Upsert post success", foundPost)
        );
    }

    // Delete a Post => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObject> deletePost(@PathVariable Long id) {
        boolean isExist = repository.existsById(id);
        if (isExist) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", "Delete post success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Can't find post " + id, "")
        );
    }

}













