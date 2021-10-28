package uz.asbt.digid.common.models;

public interface IResponse {

  int getCode();
  String getMessage();
  Object getResponse();

}
