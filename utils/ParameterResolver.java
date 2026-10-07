package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import mg.etu4370.annotation.AnjaParameter;
public class ParameterResolver {
    private static Object buildObject(
            Class<?> type,
            String prefix,
            HttpServletRequest request
    ) throws Exception {

        Object object = type.getDeclaredConstructor().newInstance();

        for (Field field : type.getDeclaredFields()) {

            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            String fieldName = field.getName();

            String parameterName;

            if (prefix == null || prefix.isEmpty()) {
                parameterName = fieldName;
            } else {
                parameterName = prefix + "." + fieldName;
            }

            Class<?> fieldType = field.getType();

            // Objet simple
            if (isSimpleType(fieldType)) {

                String value = request.getParameter(parameterName);

                if (value != null) {
                    Object converted =
                        convertSimpleType(fieldType, value);

                    field.set(object, converted);
                }

            } 
            // Objet complexe
            else {

                Object childObject =
                    buildObject(fieldType, parameterName, request);

                field.set(object, childObject);
            }
        }

        return object;
    }
    public static Object[] resolveParameters(Method method, HttpServletRequest request) {
        Parameter[] parameters = method.getParameters();
        Object[] resolvedParameters = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> paramType = parameter.getType();

            if(isSimpleType(paramType)){
                String paramName = parameter.isNamePresent() ? parameter.getName() : null;

                    // Si le paramètre porte l'annotation @AnjaParameter
                    if (parameter.isAnnotationPresent(AnjaParameter.class)) {
                        paramName = parameter.getAnnotation(AnjaParameter.class).name();
                    }

                    String httpValue = request.getParameter(paramName);
                    resolvedParameters[i] = convertSimpleType(paramType, httpValue);
            }else{

                try {
                String paramName = parameter.isNamePresent() ? parameter.getName() : null;

                    if (parameter.isAnnotationPresent(AnjaParameter.class)) {
                        paramName = parameter.getAnnotation(AnjaParameter.class).name();
                    }
                    Object complexObject = buildObject(paramType,paramName ,request);
                    resolvedParameters[i] = complexObject;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resolvedParameters;
    }
    private static boolean isSimpleType(Class<?> clazz) {
    return clazz.equals(String.class)
        || clazz.equals(int.class)
        || clazz.equals(Integer.class)
        || clazz.equals(double.class)
        || clazz.equals(Double.class)
        || clazz.equals(float.class)
        || clazz.equals(Float.class)
        || clazz.equals(boolean.class)
        || clazz.equals(Boolean.class)
        || clazz.equals(long.class)
        || clazz.equals(Long.class)
        || clazz.equals(LocalDate.class);
}

    private static Object convertSimpleType(Class<?> targetType, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        value = value.trim();

        if (targetType.equals(String.class)) return value;
        if (targetType.equals(int.class) || targetType.equals(Integer.class)) return Integer.parseInt(value);
        if (targetType.equals(double.class) || targetType.equals(Double.class)) return Double.parseDouble(value);
        if (targetType.equals(float.class) || targetType.equals(Float.class)) return Float.parseFloat(value);
        if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) return Boolean.parseBoolean(value);
        if (targetType.equals(long.class) || targetType.equals(Long.class)) return Long.parseLong(value);

        return value;
    }
}