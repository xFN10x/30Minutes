package fn10.minuteengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MinuteRandomUtils {

    private static final Random random = new Random();
    private static final HashMap<Integer, ArrayList<Long>> banks = new HashMap<>();

    public static ArrayList<Long> getBank(Integer bank) {
        if (!banks.containsKey(bank)) {
            banks.put(bank, new ArrayList<>());
        }
        return banks.get(bank);
    }

    public static Long getUnqiueId(Integer bank) {
        ArrayList<Long> Bank = getBank(bank);
        long l = random.nextLong();
        if (Bank.contains(l)) {
            return getUnqiueId(bank);
        } else {
            Bank.add(l);
            return l;
        }
    }

    public static void resetBank(Integer bank) {
        getBank(bank).clear();
    }
}
