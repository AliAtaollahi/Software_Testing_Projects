package controllers;

import service.Baloot;
import model.Comment;
import exceptions.NotExistentComment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CommentController {
    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = input.get("username");
            comment.addUserVote(username, "like");
            return new ResponseEntity<>("The comment was successfully liked!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/comment/{id}/dislike")
    public ResponseEntity<String> dislikeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = Baloot.getInstance().getCommentById(commentId);
            String username = input.get("username");
            comment.addUserVote(username, "dislike");
            return new ResponseEntity<>("The comment was successfully disliked!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
