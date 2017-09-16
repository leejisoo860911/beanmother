package io.beanmother.core.converter;

import io.beanmother.core.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A KnwonConvertModule loads other libs converter modules.
 */
public abstract class KnownConverterModuleLoader {

    private final static String[] knownConverterModules;

    static {
        knownConverterModules = new String[]{
                "io.beanmother.java8.converter.JavaTimeConverterModule",
                "io.beanmother.core.DummyConverterModule" // for test
        };
    }

    /**
     * Load instances of converters in known converter modules
     * @return
     */
    public static List<ConverterModule> load() {
        List<ConverterModule> modules = new ArrayList<>();

        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        for (String klass : knownConverterModules) {
            try {
                Class<? extends ConverterModule> module = (Class<? extends ConverterModule>) classLoader.loadClass(klass);
                try {
                    modules.add(module.newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (ClassNotFoundException e) {
                // Do nothing
            } catch (ClassCastException e) {
                // Do nothing
            }
        }
        return modules;
    }
}
