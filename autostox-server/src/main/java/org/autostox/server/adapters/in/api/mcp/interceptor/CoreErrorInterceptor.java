package org.autostox.server.adapters.in.api.mcp.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import org.autostox.core.CoreException;

import io.quarkiverse.mcp.server.ToolCallException;

@CoreErrors
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class CoreErrorInterceptor {

    public CoreErrorInterceptor() {}

    @AroundInvoke
    Object wrap(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (CoreException e) {
            throw new ToolCallException(e.getMessage(), e);
        }
    }
}
