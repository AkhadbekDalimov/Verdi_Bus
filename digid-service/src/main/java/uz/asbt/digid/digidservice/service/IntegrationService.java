package uz.asbt.digid.digidservice.service;


import uz.asbt.digid.common.exception.IntegrationException;

public interface IntegrationService<T, V> {

    V get(T request) throws IntegrationException;

}
