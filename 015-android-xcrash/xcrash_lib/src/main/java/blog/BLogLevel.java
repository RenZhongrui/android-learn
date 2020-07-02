package blog;

/**
 * 崩溃日志级别
 */

public class BLogLevel {

    public static final int CRASH = 1;

    /**
     * 获取日志等级名称
     *
     * @param level
     * @return
     */
    public static String getLevelName(int level) {
        if (level == CRASH) {
            return "crash";
        } else {
            return "";
        }
    }
}
