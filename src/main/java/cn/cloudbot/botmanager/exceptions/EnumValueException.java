package cn.cloudbot.botmanager.exceptions;

public class EnumValueException extends RuntimeException {
    public Object getValue() {
        return value;
    }

    Object value;

    public EnumValueException(Object value) {
        this.value = value;
    }
}
