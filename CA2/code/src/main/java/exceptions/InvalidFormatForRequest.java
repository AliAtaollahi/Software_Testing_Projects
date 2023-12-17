package exceptions;

import static defines.Errors.INVALID_FORMAT_FOR_REQUEST;

public class InvalidFormatForRequest extends Exception {
    public InvalidFormatForRequest() {
        super(INVALID_FORMAT_FOR_REQUEST);
    }
}
