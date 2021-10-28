package uz.asbt.digid.digidservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.models.entity.LivenessLoggerRequest;
import uz.asbt.digid.common.models.entity.LivenessLoggerResponse;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;
import uz.asbt.digid.common.service.monitor.ILivenessReport;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Aspect
@Slf4j
@Component
public class LivenessServiceAspect {

  private final ILivenessReport<LivenessLoggerRequest, LivenessLoggerResponse> liveness;

  @Autowired
  public LivenessServiceAspect(@Qualifier("livenessMonitorService") final ILivenessReport<LivenessLoggerRequest, LivenessLoggerResponse> liveness) {
    this.liveness = liveness;
  }

  @Pointcut("execution(* uz.asbt.digid.digidservice.service.LivenessService.*(..))")
  public void livenessRequest() {

  }

  @Before("livenessRequest()")
  public void before(final JoinPoint joinPoint) {
    final Object[] args = joinPoint.getArgs();

    if (args != null && args.length == 1) {

      final Object value = args[0];

      if (value instanceof ModelPersonAnswere) {
        final ModelPersonAnswere answere = Optional.of(value)
          .map(ModelPersonAnswere.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        logRequest(answere);
      }
    }
  }

  @After("livenessRequest()")
  public void after(final JoinPoint joinPoint) {
    final Object[] args = joinPoint.getArgs();
    if (args != null && args.length == 1) {

      final Object value = args[0];

      if (value instanceof ModelPersonAnswere) {
        final ModelPersonAnswere answere = Optional.of(value)
          .map(ModelPersonAnswere.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        logResponse(answere);
      }
    }
  }

  private void logRequest(final ModelPersonAnswere request) {
    log.info("========Begin create liveness request: {}========", request);
    try {
      final String photo = (request.getModelPersonPhoto() == null ||
        request.getModelPersonPhoto().getPersonPhoto() == null) ? null : request.getModelPersonPhoto().getPersonPhoto();
      final String additionalPhoto = (request.getModelPersonPhoto() == null ||
        request.getModelPersonPhoto().getAdditional() == null) ? null : request.getModelPersonPhoto().getAdditional();
      final LivenessLoggerRequest livenessLoggerRequest = LivenessLoggerRequest
        .builder()
        .guid(request.getRequestGuid())
        .pinpp(request.getModelPersonPassport().getPersonPassport().getPinpp())
        .documentNumber(request.getModelPersonPassport().getPersonPassport().getDocumentNumber())
        .requestDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
        .photo(photo)
        .additionalPhoto(additionalPhoto)
        .build();
      if(request.getModelServiceInfo() != null && request.getModelServiceInfo().getServiceInfo() != null && request.getModelServiceInfo().getServiceInfo().getScannerSerial() != null) {
        livenessLoggerRequest.setSerialNumber(request.getModelServiceInfo().getServiceInfo().getScannerSerial());
      } else {
        livenessLoggerRequest.setSerialNumber("modile client");
      }
      liveness.saveRequest(livenessLoggerRequest);
    } catch (final Exception ex) {
      log.error("EXCEPTION WHEN LOGGING REQUEST MODEL: {}", ex.getMessage());
    }
    log.info("========End create monitor request========");
  }

  private void logResponse(final ModelPersonAnswere response) {
    log.info("========Begin create liveness response: {}========", response);
    try {
      liveness.saveResponse(LivenessLoggerResponse
        .builder()
        .guid(response.getRequestGuid())
        .code(String.valueOf(response.getLivenessAnswere().getAnswere().getAnswereId()))
        .message(response.getLivenessAnswere().getAnswere().getAnswereComment())
        .responseDate(LocalDateTime.now(ZoneId.of("Asia/Tashkent")))
        .build());
    } catch (final Exception ex) {
      log.error("EXCEPTION WHEN LOGGING RESPONSE MODEL: {}", ex.getMessage());
    }
    log.info("========End create monitor response========");
  }

}
