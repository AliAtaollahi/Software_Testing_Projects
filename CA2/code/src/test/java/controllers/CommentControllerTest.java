package controllers;

import controllers.CommentController;
import org.junit.jupiter.api.DisplayName;
import service.Baloot;
import model.Comment;
import exceptions.NotExistentComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static defines.Errors.NOT_EXISTENT_COMMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    private int commentId;
    private String commentUsername;
    private Map<String, String> input;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private Baloot baloot;

    @Mock
    private Comment comment;

    @BeforeEach
    public void setUp() {
        commentId = 1;
        commentUsername = "ali";

        input = new HashMap<>();
        input.put("username", commentUsername);

        commentController = new CommentController();
        commentController.setBaloot(baloot);
    }

    @Test
    @DisplayName("Test successfully liking a comment - valid comment ID and user liking")
    public void likeCommentTest() throws Exception {
        when(baloot.getCommentById(commentId)).thenReturn(comment);

        ResponseEntity<String> response = commentController.likeComment(String.valueOf(commentId), input);

        verify(baloot).getCommentById(commentId);
        verify(comment).addUserVote(commentUsername, "like");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The comment was successfully liked!", response.getBody());
    }

    @Test
    @DisplayName("Test liking an unavailable comment - non-existent comment ID")
    public void likeUnavailableCommentTest() throws Exception {
        when(baloot.getCommentById(commentId)).thenThrow(new NotExistentComment());

        ResponseEntity<String> response = commentController.likeComment(String.valueOf(commentId), input);

        verify(baloot).getCommentById(commentId);
        verify(comment, times(0)).addUserVote(any(), any());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_EXISTENT_COMMENT, response.getBody());
    }

    @Test
    @DisplayName("Test successfully disliking a comment - valid comment ID and user disliking")
    public void dislikeCommentTest() throws Exception {
        when(baloot.getCommentById(commentId)).thenReturn(comment);

        ResponseEntity<String> response = commentController.dislikeComment(String.valueOf(commentId), input);

        verify(baloot).getCommentById(commentId);
        verify(comment).addUserVote(commentUsername, "dislike");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The comment was successfully disliked!", response.getBody());
    }

    @Test
    @DisplayName("Test disliking an unavailable comment - non-existent comment ID")
    public void dislikeUnavailableCommentTest() throws Exception {
        when(baloot.getCommentById(commentId)).thenThrow(new NotExistentComment());

        ResponseEntity<String> response = commentController.dislikeComment(String.valueOf(commentId), input);

        verify(baloot).getCommentById(commentId);
        verify(comment, times(0)).addUserVote(any(), any());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(NOT_EXISTENT_COMMENT, response.getBody());
    }
}
