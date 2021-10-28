package uz.asbt.digid.gatewayservice.filter;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shoh Jahon tomonidan 8/8/2019 da qo'shilgan.
 */
public class ErrorFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        return "error";
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

        return null;
    }
}
