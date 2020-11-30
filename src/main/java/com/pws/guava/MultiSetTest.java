package com.pws.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.pws.javafeatures.util.PrintUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Multiset: 允许把重复的元素放入集合.
 *
 * @author panws
 * @since 2017-06-23
 */
@Slf4j
public class MultiSetTest {

    private static final String GREEN = "GREEN";
    private static final String RED = "RED";
    private static final String YELLOW = "YELLOW";
    private static final String BLACK = "BLACK";

    public static void main(String[] args) {

        /**
         * Multiset: 允许把重复的元素放入集合.
         * 普通的 Set 就像这样 :[car, ship, bike]，而 Multiset 会是这样 : [car x 2, ship x 6, bike x 3]。
         */
        HashMultiset<String> hashMultiset = HashMultiset.create();
        hashMultiset.add(RED);
        hashMultiset.add(RED);
        hashMultiset.add(GREEN);
        hashMultiset.add(GREEN);
        hashMultiset.add(GREEN);
        hashMultiset.add(YELLOW);
        hashMultiset.add(BLACK, 5);

        log.info("hashMultiset.count(RED): {}", hashMultiset.count(RED));
        log.info("hashMultiset.count(GREEN): {}", hashMultiset.count(GREEN));
        log.info("hashMultiset.count(YELLOW): {}", hashMultiset.count(YELLOW));
        log.info("hashMultiset.count(BLACK): {}", hashMultiset.count(BLACK));
        // [RED x 2, YELLOW, GREEN x 3]
        log.info("hashMultiset: {}", hashMultiset);

        for (Multiset.Entry entry : hashMultiset.entrySet()) {
            PrintUtil.printSep();
            log.info("entry: {}", entry);
            log.info("entry.getElement(): {}", entry.getElement());
            log.info("entry.getCount(): {}", entry.getCount());
        }
    }
}
