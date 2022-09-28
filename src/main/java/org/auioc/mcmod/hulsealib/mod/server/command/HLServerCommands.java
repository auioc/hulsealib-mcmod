package org.auioc.mcmod.hulsealib.mod.server.command;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.game.command.AHCommands;
import org.auioc.mcmod.arnicalib.game.command.DynamicCommandHandler;
import org.auioc.mcmod.arnicalib.game.command.node.VersionCommandNode;
import org.auioc.mcmod.arnicalib.game.cpw.EnvironmentUtils;
import org.auioc.mcmod.hulsealib.HulseaLib;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public final class HLServerCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal(HulseaLib.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        VersionCommandNode.addVersionNode(NODE, HulseaLib.class);
        if (EnvironmentUtils.IS_DEV) addTestNode(NODE);

        AHCommands.getServerNode(dispatcher).addChild(NODE);
    }

    private static void addTestNode(CommandNode<CommandSourceStack> node) {
        node.addChild(
            literal("test")
                .executes(
                    (ctx) -> DynamicCommandHandler.run(
                        "org.auioc.mcmod.hulsealib.mod.server.command.HLTestCommandHandler",
                        "run",
                        ctx
                    )
                )
                .build()
        );
    }

}
