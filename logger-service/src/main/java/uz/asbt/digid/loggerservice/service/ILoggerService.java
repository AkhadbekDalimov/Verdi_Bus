package uz.asbt.digid.loggerservice.service;


import java.util.List;

/**
 * Shoh Jahon tomonidan 8/14/2019 da qo'shilgan.
 */
public interface ILoggerService<T> {

    T save(T obj);

    List<T> findAll();

    T update(T obj);

    T findById(String id);
}
