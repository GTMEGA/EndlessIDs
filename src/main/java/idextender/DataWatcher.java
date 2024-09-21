package idextender;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;

public class DataWatcher {
    public static int maxWatchableID() {
        return GeneralConfig.extendDataWatcher ? ExtendedConstants.maxWatchableID : VanillaConstants.maxWatchableID;
    }

    public static int watchableBits() {
        return GeneralConfig.extendDataWatcher ? ExtendedConstants.watchableBits : VanillaConstants.watchableBits;
    }

}