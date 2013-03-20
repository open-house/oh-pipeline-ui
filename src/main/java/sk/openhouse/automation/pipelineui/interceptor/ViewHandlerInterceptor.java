package sk.openhouse.automation.pipelineui.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pete
 */
public class ViewHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ViewHandlerInterceptor.class);

    private final String contextPath;

    public ViewHandlerInterceptor(String contextPath) {
        this.contextPath = StringUtils.stripEnd(contextPath, "/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
            throws Exception {

        logger.debug(String.format("Post Handle = %s", request.getServletPath()));

        /* request to resources (css etc.) does not have model and view set */
        if (mav == null) {
            logger.debug("No model and view set.");
            return;
        }

        /* redirect - no need to set view name, null - calling get on model map will return null anyway */
        String viewName = mav.getViewName();
        if (viewName == null || viewName.startsWith("redirect:") || viewName.startsWith("forward:")) {
            logger.debug(String.format("View name %s. Not setting any additional objects.", viewName));
            return;
        }

        mav.addObject("contextPath", contextPath);
        logger.debug(String.format("Set contextPath %s", contextPath));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
