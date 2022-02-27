package com.controllers;

import com.models.Comment;
import com.models.Post;
import com.services.PostService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.repositories.PostRepository;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3006", "http://someserver:3000"})
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
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Post> fundPost = postRepository.findById(id);
        if (fundPost.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query post success", fundPost)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find post with id " + id, "")
            );
        }
    }

    @GetMapping("/get-comment-by-post")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findCommentByPost(@RequestParam Long postId) {
        Optional<Post> fundPost = postRepository.findById(postId);
        return fundPost.map(post -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Query list comment by post success", post.getComments())
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObjectBase("failed", "Can't find comments by post id " + fundPost, "")
        ));
    }

    @GetMapping("/get-comment-text-by-post")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findCommentTextByPost(@RequestParam Long postId) {
        Optional<Post> fundPost = postRepository.findById(postId);
        return fundPost.map(post -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Query list comment text by post success",
                        post.getComments().stream().filter((Comment comment) -> true)) // comment.getAction().contentEquals("text")))
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObjectBase("failed", "Can't find comments by post id " + fundPost, "")
        ));
    }

    // insert new Post with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertPost(@RequestBody Post newPost) {
        List<Post> foundPosts = postRepository.findByCode(newPost.getCode().trim());
        if (foundPosts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObjectBase("failed", "Post code already exist", "")
            );
        }
        newPost.setCreated(new Date());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert post success", postRepository.save(newPost))
        );
    }

    // update a Post => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updatePost(@RequestBody Post newPost, @PathVariable Long id) {
        Post foundPost = postRepository.findById(id).map(post -> {
            post.setCode(newPost.getCode());
            post.setNote(newPost.getNote());
            post.setContent(newPost.getContent());
            return postRepository.save(post);
        }).orElseGet(() -> {
            newPost.setId(id);
            return postRepository.save(newPost);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Upsert post success", foundPost)
        );
    }

    // Delete a Post => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deletePost(@PathVariable Long id) {
        boolean isExist = postRepository.existsById(id);
        if (isExist) {
            postRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Delete post success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find post " + id, "")
        );
    }
}













