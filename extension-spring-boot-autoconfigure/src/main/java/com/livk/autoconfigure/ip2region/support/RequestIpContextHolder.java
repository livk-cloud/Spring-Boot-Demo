package com.livk.autoconfigure.ip2region.support;

import com.livk.commons.web.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

/**
 * <p>
 * RequestIpContextHolder
 * </p>
 *
 * @author livk
 */
public class RequestIpContextHolder {
    private static final ThreadLocal<String> context = new NamedThreadLocal<>("request ip context");

    private static final ThreadLocal<String> inheritableContext = new NamedInheritableThreadLocal<>("inheritable request ip context");

    /**
     * Set.
     *
     * @param ip the ip
     */
    public static void set(String ip) {
        set(ip, false);
    }

    /**
     * Set.
     *
     * @param ip          the ip
     * @param inheritable the inheritable
     */
    public static void set(String ip, boolean inheritable) {
        if (StringUtils.hasText(ip)) {
            if (inheritable) {
                inheritableContext.set(ip);
                context.remove();
            } else {
                context.set(ip);
                inheritableContext.remove();
            }
        } else {
            remove();
        }
    }

    /**
     * Set.
     *
     * @param request the request
     */
    public static void set(HttpServletRequest request) {
        String ip = WebUtils.realIp(request);
        set(ip);
    }

    /**
     * Compute if absent string.
     *
     * @param supplier the supplier
     * @return the string
     */
    public synchronized static String computeIfAbsent(Supplier<String> supplier) {
        String ip = get();
        if (StringUtils.hasText(ip)) {
            return ip;
        }
        String supplierIp = supplier.get();
        set(supplierIp);
        return supplierIp;
    }

    /**
     * Get string.
     *
     * @return the string
     */
    public static String get() {
        String ip = context.get();
        if (!StringUtils.hasText(ip)) {
            ip = inheritableContext.get();
        }
        return ip;
    }

    /**
     * Remove.
     */
    public static void remove() {
        context.remove();
        inheritableContext.remove();
    }
}
