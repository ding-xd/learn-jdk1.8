import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingxiaodong on 2018/9/4.
 */
public class OtherFeature {

    public static void main(String[] args) {
        int i = 1_11_111;// since1.7
        BitSet bs = new BitSet(10000);
        System.out.println(bs.length());
        bs.set(998);
        Collections.emptyList().forEach(null);//since1.8
    }

    /**
     * Int支持_分隔字符
     * 定义二进制变量
     */
    public void defineParam() {
        int i = 1_11_111;
        int b = 0b111;//7
    }

    /**
     * collection增加forEach、removeIf（Since1.8）
     */
    public void newMethod2Collection() {
        Collections.emptyList().forEach(System.out::println);
        Collections.emptyList().removeIf(x -> x.hashCode() > 100);
    }

    /**
     * map增加
     */
    public void newMethod2Map() {
        Map<String, String> map = new HashMap<>();

        //元素操作
        map.putIfAbsent("key", "value");//没有key就放入
        map.remove("key");//根据key删除
        map.replace("key", "oldValue", "newValue");//替换

        //函数式操作
        map.forEach((x, y) -> System.out.println(x + ":" + y));
        map.replaceAll((k, v) -> k + v);//替换所有value，value计算得出
        map.compute("key", (k, v) -> k + v);//根据key值替换value，value计算得出
        map.computeIfAbsent("key", (k) -> k);//不存在就替换
        map.computeIfPresent("key", (k, v) -> k + v);//存在就替换
        map.merge("key", "defaultValue", (k, v) -> k + v);//不存在就用newValue，存在就计算value
    }

    /**
     * 接口支持静态方法和default方法（Since1.8）
     */
    public interface TestInterface {
        // 1.8中接口支持静态方法
        public static void staticMethod() {
            System.out.println("This is static method!");
        }

        // 1.8中接口支持default方法,为了实现二进制的向后兼容性（比如Collections增加了forEach），继承的接口和实现类会默认带有此方法，除非被override掉
        // 会带来多重继承问题，C实现了A、B，AB都有x方法，C必须override x，并指定调用A.supper.x或者自己实现一个
        default void defaultMethod() {
            System.out.println("This is default method!");
        }
    }

    /**
     * 类型注解 since1.8(JSR 308)
     * 以前只支持@Target(ElementType.PARAMETER)，用于方法参数
     * 作用：配合第三方的CheckFramework做编译时检查。基本没什么用
     */
    @Target({ ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })// 可用于变量声明，可用于class引用
    public @interface TypeAnnotation {
        String value() default "";
    }

    public void typeAnnotation(@TypeAnnotation Integer param) {
        @TypeAnnotation String s = "123";
        Object obj = "";
        String ss = (@TypeAnnotation("aaa") String) obj;
    }

}
