package cn.unipus.java.learning.concurrency.chapter8;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TestWordCount {
    public static void main(String[] args) throws IOException {
        //创建ConcurrentHashMap集合能不能保证原子性
        //这段代码的问题在于BiConsumer的三行代码并发原子操作
        //即map方法的组合之间可能受到线程切换的影响，从而是一个非原子性的操作
        //要保证结果的正确性需要保证BiConsumer代码具有原子性
        demo(() -> new ConcurrentHashMap<String, Integer>(), (map, words) -> {
            for (String word : words) {
                Integer counter = map.get(word);
                int newValue = counter == null ? 1 : counter + 1;
                map.put(word, newValue);
            }
        });

    }

    public static <V> void demo(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx);
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);

    }

    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\WorkSpace\\code\\java-concurrency\\tmp\\"
                + i +".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";

    public static void generateData() throws IOException {
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);

        for (int i = 0; i < 26; i++) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\WorkSpace\\code\\java-concurrency\\tmp\\" + (i + 1) + ".txt"), "UTF-8"));
            String collect = list.subList(i * count, (i + 1) * count).stream().collect(Collectors.joining("\n"));
            writer.write(collect);
            writer.flush();
            writer.close();
        }

    }
}
