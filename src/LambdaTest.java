import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by dingxiaodong on 2018/9/6.
 */
public class LambdaTest {

    /**
     * λ表达式语法：
     * 1、λ表达式本质上是一个匿名方法。
     * 2、λ表达式有三部分组成：参数列表，箭头（->），以及一个表达式或语句块。
     * 3、基本语法: (parameters) -> expression或(parameters) ->{ statements; }
     */

    /**
     * λ表达式的类型
     * 1、λ表达式的类型（T），叫做“目标类型（target type）”。λ表达式的目标类型是“函数接口（functional interface）”。
     * 2、λ表达式的类型是由其上下文推导而来。
     * 3、lambda表达式的入参和T的方法入参在数量和类型上一一对应。
     * 4、lambda表达式的返回值和T的方法返回值相兼容（Compatible）。
     * 5、lambda表达式内所抛出的异常和T的方法throws类型相兼容。
     *
     */

    /**
     * 取代内部类
     */
    public void replaceInnerClass() {
        // 创建线程
        new Thread() {
            @Override public void run() {
                System.out.println("run thread1");
            }
        }.start();

        //使用lambda
        new Thread(() -> System.out.println("run thread2")).start();

        Runnable race1 = new Runnable() {
            @Override public void run() {
                System.out.println("run thread3");
            }
        };

        //使用lambda
        Runnable race2 = () -> System.out.println("run thread4");

        //对象比较(根据字符串最后一位比较)
        new ArrayList<String>().sort((x, y) -> (x.substring(x.length() - 1).compareTo(y.substring(y.length() - 1))));
        Comparator<String> lastCharComparator = (String x, String y) -> (x.substring(x.length() - 1).compareTo(y.substring(y.length() - 1)));

    }

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();
        lambdaTest.replaceInnerClass();
    }
}
