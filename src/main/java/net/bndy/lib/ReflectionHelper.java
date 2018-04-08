package net.bndy.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Reflection Helper Class
 */
public class ReflectionHelper {

    /**
     * When {@code Type} initialized with a value of an object, its fully qualified class name
     * will be prefixed with this.
     *
     * @see #getClassName(Type)
     */
    private static final String TYPE_CLASS_NAME_PREFIX = "class ";
    private static final String TYPE_INTERFACE_NAME_PREFIX = "interface ";

    /**
     * Utility class with static access methods, no need for constructor.
     */
    private ReflectionHelper() {
    }

    /**
     * Gets class of T generic type.
     *
     * @param <T> the generic type
     * @return the class
     */
    public static <T> Class<T> getClazz() {
        GenericType<T> gt = new GenericType<>();
        return gt.getClazz();
    }

    /**
     * {@link Object#toString()} value is the fully qualified class name prefixed
     * with {@link #TYPE_CLASS_NAME_PREFIX}. This method will substring it, for it to be eligible
     * for {@link Class#forName(String)}.
     *
     * @param type the {@code Type} value whose class name is needed.
     * @return {@code String} class name of the invoked {@code type}.
     * @see #getClass()
     */
    public static String getClassName(Type type) {
        if (type == null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_CLASS_NAME_PREFIX)) {
            className = className.substring(TYPE_CLASS_NAME_PREFIX.length());
        } else if (className.startsWith(TYPE_INTERFACE_NAME_PREFIX)) {
            className = className.substring(TYPE_INTERFACE_NAME_PREFIX.length());
        }
        return className;
    }

    /**
     * Returns the {@code Class} object associated with the given {@link Type}
     * depending on its fully qualified name.
     *
     * @param type the {@code Type} whose {@code Class} is needed.
     * @return the {@code Class} object for the class with the specified name.
     * @throws ClassNotFoundException if the class cannot be located.
     * @see #getClassName(Type)
     */
    public static Class<?> getClass(Type type)
        throws ClassNotFoundException {
        String className = getClassName(type);
        if (className == null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    /**
     * Creates a new instance of the class represented by this {@code Type} object.
     *
     * @param type the {@code Type} object whose its representing {@code Class} object
     *             will be instantiated.
     * @return a newly allocated instance of the class represented by
     * the invoked {@code Type} object.
     * @throws ClassNotFoundException if the class represented by this {@code Type} object
     *                                cannot be located.
     * @throws InstantiationException if this {@code Type} represents an abstract class,
     *                                an interface, an array class, a primitive type, or void;
     *                                or if the class has no nullary constructor;
     *                                or if the instantiation fails for some other reason.
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
     * @see Class#newInstance()
     */
    public static Object newInstance(Type type)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = getClass(type);
        if (clazz == null) {
            return null;
        }
        return clazz.newInstance();
    }

    /**
     * Returns an array of {@code Type} objects representing the actual type
     * arguments to this object.
     * If the returned value is null, then this object represents a non-parameterized
     * object.
     *
     * @param object the {@code object} whose type arguments are needed.
     * @return an array of {@code Type} objects representing the actual type
     * arguments to this object.
     * @see Class#getGenericSuperclass()
     */
    public static Type[] getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }

        return ((ParameterizedType) superclassType).getActualTypeArguments();
    }

    /**
     * Checks whether a {@code Constructor} object with no parameter types is specified
     * by the invoked {@code Class} object or not.
     *
     * @param clazz the {@code Class} object whose constructors are checked.
     * @return {@code true} if a {@code Constructor} object with no parameter types is specified.
     * @throws SecurityException If a security manager, <i>s</i> is present and any of the
     *                           following conditions is met:
     *                           <ul>
     *                           <li> invocation of
     *                           {@link SecurityManager#checkMemberAccess
     *                           s.checkMemberAccess(this, Member.PUBLIC)} denies
     *                           access to the constructor
     *                           <p>
     *                           <li> the caller's class loader is not the same as or an
     *                           ancestor of the class loader for the current class and
     *                           invocation of {@link SecurityManager#checkPackageAccess
     *                           s.checkPackageAccess()} denies access to the package
     *                           of this class
     *                           </ul>
     * @see Class#getConstructor(Class...)
     */
    public static boolean hasDefaultConstructor(Class<?> clazz) throws SecurityException {
        Class<?>[] empty = {};
        try {
            clazz.getConstructor(empty);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a {@code Class} object that identifies the
     * declared class for the field represented by the given {@code String name} parameter inside
     * the invoked {@code Class<?> clazz} parameter.
     *
     * @param clazz the {@code Class} object whose declared fields to be
     *              checked for a certain field.
     * @param name  the field name as {@code String} to be
     *              compared with {@link Field#getName()}
     * @return the {@code Class} object representing the type of given field name.
     * @see Class#getDeclaredFields()
     * @see Field#getType()
     */
    public static Class<?> getFieldClass(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }

        Class<?> propertyClass = null;

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equalsIgnoreCase(name)) {
                propertyClass = field.getType();
                break;
            }
        }

        return propertyClass;
    }

    /**
     * Returns a {@code Class} object that identifies the
     * declared class as a return type for the method represented by the given
     * {@code String name} parameter inside the invoked {@code Class<?> clazz} parameter.
     *
     * @param clazz the {@code Class} object whose declared methods to be
     *              checked for the wanted method name.
     * @param name  the method name as {@code String} to be
     *              compared with {@link Method#getName()}
     * @return the {@code Class} object representing the return type of the given method name.
     * @see Class#getDeclaredMethods()
     * @see Method#getReturnType()
     */
    public static Class<?> getMethodReturnType(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase();
        Class<?> returnType = null;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(name)) {
                returnType = method.getReturnType();
                break;
            }
        }

        return returnType;
    }

    /**
     * Extracts the enum constant of the specified enum class with the
     * specified name. The name must match exactly an identifier used
     * to declare an enum constant in the given class.
     *
     * @param clazz the {@code Class} object of the enum type from which
     *              to return a constant.
     * @param name  the name of the constant to return.
     * @return the enum constant of the specified enum type with the
     * specified name.
     * @throws IllegalArgumentException if the specified enum type has
     *                                  no constant with the specified name, or the specified
     *                                  class object does not represent an enum type.
     * @see Enum#valueOf(Class, String)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getEnumConstant(Class<?> clazz, String name) {
        if (clazz == null || name == null || name.isEmpty()) {
            return null;
        }
        return Enum.valueOf((Class<Enum>) clazz, name);
    }

    /**
     * Gets all fields of type clazz.
     *
     * @param clazz the {@code Class} object.
     * @return the list typed Field
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        Field[] publicFields = clazz.getFields();

        for (Field field : publicFields) {
            if (!result.contains(field)) {
                result.add(field);
            }
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!result.contains(field)) {
                result.add(field);
            }
        }
        return result;
    }

    /**
     * Gets the field by field name.
     *
     * @param name  the field name
     * @param clazz the class
     * @return the field typed {@code Field}
     */
    public static Field getField(String name, Class<?> clazz) {
        return CollectionHelper.first(getAllFields(clazz), field -> field.getName().equals(name));
    }

    /**
     * Sets field value.
     *
     * @param data       the data
     * @param fieldName  the field name
     * @param fieldValue the field value
     * @throws IllegalAccessException if illegal access
     */
    public static void setFieldValue(Object data, String fieldName, Object fieldValue) throws IllegalAccessException {
        Field field = getField(fieldName, data.getClass());
        if (field != null) {
            field.setAccessible(true);
            if (field.getType().getSuperclass() == Enum.class && fieldValue.getClass() == String.class) {
                field.set(data, Enum.valueOf((Class<Enum>) field.getType(), fieldValue.toString()));
            } else {
                field.set(data, fieldValue);
            }
        }
    }

    /**
     * Gets all methods of type clazz.
     *
     * @param clazz the {@code Class} object.
     * @return the list typed Method
     */
    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        Method[] publicMethods = clazz.getMethods();

        for (Method method : publicMethods) {
            if (!result.contains(method)) {
                result.add(method);
            }
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (!result.contains(method)) {
                result.add(method);
            }
        }
        return result;
    }

    /**
     * Gets the method by method name.
     *
     * @param name  the method name
     * @param clazz the class
     * @return the field typed {@code Method}
     */
    public static Method getMethod(String name, Class<?> clazz) {
        return CollectionHelper.first(getAllMethods(clazz), method -> method.getName().equals(name));
    }
}
