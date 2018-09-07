import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dingxiaodong on 2018/9/4.
 */
public class StreamTest {
    /**
     * what is Monad
     * http://www.ruanyifeng.com/blog/2015/07/monad.html
     *
     * 教程
     * http://www.cnblogs.com/chenpi/p/5915364.html
     * https://www.ibm.com/developerworks/cn/java/j-java-streams-1-brian-goetz/index.html
     * https://blog.csdn.net/weixin_38132621/article/details/81297789
     * https://blog.csdn.net/weixin_38132621/article/details/81297811
     */

    /**
     * how to create stream
     */

    public Stream<String> createStream(String[] data, String createWay) {
        switch (createWay) {
        case "byCollection":
            return new ArrayList<>(Arrays.asList(data)).stream();
        case "byStreamTemplate":
            return Stream.of(data[0]);
        case "byStreamTemplateArray":
            return Stream.of(data);
        case "byArray":
            return Arrays.stream(data);
        case "bufferedReader":
            return new BufferedReader(null).lines();
        default:
            return null;
        }
    }

    public IntStream createIntStream(int start, int end, int mod) {
        if (0 == mod) {
            return IntStream.range(start, end);
        } else {
            return IntStream.rangeClosed(start, end);
        }
    }

    public Stream<Integer> createStreamByGenerate() {
        final int i = 0;
        Stream<Integer> stream = Stream.generate(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return i;
        });
        return stream;
    }

    /**
     * 流到流之间的转换：
     * 比如 filter(过滤), map(映射转换), mapTo[Int|Long|Double] (到原子类型流的转换), flatMap(高维结构平铺)，flatMapTo[Int|Long|Double],
     * sorted(排序)，distinct(不重复值)，peek(执行某种操作，流不变，可用于调试)，limit(限制到指定元素数量), skip(跳过若干元素)
     *
     * 流到终值的转换：
     * 比如 toArray（转为数组），reduce（推导结果），collect（聚合结果），min(最小值), max(最大值), count (元素个数)
     * anyMatch (任一匹配), allMatch(所有都匹配)， noneMatch(一个都不匹配)， findFirst（选择首元素），findAny(任选一元素) ；
     *
     * 直接遍历：
     * forEach (不保序遍历，比如并行流), forEachOrdered（保序遍历)
     *
     * 构造流：
     * empty (构造空流)，of (单个元素的流及多元素顺序流)，iterate (无限长度的有序顺序流)，
     * generate (将数据提供器转换成无限非有序的顺序流)， concat (流的连接)， Builder (用于构造流的Builder对象)
     */

    public void reduce() {
        Integer[] ints = { 1, 2, 3, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        //求和
        int sum = Arrays.stream(ints).reduce(0, (x, y) -> (x + y));
        System.out.println("Sum:" + sum);
    }

    public void map() {
        Integer[] ints = { 1, 2, 3, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        // int数组转string数组
        String[] strings = Arrays.stream(ints).map(x -> String.valueOf(x)).toArray(String[]::new);
        Arrays.stream(strings).forEach(x -> System.out.println(x.getClass() + ":" + x));
    }

    /**
     * 递给 collect() 的 3 个函数（创建、填充和合并结果容器）
     * Collectors 类包含许多常见聚合操作的因素
     * 实现Collector接口可以自定义收集器
     * 最后一个参数”合并结果“只有并行模式下会用到
     */
    public void collect() {
        Integer[] ints = { 1, 2, 3, 1, 34, 234, 3242, 434534, 4345, 4354, 435, 34534, 53 };
        //int数组转string数组
        String[] strings = Arrays.stream(ints).map(x -> String.valueOf(x)).toArray(String[]::new);
        //用逗号间隔拼接字符串
        StringBuffer sb = Arrays.stream(ints).sorted().collect(StringBuffer::new, (x, y) -> {
            if (!"".equals(x.toString().trim()) && !"".equals(y.toString().trim())) {
                x.append(",").append(y);
            } else {
                x.append(y);
            }
        }, StringBuffer::append);
        String s = Arrays.stream(strings).collect(Collectors.joining());
        //求和
        Integer sum1 = Arrays.stream(ints).filter(x -> x > 1000).collect(Collectors.summingInt(x -> x));

        // 按照位数分组
        Map<Integer, List<Integer>> result = Arrays.stream(ints)
                .collect(Collectors.groupingBy(x -> x.toString().length()));
        result.keySet().forEach(x -> System.out.println(
                x + ":[" + result.get(x).stream().map(y -> String.valueOf(y)).collect(Collectors.joining(",")) + "]"));

        // 按照位数求平均值
        Map<Integer, Double> result2 = Arrays.stream(ints)
                .collect(Collectors.groupingBy(x -> x.toString().length(), Collectors.averagingDouble(Double::new)));
        result2.keySet().forEach(x -> System.out.println(x + ":" + result2.get(x)));

        //转map，有重复的key会报错
        //Map<String, String> map = Arrays.stream(ints)
        //      .collect(Collectors.toMap(x -> ("Key:" + x.toString()), x -> ("value" + String.valueOf(x * x))));
        // 最后一个参数增加重复value的处理
        Map<String, String> map = Arrays.stream(ints).collect(Collectors
                .toMap(x -> ("Key:" + x.toString()), x -> ("value" + String.valueOf(x * x)),
                        (x, y) -> (x.toString() + "," + y.toString())));
        map.keySet().stream().forEach(x -> System.out.println(x + "=" + map.get(x)));

        // 数字统计
        Arrays.stream(ints).mapToInt(x->x).summaryStatistics();
    }

    /**
     * limit
     */

    /**
     * 并行流可以并行处理数据，创建stream时使用parallelStream即可，
     * 并行流充分利用现代CPU的多核特性，并行流并不一定比顺序流更快，取决于一定的因素。
     * 使用流时，不要做会影响数据状态的操作，并行时会产生多线程问题，此外运行时会根据Terminal进行优化，不保证所有Intermediate都会被执行。
     * 用为了实现并发访问而设计的集合，比如 ConcurrentHashMap可以避免这种情况。
     */

    public static void main(String[] args) {
        IntStream.range(1,10);
        StreamTest streamTest = new StreamTest();
        streamTest.collect();
    }

}
