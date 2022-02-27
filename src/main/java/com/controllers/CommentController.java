package com.controllers;

import com.models.Comment;
import com.repositories.CommentRepository;
import com.services.CommentService;
import com.ultils.modelHelper.ModelResult;
import com.ultils.modelHelper.ResponseObject;
import com.ultils.modelHelper.ResponseObjectBase;
import com.ultils.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/comments")
public class CommentController {
    // DI = Dependency
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/get-list-all")
    @ResponseBody
    ResponseEntity<ResponseObject> getListAllTours() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list all comment success", commentRepository.findAll(), commentRepository.count())
        );
    }

    @PostMapping("/get-list")
    @ResponseBody
    ResponseEntity<ResponseObject> getListComments(@RequestParam("pageIndex") int pageIndex,
                                                   @RequestParam("pageSize") int pageSize,
                                                   @RequestParam("sortColumn") String sortColumn,
                                                   @RequestParam("sortOrder") String sortOrder,
                                                   @RequestBody List<SearchCriteria> searchCriteriaList) {
        ModelResult<Comment> commentResult = commentService.getListCommentWithSearch(pageIndex, pageSize, sortColumn, sortOrder, searchCriteriaList);
        List<Comment> listComment = commentResult.getListResult();
        long count = commentResult.getCount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query list comment success", listComment, pageIndex, pageSize, count)
        );
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> findById(@PathVariable Long id) {
        Optional<Comment> fundComment = commentRepository.findById(id);
        if (fundComment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Query comment success", fundComment)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObjectBase("failed", "Can't find comment with id " + id, "")
            );
        }
    }

    // insert new Comment with POST method
    @PostMapping("/insert")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> insertComment(@RequestBody Comment newComment) {
        System.out.println(newComment);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Insert comment success", commentRepository.save(newComment))
        );
    }

    // update a Comment => Method PUT
    @PutMapping("/update/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> updateComment(@RequestBody Comment newComment, @PathVariable Long id) {
        Comment foundComment = commentRepository.findById(id).map(comment -> {
            comment.setCustomer(newComment.getCustomer());
            comment.setPost(newComment.getPost());
            comment.setActive(newComment.getActive());
            return commentRepository.save(comment);
        }).orElseGet(() -> {
            newComment.setId(id);
            return commentRepository.save(newComment);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObjectBase("ok", "Upsert comment success", foundComment)
        );
    }

    // Delete a Comment => Method DELETE
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    ResponseEntity<ResponseObjectBase> deleteComment(@PathVariable Long id) {
        boolean isExist = commentRepository.existsById(id);
        if (isExist) {
            commentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObjectBase("ok", "Delete comment success", id)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObjectBase("failed", "Can't find comment " + id, "")
        );
    }
}
