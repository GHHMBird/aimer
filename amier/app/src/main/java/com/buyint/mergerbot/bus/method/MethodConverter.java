package com.buyint.mergerbot.bus.method;

import com.buyint.mergerbot.bus.MethodInfo;

import java.lang.reflect.Method;

/**
 * User: mcxiaoke
 * Date: 15/8/4
 * Time: 18:32
 */
interface MethodConverter {

    MethodInfo convert(final Method method);
}
