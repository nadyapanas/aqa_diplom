package common.assertion;

import java.util.Objects;

import org.assertj.core.api.SoftAssertions;

public class SoftAssertionsStorage {
    private static final ThreadLocal<SoftAssertions> SOFT_ASSERTIONS_THREAD_LOCAL = new ThreadLocal<>();

    public static SoftAssertions softAssert() {
        if (Objects.isNull(SOFT_ASSERTIONS_THREAD_LOCAL.get())) {
            init();
        }
        return SOFT_ASSERTIONS_THREAD_LOCAL.get();
    }

    public static void init() {
        SOFT_ASSERTIONS_THREAD_LOCAL.set(new SoftAssertions());
    }
}
