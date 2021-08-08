package wintersteve25.immersiveagriculture.common;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import wintersteve25.immersiveagriculture.common.config.Config;

public class IAReloadCommand {
    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("reload").executes((cs) -> reload(cs.getSource()));
    }

    public static int reload(CommandSource source) {
        Config.read();
        source.sendFeedback(new TranslationTextComponent("immersiveagriculture.reload"), true);
        return 1;
    }
}
