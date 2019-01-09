package cn.cloudbot.botmanager.dao;

import cn.cloudbot.botmanager.domain.bot.group.BotEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface BotEntityService extends CrudRepository<BotEntity, Long> {
    @Override
    <S extends BotEntity> S save(S entity);

//    Optional<BotEntity> findByUuid(Long aLong);

    @Override
    Optional<BotEntity> findById(Long aLong);

    //    BotEntity findByContainer_id(String container__id);

    Collection<BotEntity> findAll();


    @Override
    void deleteAll();

    @Override
    void deleteById(Long aLong);

    Optional<BotEntity> findByIp(String ip);
}
