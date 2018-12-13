package cn.cloudbot.botmanager.exceptions;

import java.util.Map;

public class PayloadCastError extends RuntimeException {
    private Map<String, Object> value_map;
    private Class target_class;

    public PayloadCastError(Map<String, Object> value_map, Class target_class) {
        this.value_map = value_map;
        this.target_class = target_class;
    }

    public Error genericError() {
        return new Error("value map cannot be cast to class " + target_class.getName());
    }
}
