package dev.negativekb.customitems.core;

import dev.negativekb.customitems.core.registry.ClassLoaderUtil;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CoreInitializer {

    private final List<Class<?>> registryClasses;

    public CoreInitializer() {
        registryClasses = ClassLoaderUtil.getClasses(Registry.class.getName());
        register();
    }

    public void register() {
        RegistryType[] values = RegistryType.values();

        List<RegistryType> collect = Arrays.stream(values)
                .sorted(Comparator.comparingInt(RegistryType::getPriority).reversed())
                .collect(Collectors.toList());

        collect.forEach(type -> registryClasses.stream().filter(aClass -> aClass.getDeclaredAnnotation(Registry.class).type().equals(type))
                .sorted(Comparator.comparingInt(value -> value.getDeclaredAnnotation(Registry.class).priority().getPriority()))
                .forEach(type::register));
    }


}
