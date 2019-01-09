package cn.cloudbot.botmanager.dao;

import cn.cloudbot.botmanager.domain.bot.group.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ServicerService {
    @Autowired
    private ServiceDao serviceDao;

    private Logger logger = Logger.getLogger(ServicerService.class.getName());

    public Service findServ(String name) {
        Optional<Service> serviceOptional = serviceDao.findByServ(name);
        Service ret;
        if (!serviceOptional.isPresent()) {
            ret = new Service();
            ret.setServ(name);
            serviceDao.save(ret);
            logger.info("Create service " + ret);
        } else {
            logger.info("Get server " + name);
            ret = serviceOptional.get();
        }
        return ret;
    }

    public Iterable<Service> listAll() {
        return serviceDao.findAll();
    }

}
