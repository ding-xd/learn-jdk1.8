import java.util.Arrays;
import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dingxiaodong on 2018/9/5.
 */
public class FunctionalInterfaceTest {

    /**
     * 主要有三个核心概念：
     * 函数接口(Function)
     * 流(Stream)
     * 聚合器(Collector)
     * <p>
     * https://www.cnblogs.com/Dorae/p/7769868.html
     * http://www.cnblogs.com/taich-flute/p/8416080.html
     * http://www.cnblogs.com/lovesqcc/p/7077971.html
     * http://www.cnblogs.com/lovesqcc/p/6649018.html
     */

    public void newThread() {
        new Thread(() -> System.out.println("Hello world!")).start();
    }

    /**
     * 函数式接口（FunctionalInterface），通常会用@FunctionalInterface标注，目的是对某一个行为进行封装
     * 1、接口中只能有一个抽象方法
     * 2、可以有从Object继承过来的抽象方法
     * 3、接口中唯一抽象方法的命名并不重要
     * 比如在1.8中，Runnable、Comparator接口增加了@FunctionalInterface注解
     *
     * java.util.Function 包，定义了四个最基础的函数接口：
     * 1、Supplier<T>: 数据提供器，可以提供 T 类型对象；调用类的无参的构造器，提供了 get 方法
     * 2、Function<T,R>: 数据转换器，接收一个 T 类型的对象，返回一个 R类型的对象； 单参数单有返回值的行为接口
     * 3、Consumer<T>: 数据消费器， 接收一个 T类型的对象，无返回值，通常用于设置T对象的值； 单参数无返回值的行为接口
     * 4、Predicate<T>: 条件测试器，接收一个 T 类型的对象，返回布尔值，通常用于传递条件函数； 单参数布尔值的条件性接口
     * 在此基础上的扩展（参数个数）：
     * 1、BiConsumer<T,U>：双参数消费器
     * 2、BiFunction<T, U, R>：双参数转换器
     * 3、BiPredicate<T, U>；双参数条件测试器
     *在处理数据类型上扩展（确定的泛型类型）
     * [Int|Double|Long][Function|Consumer|Supplier|Predicate]
     *
     * 函数接口可以接受的入参：
     * 1、  类/对象的静态方法引用、实例方法引用。
     *          1.1、引用符号为双冒号 ::（可以调用有参数构造方法() -> new StringJoiner(delimiter, prefix, suffix)）。
     *          1.2、可以匿名引用(x, y) -> Integer.compare(x, y)等价于Integer::compare。
     *          1.3、可以引用父类的方法 super::toString
     * 2、类的构造器引用，比如 Class::new
     * 3、lambda表达式
     */

    /**
     * 返回一个T
     */
    public void testSupplier() {
        // 方法调用，打印5个随机数
        Supplier<Double> randomSupplier = Math::random;
        Stream.generate(randomSupplier).limit(5).forEach(System.out::println);

        // 构造方法
        Stream<Date> stream2 = Stream.generate(Date::new);

        //lambda表达式
        Stream<Integer> stream3 = Stream.generate(() -> {
            return 123 * 2123;
        });

    }

    /**
     * 把T转换为R
     */
    public void testFunction() {
        Integer[] ints = { 1, 2, 3, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        String[] strings1 = Arrays.stream(ints).map(String::valueOf).toArray(String[]::new);
        String[] string2 = Arrays.stream(ints).map(x -> x.toString()).toArray(String[]::new);
    }

    /**
     * 接受T，修改T的值，没有返回值
     */
    public void testConsumer() {
        Integer[] ints = { 1, 2, 3, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        //Arrays.stream(ints).forEachOrdered(System.out::println);
        StringBuffer sb = Arrays.stream(ints).sorted().collect(StringBuffer::new, (x, y) -> {
            if (!"".equals(x.toString().trim()) && !"".equals(y.toString().trim())) {
                x.append(",").append(y);
            } else {
                x.append(y);
            }
        }, StringBuffer::append);
        System.out.println(sb.toString());
    }

    /**
     * 接受T，返回boolean
     */
    public void testPredicate() {
        Integer[] ints = { 1, 2, 3, 1, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        boolean find = Arrays.stream(ints).sorted().allMatch((x) -> (x > 10000));
        Integer[] bigs = Arrays.stream(ints).filter(x -> x > 100).toArray(Integer[]::new);
        Arrays.stream(bigs).forEach(System.out::println);

        int[] ints2 = { 1, 2, 3, 1, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
    }

    public static void main(String[] args) {
        FunctionalInterfaceTest lambdaTest = new FunctionalInterfaceTest();
        lambdaTest.testPredicate();
    }

}
