package exceptions;

import static defines.Errors.NOT_EXISTENT_COMMENT;

public class NotExistentComment extends Exception {
    public NotExistentComment() {
        super(NOT_EXISTENT_COMMENT);
    }
}
