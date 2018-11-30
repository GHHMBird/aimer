package com.buyint.mergerbot.bus.method;


import com.buyint.mergerbot.bus.Bus;
import com.buyint.mergerbot.bus.MethodInfo;

import java.util.Set;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:14
 */
public interface MethodFinder {

    Set<MethodInfo> find(final Bus bus, final Class<?> targetClass);
}
