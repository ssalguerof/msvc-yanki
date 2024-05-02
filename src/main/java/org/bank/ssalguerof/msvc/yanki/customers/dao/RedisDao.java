package org.bank.ssalguerof.msvc.yanki.customers.dao;

public interface RedisDao{
    public void save(String key, String value);
    public String findById(String key);
    public void delete(String key);
}
