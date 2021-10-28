package uz.asbt.digid.liveness.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.asbt.digid.common.utils.CommonUtils;

@Component("customPasswordEncoder")
public class CustomPasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(final CharSequence charSequence) {
    return CommonUtils.getMd5((String) charSequence);
  }

  @Override
  public boolean matches(final CharSequence charSequence, final String s) {
    final String sendPass = encode(charSequence.toString());
    final String realPass = encode(s);
    return sendPass.equals(realPass);
  }

}
