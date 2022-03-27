package com.damonyuan.excal.common;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A specialized RuntimeException used internally by JQuantLib
 *
 * @author Richard Gomes
 */
public class ExcalException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(ExcalException.class);

    /**
     * Constructs a new runtime exception with null as its detail message.
     */
    public ExcalException() {
        super("LibraryException created");
        logger.error(this.toString());
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     *
     * @param message
     */
    public ExcalException(final String message) {
        super(message);
        logger.error(this.toString());
    }


    /**
     * Constructs a new runtime exception with the specified detail message and cause.
     * @param message
     * @param cause
     */
    public ExcalException(final String message, final Throwable cause) {
        super(message, cause);
        logger.error(this.toString());
    }


    /**
     * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
     * (which typically contains the class and detail message of cause).
     *
     * @param cause
     */
    public ExcalException(final Throwable cause) {
        super(cause);
        logger.error(this.toString());
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public synchronized Throwable initCause(final Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(final PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(final PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public void setStackTrace(final StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
