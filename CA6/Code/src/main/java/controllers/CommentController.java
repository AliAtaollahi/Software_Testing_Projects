package controllers;

import exceptions.NotExistentComment;
import model.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.Baloot;

import java.util.Map;

@RestController
public class CommentController {

    private Baloot baloot = Baloot.getInstance();

    public void setBaloot(Baloot baloot) {
        this.baloot = baloot;
    }
    @PostMapping(value = "/comment/{id}/like")
    public ResponseEntity<String> likeComment(@PathVariable String id, @RequestBody Map<String, String> input) {
        int commentId = Integer.parseInt(id);
        try {
            Comment comment = baloot.getCommentById(commentId);
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
            Comment comment = baloot.getCommentById(commentId);
            String username = input.get("username");
            comment.addUserVote(username, "dislike");
            return new ResponseEntity<>("The comment was successfully disliked!", HttpStatus.OK);
        } catch (NotExistentComment e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
