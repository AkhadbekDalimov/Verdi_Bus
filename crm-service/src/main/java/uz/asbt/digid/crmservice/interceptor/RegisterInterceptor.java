package uz.asbt.digid.crmservice.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import uz.asbt.digid.common.models.entity.Client;
import uz.asbt.digid.common.models.entity.Device;
import uz.asbt.digid.common.wrapper.RequestWrapper;
import uz.asbt.digid.crmservice.repository.DeviceRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class RegisterInterceptor implements HandlerInterceptor {

  DeviceRepository deviceRepository;

  @Override
  public boolean preHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler) {
    try {
      log.info("Register interceptor");
      if (!request.getMethod().equals("OPTIONS")) {
        final RequestWrapper requestWrapper = new RequestWrapper(request);
        final Client client = new ObjectMapper().readValue(requestWrapper.getBody(), Client.class);
        if (null == client)
          throw new IllegalArgumentException("Can't parse client object");
        log.info("Client: {}", client.toString());
        final Device device = deviceRepository.findBySerialNumber(client.getDevice().getSerialNumber());
        if (null == device)
          throw new IllegalArgumentException("Can't find device");
        log.info("Device: {}", device.toString());
        if (device.equals(client.getDevice())) {
          log.info("Device is equals");
          return true;
        }
      } else {
        return true;
      }
    } catch (final Exception ex) {
      log.error(ex.getMessage());
    }
    log.info("Error while login");
    response.setStatus(403);
    return false;
  }

  @Override
  public void postHandle(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final Object handler,
                         final ModelAndView modelAndView) {

  }

  @Override
  public void afterCompletion(final HttpServletRequest request,
                              final HttpServletResponse response,
                              final Object handler,
                              final Exception ex) {

  }
}
