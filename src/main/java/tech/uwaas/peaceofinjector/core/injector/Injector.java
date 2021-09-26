package tech.uwaas.peaceofinjector.core.injector;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.classes.CacheableSearchConfig;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.ClassHunter.SearchResult;
import org.burningwave.core.classes.SearchConfig;
import tech.uwaas.peaceofinjector.core.annotation.Component;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SHUTO, Uwai
 */
public class Injector {

    private Map<Class<?>, Class<?>> diMap;

    private Map<Class<?>, Object> scope;

    private static Injector injector;

    private Injector() {
        super();
        this.diMap = new HashMap<>();
        this.scope = new HashMap<>();
    }

    /**
     * Start application
     *
     * @param mainClass
     */
    public static void startApplication(Class<?> mainClass) {
        try {
            synchronized (Injector.class) {
                if (injector == null) {
                    injector = new Injector();
                    injector.initFramework(mainClass);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Server from injector
     * */
    public static <T> T getService(Class<T> classes) {
        try {
            return injector.getBeanInstance(classes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * initialize the injector framework
     * */
    private void initFramework(Class<?> mainClass)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        Class<?>[] classes = getClasses(mainClass.getPackage().getName(), true);

        ComponentContainer componentContainer = ComponentContainer.getInstance();

        ClassHunter classHunter = componentContainer.getClassHunter();
        String packageRelPath = mainClass.getPackage().getName().replace(".", "/");

        try (SearchResult result = classHunter.findBy(
                SearchConfig.forResources(
                        packageRelPath
                ).by(ClassCriteria.create().allThoseThatMatch(cls -> cls.getAnnotation(Component.class) != null))
        )) {
            Collection<Class<?>> types = result.getClasses();

            for (Class<?> implementationClass : types) {
                Class<?>[] interfaces = implementationClass.getInterfaces();
                if (interfaces.length == 0) {
                    this.diMap.put(implementationClass, implementationClass);
                } else {
                    for (Class<?> targetInterface : interfaces) {
                        this.diMap.put(implementationClass, targetInterface);
                    }
                }
            }

            for (Class<?> targetClass : classes) {
                if (targetClass.isAnnotationPresent(Component.class)) {
                    Object classInstance = targetClass.newInstance();
                    this.scope.put(targetClass, classInstance);
                    InjectionUtil.autowire(this, targetClass, classInstance);
                }
            }
        }

    }

    /**
     * Get all the classes for the input package
     */
    public Class<?>[] getClasses(String packageName, boolean recursive) throws ClassNotFoundException, IOException {
        ComponentContainer container = ComponentContainer.getInstance();
        ClassHunter classHunter = container.getClassHunter();
        String packageRelPath = packageName.replace(".", "/");
        CacheableSearchConfig config = SearchConfig.forResources(packageRelPath);

        if (!recursive) {
            config.notRecursiveOnPath(
                    packageRelPath, false
            );
        }

        try (SearchResult result = classHunter.findBy(config)) {
            Collection<Class<?>> classes = result.getClasses();
            return classes.toArray(new Class[classes.size()]);
        }
    }

    /**
     * Create and Get the Object instance of the implementation class for input
     * interface service
     */
    @SuppressWarnings("unchecked")
    private <T> T getBeanInstance(Class<T> interfaceClass) throws InstantiationException, IllegalAccessException {
        return (T) getBeanInstance(interfaceClass, null, null);
    }

    /**
     * Overload get Bean Instantiation to handle qualifier and autowire by type
     */
    public <T> Object getBeanInstance(Class<T> interfaceClass, String fieldName, String qualifier)
            throws InstantiationException, IllegalAccessException {
        Class<?> implCls = getImplClass(interfaceClass, fieldName, qualifier);

        if (this.scope.containsKey(implCls)) {
            return this.scope.get(implCls);
        }

        synchronized (this.scope) {
            Object service = implCls.newInstance();
            this.scope.put(implCls, service);
            return service;
        }
    }

    /**
     * Get the name of the implementation class for input interface service
     */
    private Class<?> getImplClass(Class<?> interfaceClass, final String fieldName, final String qualifier) {
        Set<Map.Entry<Class<?>, Class<?>>> implementationClasses = diMap.entrySet().stream()
                .filter(entry -> entry.getValue() == interfaceClass).collect(Collectors.toSet());
        String errorMessage = "";
        if (implementationClasses == null || implementationClasses.size() == 0) {
            errorMessage = "no implementation found for interface " + interfaceClass.getName();
        } else if (implementationClasses.size() == 1) {
            Optional<Map.Entry<Class<?>, Class<?>>> optional = implementationClasses.stream().findFirst();
            if (optional.isPresent()) {
                return optional.get().getKey();
            }
        } else if (implementationClasses.size() > 1) {
            final String findBy = (qualifier == null || qualifier.trim().length() == 0) ? fieldName : qualifier;
            Optional<Map.Entry<Class<?>, Class<?>>> optional = implementationClasses.stream()
                    .filter(entry -> entry.getKey().getSimpleName().equalsIgnoreCase(findBy)).findAny();
            if (optional.isPresent()) {
                return optional.get().getKey();
            } else {
                errorMessage = "There are " + implementationClasses.size() + " of interface " + interfaceClass.getName()
                        + " Expected single implementation or make use of @CustomQualifier to resolve conflict";
            }
        }
        throw new RuntimeErrorException(new Error(errorMessage));
    }

}
