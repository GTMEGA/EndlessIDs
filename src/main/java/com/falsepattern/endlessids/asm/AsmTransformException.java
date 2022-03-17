package com.falsepattern.endlessids.asm;

public class AsmTransformException extends RuntimeException
{
    private static final long serialVersionUID = 5128914670008752449L;
    
    public AsmTransformException(final String message) {
        super(message);
    }
    
    public AsmTransformException(final Throwable cause) {
        super(cause);
    }
    
    public AsmTransformException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
