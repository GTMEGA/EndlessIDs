package com.falsepattern.endlessids.asm;

public class MethodNotFoundException extends AsmTransformException
{
    public MethodNotFoundException(final String method) {
        super("can't find method " + method);
    }
}
