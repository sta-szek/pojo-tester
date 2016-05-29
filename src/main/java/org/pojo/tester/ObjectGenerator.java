package org.pojo.tester;


import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

class ObjectGenerator {

    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    ObjectGenerator(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        this.abstractFieldsValuesChanger = abstractFieldsValuesChanger;
    }


    Object createSameInstance(final Object object) {
        Object newInstance = createNewInstance(object.getClass());
        if (!object.equals(newInstance)) {
            newInstance = makeThemEqual(object, newInstance);
        }
        return newInstance;
    }

    Object createInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = createSameInstance(baseObject);
        abstractFieldsValuesChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
    }

    Object createNewInstance(final Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return createPrimitiveInstance(clazz);
        }

        if (hasDefaultConstructor(clazz)) {
            return createUsingDefaultConstructor(clazz);
        } else {
            return createFindingBestConstructor(clazz);
        }

    }

    private Object createPrimitiveInstance(final Class<?> clazz) {
        if (isBoolean(clazz)) {
            return false;
        } else {
            return 0;
        }

    }

    private boolean isBoolean(final Class<?> clazz) {
        return boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz);
    }

    private boolean hasDefaultConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getConstructors();
        return Arrays.stream(constructors)
                     .filter(this::isNoArgs)
                     .filter(this::isPublic)
                     .findAny()
                     .isPresent();
    }

    private Object createUsingDefaultConstructor(final Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectInstantiationException(clazz, e);
        }
    }

    private Object createFindingBestConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (final Constructor<?> constructor : constructors) {
            final Object createdObject = createObjectFromConstructor(constructor);
            if (createdObject != null) {
                return createdObject;
            }
        }
        throw new ObjectInstantiationException(clazz, "Class could not be created by any constructor.");
    }

    private Object createObjectFromConstructor(final Constructor<?> constructor) {
        makeAccessible(constructor);
        if (constructor.getParameterCount() == 0) {
            try {
                return constructor.newInstance();
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // ignore
                return null;
            }
        } else {
            final Class<?>[] parameterTypes = constructor.getParameterTypes();
            final Object[] constructorParameters = new Object[parameterTypes.length];
            int i = 0;
            for (final Class<?> parameterType : parameterTypes) {
                final Object parameter;
                if (hasDefaultConstructor(parameterType)) {
                    constructorParameters[i] = createUsingDefaultConstructor(parameterType);
                } else if (parameterType.isPrimitive()) {
                    if (isBoolean(parameterType)) {
                        constructorParameters[i] = false;
                    } else {
                        constructorParameters[i] = 0;
                    }
                } else if (parameterType.isArray()) {
                    constructorParameters[i] = Array.newInstance(parameterType.getComponentType(), 0);
                } else if (parameterType.isInterface()
                           || Modifier.isAbstract(parameterType.getModifiers())
                           || parameterType.isAnnotation()) {
                    parameter = Proxy.newProxyInstance(constructor.getDeclaringClass()
                                                                  .getClassLoader(),
                                                       new Class[]{parameterType},
                                                       (proxy, method, args) -> 0);
                    constructorParameters[i] = parameter;
                } else if (parameterType == Class.class) {
                    parameter = Object.class;
                    constructorParameters[i] = parameter;
                } else {
                    parameter = createNewInstance(parameterType);
                    constructorParameters[i] = parameter;
                }
                i++;

            }

            try {
                final Object o = constructor.newInstance(constructorParameters);
                return o;
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
                // ignore
                return null;
            }
        }
    }

    private void makeAccessible(final Constructor<?> constructor) {
        constructor.setAccessible(true);
    }

    private boolean isPublic(final Constructor<?> constructor) {
        return (constructor.getModifiers() & Modifier.PUBLIC) != 0;
    }

    private boolean isNoArgs(final Constructor<?> constructor) {
        return constructor.getParameterCount() == 0;
    }


    private Object makeThemEqual(final Object object, final Object newInstance) {
        String currentFieldName = "";
        try {
            final List<Field> allFields = FieldUtils.getAllFields(object.getClass());
            for (final Field field : allFields) {
                currentFieldName = field.getName();
                final Object value = FieldUtils.getValue(object, field);
                FieldUtils.setValue(newInstance, field, value);
            }
            return newInstance;
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(currentFieldName, object.getClass(), e);
        }
    }

}
