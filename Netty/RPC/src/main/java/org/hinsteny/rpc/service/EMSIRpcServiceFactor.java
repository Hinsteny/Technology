package org.hinsteny.rpc.service;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.hinsteny.rpc.proxy.ObjectProxy;
import org.hinsteny.rpc.service.IRpcServiceFactor;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/24
 * @copyright: 2016 All rights reserved.
 */
public class EMSIRpcServiceFactor implements IRpcServiceFactor, Closeable {

    /**
     * A map to store rpc service instance by their given service interface .
     */
    protected ConcurrentMap<String, Object> registry = new ConcurrentHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    @Override
    public <T> T getRpcServiceClient(Class<T> interfaceClass) {
        String className = getAbsoluteClassName(interfaceClass);
        if (StringUtils.isBlank(className)) return null;
        Object instance = queryRpcService(className);
        if (instance != null) return (T)instance;
        instance = createAndSave(className, interfaceClass);

        return instance != null ? (T)instance : null;
    }

    /**
     * Gets or creates the ConcurrentMap of named loggers for a given LoggerContext.
     *
     * @param className the LoggerContext to get loggers for
     * @return the instanse for the given className
     */
    private Object queryRpcService(final String className) {
        Object instanse;
        lock.readLock ().lock ();
        try {
            instanse = registry.get (className);
        } finally {
            lock.readLock ().unlock ();
        }
        return instanse;
    }

    private Object createAndSave(final String className, Class interfaceClass) {
        lock.writeLock ().lock ();
        try {
            Object instanse = Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class<?>[]{interfaceClass},
                    new ObjectProxy<>(interfaceClass)
            );
            if (instanse == null) {
                registry.put (className, instanse);
            }
            return instanse;
        } finally {
            lock.writeLock ().unlock ();
        }
    }

    /**
     * Get the absolute name include class package path and class name for the interfaceClass
     *
     * @param interfaceClass
     * @return
     */
    private String getAbsoluteClassName(Class interfaceClass) {
        return (interfaceClass == null) ? "" : ClassUtils.getPackageName(interfaceClass) + ClassUtils.getSimpleName(interfaceClass);
    }

    @Override
    public void close() throws IOException {
        lock.writeLock ().lock ();
        try {
            registry = null;
        } finally {
            lock.writeLock ().unlock ();
        }
    }
}
