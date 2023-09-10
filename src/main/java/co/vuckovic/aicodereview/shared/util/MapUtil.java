package co.vuckovic.aicodereview.shared.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public final class MapUtil {

    public static Map<String, String> getSortedMapByKey(Map<String, String> map) {

        Map<String, String> sortedMap = new TreeMap<>(Comparator.naturalOrder());
        sortedMap.putAll(map);

        return sortedMap;
    }

}
