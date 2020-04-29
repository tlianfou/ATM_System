package top.atm.message;

/**
 * @author taifu
 */

@SuppressWarnings ("unused")
public class DepositMessage extends AbstractMessage {
    public DepositMessage(Status status, String... messages) {
        super(status.getCode(), messages);
    }

    @Override
    public String debugStatus() {
        for (Status value : Status.values()) {
            if (value.code.equals(getStatus())) {
                return value.name();
            }
        }
        return "UNKNOWN";
    }

    public enum Status {
        OK(0),
        FORMAT_ERROR(1),
        DATABASE_ERROR(2),
        DIGITAL_ERROR(3),
        VERIFY_ERROR(4),
        OVER_LIMIT_ERROR(5),
        UNKNOWN(6);

        private final Integer code;

        Status(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }
}
