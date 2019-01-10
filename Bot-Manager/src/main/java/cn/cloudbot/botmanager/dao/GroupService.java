package cn.cloudbot.botmanager.dao;

import cn.cloudbot.botmanager.domain.bot.group.Group;
import cn.cloudbot.botmanager.domain.bot.group.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupService extends CrudRepository<Group, Long> {
    @Override
    <S extends Group> S save(S entity);

    @Override
    <S extends Group> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    Optional<Group> findById(Long aLong);

    Optional<Group> findByGroup(String group);

    Iterable<Group> findAllByBotId(Long botId);


    @Override
    Iterable<Group> findAll();

    Iterable<Group> findAllByServListContains(Service group);

    Iterable<Group> findAllByServListContaining(Service group);

    @Override
    void delete(Group entity);
}
