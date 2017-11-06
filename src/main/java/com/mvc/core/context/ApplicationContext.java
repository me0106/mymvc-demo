package com.mvc.core.context;

import com.mvc.core.exception.ViewResolverUnDefinedException;
import com.mvc.core.utils.ApplicationConfig;
import com.mvc.core.viewresolver.ViewResolver;
import com.mvc.core.viewresolver.modelandview.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 我啊 on 2017-10-27 10:53
 */
public class ApplicationContext {

    /**
     * 临时容器将放入threadLocal内，防止线程读写问题
     */
    private final ThreadLocal<Map<Class, Bean>> context;

    /**
     * bean实例化后的临时容器
     */
    private static Map<Class, Bean> objectContext;
    /**
     * 当前类实例
     */
    private static ApplicationContext instance;

    /**
     * 视图解析器实例
     */

    private ViewResolver viewResolver;


    public static ApplicationContext getInstance() {
        return instance;
    }

    public static void newInstance(List<Class> classes) {
        if (instance != null) {
            return;
        }
        try {
            instance = new ApplicationContext(classes);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * ViewResolver的初始化在initContext()方法内进行
     *
     * @param classes 此classList是存放已扫描到的类的Class对象
     * @throws ReflectiveOperationException 反射异常
     */

    private ApplicationContext(List<Class> classes) throws ReflectiveOperationException {
        objectContext = new HashMap<>();
        initContext(classes);
//        initAutoWired(classes);
        initView();
        context = ThreadLocal.withInitial(() -> objectContext);
    }

    /**
     * 初始化容器
     * 其中，初始化有controller注解的对象，初始化xml配置里的对象，初始化ViewResolver
     */
    private void initContext(List<Class> classes) throws ReflectiveOperationException {
        classes.forEach((o) -> {
            try {
                objectContext.put(o, new Bean(o.getTypeName(), o, o.newInstance()));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
        ApplicationConfig.getXMLObjectList().forEach((o) -> {
            Class clazz = o.getClass();
            initViewResolver(o);
            objectContext.put(clazz, new Bean(clazz.getTypeName(), clazz, o));
        });
    }

    /**
     * 遍历所有扫描到的class的filed，有@Autowired的则进行注入
     *
     * @throws IllegalAccessException 反射异常
     */
//    private void initAutoWired(List<Class> classes) throws IllegalAccessException {
//        for (Class aClass : classes) {
//            Field[] fields = aClass.getDeclaredFields();
//            for (Field field : fields) {
//                if (field.isAnnotationPresent(Autowired.class)) {
//                    field.setAccessible(true);
//                    Class filedType = field.getType();
//                    field.set(objectContext.get(aClass).getBean(), objectContext.get(filedType).getBean());
//                }
//            }
//        }
//    }
//    void initAutoWired2(List<Class> classes){
//        classes.forEach((clazz)->{
//           Field[] fields=clazz.getDeclaredFields();
//            Arrays.asList(fields).forEach((field) ->{
//                if (field.isAnnotationPresent(Autowired.class)) {
//                    field.setAccessible(true);
//                    Class filedType = field.getType();
//                    try {
//                        field.set(objectContext.get(clazz).getBean(), objectContext.get(filedType).getBean());
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        });
//    }

    /**
     * 初始化ViewResolver
     *
     * @param o 由xml解析出的对象
     */
    private void initViewResolver(Object o) {
        if (ViewResolver.class.isAssignableFrom(o.getClass())) {
            viewResolver = (ViewResolver) o;
            }
    }

    /**
     * 初始化view
     *
     * @throws ReflectiveOperationException 反射异常
     */
    private void initView() throws ReflectiveOperationException {
        Class viewClass = View.class;
        Bean viewBean = new Bean(viewClass.getTypeName(), viewClass, viewClass.newInstance());
        objectContext.put(View.class, viewBean);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) context.get().get(clazz).getBean();
    }

    /**
     * 获取视图解析器
     *
     * @return 视图解析器
     */
    public ViewResolver getViewResolver() {
        if (viewResolver == null) {
            try {
                throw new ViewResolverUnDefinedException("ViewResolver未定义");
            } catch (ViewResolverUnDefinedException e) {
                e.printStackTrace();
            }
        }
        return viewResolver;
    }

    private static class Bean {

        private String beanId;

        private Class beanClass;

        private Object bean;

        Bean(String beanId, Class beanClass, Object bean) {
            this.beanId = beanId;
            this.beanClass = beanClass;
            this.bean = bean;
        }


        public String getBeanId() {
            return beanId;
        }

        public Class getBeanClass() {
            return beanClass;
        }

        public Object getBean() {
            return bean;
        }
    }


}
