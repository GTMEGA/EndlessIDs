package idextender;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;

public class Metadata {
    public static int maximumMetadata() {
        return GeneralConfig.extendBlockItem ? ExtendedConstants.maxMetadata : VanillaConstants.maxMetadata;
    }

    public static int metadataBits() {
        return GeneralConfig.extendBlockItem ? ExtendedConstants.bitsPerMetadata : VanillaConstants.bitsPerMetadata;
    }

}