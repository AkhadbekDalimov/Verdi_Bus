package uz.asbt.digid.digidservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.exception.AbstractBusinessLogicException;
import uz.asbt.digid.common.models.entity.client.Answere;
import uz.asbt.digid.common.models.entity.client.ModelPersonAnswere;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class PassportServiceCatchExceptionAspect {

  @AfterThrowing(
    value = "execution(* uz.asbt.digid.digidservice.service.PassportService.getPassportInfo(..))",
    throwing = "ex")
  public void catchException(final JoinPoint joinPoint, final AbstractBusinessLogicException ex) {
    final Object[] args = joinPoint.getArgs();
    if (args != null && args.length == 2) {

      final Object value = args[0];

      if (value instanceof ModelPersonAnswere) {
        final ModelPersonAnswere answere = Optional.of(value)
          .map(ModelPersonAnswere.class::cast)
          .orElseThrow(IllegalArgumentException::new);
        final Answere ans = Answere
          .builder()
          .answereId(ex.getCode())
          .answereComment(ex.getInfoMessage())
          .build();
        answere.setAnswere(ans);
      }
    }
  }

}
