package uz.asbt.digid.digidservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.models.entity.RequestMonitor;
import uz.asbt.digid.common.models.entity.ResponseMonitor;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.service.monitor.IMonitorReport;
import uz.asbt.digid.digidservice.util.AuthUtils;
import uz.asbt.digid.digidservice.util.RequestUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

@Aspect
@Slf4j
@Component
public class PassportControllerAspect {

  private final IMonitorReport<RequestMonitor, ResponseMonitor> monitor;
  private final AuthUtils authUtils;

  @Autowired
  public PassportControllerAspect(@Qualifier("monitorService") final IMonitorReport<RequestMonitor, ResponseMonitor> monitor,
                                  final AuthUtils authUtils) {
    this.monitor = monitor;
    this.authUtils = authUtils;
  }

  @Pointcut("execution(* uz.asbt.digid.digidservice.controller.PassportController.info(..))")
  public void commonRequest() {

  }


  @Pointcut("execution(* uz.asbt.digid.digidservice.controller.PinppController.*(..))")
  public void mobileRequest() {

  }

  @Before("commonRequest() || mobileRequest()")
  public void before(final JoinPoint joinPoint) {
    final Object[] args = joinPoint.getArgs();

    if (args != null && args.length > 3) {

      final Object value = args[1];
      final Object header = args[0];
      final Object lang = args[2];

      if (value instanceof ModelPersonAnswere) {

        final ModelPersonAnswere answere = Optional.of(value)
          .map(ModelPersonAnswere.class::cast)
          .map(person -> {
            //Проверка наличия GUID, если нет то проставляется автоматически
            RequestUtil.checkGUID(person);
            return person;
          })
          .orElseThrow(IllegalArgumentException::new);
        final HttpHeaders headers = Optional.of(header)
          .map(HttpHeaders.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        final Locale locale = Optional.of(lang)
          .map(Locale.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        //Проверка авторизации дял безридерных версий
        authUtils.checkAuth(answere, headers, locale);
        logRequest(answere);
      }
    }
  }

  @After("commonRequest() || mobileRequest()")
  public void after(final JoinPoint joinPoint) {
    final Object[] args = joinPoint.getArgs();
    if (args != null && args.length > 1) {

      final Object value = args[1];

      if (value instanceof ModelPersonAnswere) {
        final ModelPersonAnswere answere = Optional.of(value)
          .map(ModelPersonAnswere.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        logResponse(answere);
      }
    }
  }

  private void logRequest(final ModelPersonAnswere request) {
    log.info("========Begin create monitor request: {}========", request);
    try {
      final String photo = (request.getModelPersonPhoto() == null ||
        request.getModelPersonPhoto().getPersonPhoto() == null) ? null : request.getModelPersonPhoto().getPersonPhoto();
      monitor.saveRequest(RequestMonitor
        .builder()
        .guid(request.getRequestGuid())
        .serialNumber(request.getModelServiceInfo().getServiceInfo().getScannerSerial())
        .pinpp(request.getModelPersonPassport().getPersonPassport().getPinpp())
        .documentNumber(request.getModelPersonPassport().getPersonPassport().getDocumentNumber())
        .documentType(request.getModelPersonPassport().getPersonPassport().getDocumentType())
        .requestDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
        .photo(photo)
        .build());
    } catch (final Exception ex) {
      log.error("EXCEPTION WHEN LOGGING REQUEST MODEL: {}", ex.getMessage());
    }
    log.info("========End create monitor request========");
  }

  private void logResponse(final ModelPersonAnswere response) {
    log.info("========Begin create monitor response: {}========", response);
    try {
      monitor.saveResponse(ResponseMonitor
        .builder()
        .guid(response.getRequestGuid())
        .code(String.valueOf(response.getAnswere().getAnswereId()))
        .message(response.getAnswere().getAnswereComment())
        .responseDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
        .build());
    } catch (final Exception ex) {
      log.error("EXCEPTION WHEN LOGGING RESPONSE MODEL: {}", ex.getMessage());
    }
    log.info("========End create monitor response========");
  }

}
