package uz.asbt.digid.gatewayservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Shoh Jahon tomonidan 8/8/2019 da qo'shilgan.
 */
public class RouteFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.info("Inside Route Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Set<String> headers = (Set<String>) ctx.get("ignoredHeaders");
        headers.remove("authorization");
        ctx.addZuulRequestHeader("Authorization", request.getHeader("authorization"));
        return null;
    }
}
