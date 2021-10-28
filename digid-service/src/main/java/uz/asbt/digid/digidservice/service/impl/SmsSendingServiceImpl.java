package uz.asbt.digid.digidservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import uz.asbt.digid.digidservice.controller.PhoneVerificationController;
import uz.asbt.digid.digidservice.service.SmsSendingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsSendingServiceImpl implements SmsSendingService {

  @Value("${sms-center.ip}")
  private String smsCenterIp;
  @Value("${sms-center.port}")
  private Integer smsCenterPort;

  private final MessageSource messageSource;

  private Socket clientSocket;
  private PrintWriter out;
  private BufferedReader in;

  @Override
  public void sendSmsCodeToPhoneNumber(final String phoneNumber, final Integer code) {
    log.info("begin send sms {} {}", phoneNumber, code);
    try {
      log.info("begin create message for sms center {} {}", phoneNumber, code);
      startConnection(smsCenterIp, smsCenterPort);
      sendMessage(buildSmsMessage(phoneNumber, code));
      stopConnection();
      log.info("end sending sms {} {}", phoneNumber, code);
    } catch (final IOException ex) {
      log.error("sms code sending exception{}", ex.getMessage());
    }
  }

  @Override
  public void informPersonAbtReachingMaxSmsRequest(final String phoneNumber) {
    log.info("info exceeded sms {}", phoneNumber);
    try {
      log.info("begin create message for exceeded count {}", phoneNumber);
      startConnection(smsCenterIp, smsCenterPort);
      sendMessage(buildExceededRequestCountMessage(phoneNumber));
      stopConnection();
      log.info("end exceeded sms sending {}", phoneNumber);
    } catch (final IOException ex) {
      log.error("exceeded sms sending exception {}", ex.getMessage());
    }
  }

  private String buildSmsMessage(final String phoneNumber, final Integer code) {
    final StringBuilder sb = new StringBuilder();
    sb.append("7");
    sb.append(phoneNumber);
    sb.append("@");
    sb.append("DigID activation code: " + code);
    return sb.toString();
  }

  private String buildExceededRequestCountMessage(final String phoneNumber) {
    final StringBuilder sb = new StringBuilder();
    sb.append("7");
    sb.append(phoneNumber);
    sb.append("@");
    log.info("thread locale {}", PhoneVerificationController.getLocaleThreadLocal());
    sb.append(messageSource.getMessage("message_118", null, PhoneVerificationController.getLocaleThreadLocal()));
    return sb.toString();
  }

  private void startConnection(final String ip, final int port) throws IOException {
    clientSocket = new Socket(ip, port);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  private String sendMessage(final String msg) throws IOException {
    out.println(msg);
    final String resp = in.readLine();
    return resp;
  }

  private void stopConnection() throws IOException {
    in.close();
    out.close();
    clientSocket.close();
  }
}
