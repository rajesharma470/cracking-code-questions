import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

public class MemoryExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = Logger.getLogger(MemoryExtension.class.getName());

    private static final String START_MEMORY = "START_MEMORY";

    public static long max_memory = Long.MAX_VALUE;

    private static final long KILOBYTE = 1024L;

    public static long bytesToKilobytes(long bytes) {
        return bytes / KILOBYTE;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        getStore(context).put(START_MEMORY, memory);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long startMemory = getStore(context).remove(START_MEMORY, long.class);
        Runtime runtime = Runtime.getRuntime();
        long memory =bytesToKilobytes( (runtime.totalMemory()- runtime.freeMemory()) - startMemory);
        Assertions.assertTrue(memory < max_memory, "Max memory out");
        logger.info(() ->
            String.format("Method [%s]- [%s] took %s KB memory.", testMethod.getName(), Arrays.toString(argumentsFrom(context)), memory));
    }

    private Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(getClass(), context.getRequiredTestMethod()));
    }

    private Object[] argumentsFrom(ExtensionContext context) {
        try {
            Method method = ReflectionUtils.findMethod(context.getClass(),"getTestDescriptor").orElse(null);
            TestMethodTestDescriptor descriptor = (TestMethodTestDescriptor) ReflectionUtils.invokeMethod(method, context);

            //Get the TestTemplateInvocationContext
            Field templateField = descriptor.getClass().getDeclaredField("invocationContext");
            templateField.setAccessible(true);
            TestTemplateInvocationContext template = (TestTemplateInvocationContext) templateField.get(descriptor);

            //Get the params finally
            Field argumentsField = template.getClass().getDeclaredField("arguments");
            argumentsField.setAccessible(true);
            Object[] params = (Object[]) argumentsField.get(template);

            return params;

        }catch(Exception e)
        {
            return new Object[0];
        }
    }
}