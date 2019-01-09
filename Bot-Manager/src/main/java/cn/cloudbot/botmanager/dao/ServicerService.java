package cn.cloudbot.botmanager.dao;

import cn.cloudbot.botmanager.domain.bot.group.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ServicerService {
    @Autowired
    private ServiceDao serviceDao;

    public Service findServ(String name) {
        Optional<Service> serviceOptional = serviceDao.findByServ(name);
        Service ret;
        if (!serviceOptional.isPresent()) {
            ret = new Service();
            ret.setServ(name);
            serviceDao.save(ret);
        } else {
            ret = serviceOptional.get();
        }
        return ret;
    }
}
