package sk.openhouse.automation.pipelineui.service;

/**
 * Exception that indicates that there has been problem to retrieve response from (or send request to)
 * pipeline service
 * 
 * @author pete
 */
public class PipelineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PipelineException(String message, Throwable e) {
        super(message, e);
    }
}
