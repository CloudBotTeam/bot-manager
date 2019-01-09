package cn.cloudbot.botmanager.dao;

import cn.cloudbot.botmanager.domain.bot.group.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServiceDao extends CrudRepository<Service, Long>{
    @Override
    <S extends Service> S save(S entity);

    @Override
    <S extends Service> Iterable<S> saveAll(Iterable<S> entities);

    Optional<Service> findByServ(String servName);
}
