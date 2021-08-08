package wintersteve25.immersiveagriculture.common.config;

import javax.annotation.Nullable;
import java.util.List;

public class ConfigObject {
    private final List<CropConfiguration> configurations;

    public ConfigObject(List<CropConfiguration> configurations) {
        this.configurations = configurations;
    }

    public List<CropConfiguration> getConfigurations() {
        return configurations;
    }

    public static class CropConfiguration {
        private final String cropName;
        private final String seedName;
        private final int fertilizerAmount;
        private final boolean requireHighTier;
        @Nullable
        private final String requiredFluid;
        private final int requiredFluidAmount;
        private final boolean consumeFluid;

        public CropConfiguration(String cropName, String seedName, int fertilizerAmount, boolean requireHighTier, @Nullable String requiredFluid, int requiredFluidAmount, boolean consumeFluid) {
            this.cropName = cropName;
            this.seedName = seedName;
            this.fertilizerAmount = fertilizerAmount;
            this.requireHighTier = requireHighTier;
            this.requiredFluid = requiredFluid;
            this.requiredFluidAmount = requiredFluidAmount;
            this.consumeFluid = consumeFluid;
        }

        public String getCropName() {
            return cropName;
        }

        public String getSeedName() {
            return seedName;
        }

        public int getFertilizerAmount() {
            return fertilizerAmount;
        }

        public boolean isRequireHighTier() {
            return requireHighTier;
        }

        @Nullable
        public String getRequiredFluid() {
            return requiredFluid;
        }

        public int getRequiredFluidAmount() {
            return requiredFluidAmount;
        }

        public boolean isConsumeFluid() {
            return consumeFluid;
        }
    }
}
