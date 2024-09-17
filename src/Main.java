import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger count1 = new AtomicInteger(0);
    public static AtomicInteger count2 = new AtomicInteger(0);
    public static AtomicInteger count3 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread jo1 = new Thread(() -> {
            int c = 0;
            for (int i = 0; i < texts.length; i++) {
                if (texts[i].equals(new StringBuilder(texts[i]).reverse().toString())) {

                    for (int j = 1; j < texts[i].length() / 2 + 1; j++) {
                        if (texts[i].charAt(j - 1) == texts[i].charAt(j)) {
                            c++;
                        } else {
                            break;
                        }
                    }
                    if (c == 0) {
                        //System.out.println(texts[i] + "------------1");
                        switch (texts[i].length()) {
                            case 3:
                                count1.getAndIncrement();
                                break;
                            case 4:
                                count2.getAndIncrement();
                                break;
                            case 5:
                                count3.getAndIncrement();
                                break;
                        }
                    } else {
                        //System.out.println("_________not_________" + texts[i]);
                    }
                    c = 0;
                }

            }
        });

        Thread jo2 = new Thread(() -> {
            int c = 0;
            for (int i = 0; i < texts.length; i++) {

                for (int j = 0; j < texts[i].length(); j++) {
                    if (texts[i].charAt(0) == texts[i].charAt(j)) {
                        c++;
                        continue;
                    }
                    c = 0;
                    break;
                }
                if (c != 0) {

                    //System.out.println(texts[i] + "---------2");
                    switch (texts[i].length()) {
                        case 3:
                            count1.getAndIncrement();
                            break;
                        case 4:
                            count2.getAndIncrement();
                            break;
                        case 5:
                            count3.getAndIncrement();
                            break;
                    }

                }
                c = 0;
            }
        });


        Thread jo3 = new Thread(() -> {
            int c = 0;
            for (int i = 0; i < texts.length; i++) {
                for (int j = 1; j < texts[i].length(); j++) {
                    if (texts[i].charAt(j - 1) < texts[i].charAt(j)) {
                        c++;
                        continue;
                    }
                    c = 0;
                    break;
                }
                if (c != 0) {

                    //System.out.printf("\n" + texts[i] + "---------3");
                    switch (texts[i].length()) {
                        case 3:
                            count1.getAndIncrement();
                            break;
                        case 4:
                            count2.getAndIncrement();
                            break;
                        case 5:
                            count3.getAndIncrement();
                            break;
                    }

                }
                c = 0;
            }
        });

        jo1.start();
        jo2.start();
        jo3.start();

        jo1.join();
        jo2.join();
        jo3.join();

        System.out.printf("Красивых слов с длиной 3: %d шт\n" +
                "Красивых слов с длиной 4: %d шт\n" +
                "Красивых слов с длиной 5: %d шт", count1.get(), count2.get(), count3.get());
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}