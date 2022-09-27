package org.auioc.mcmod.hulsealib.game.command;

import org.auioc.mcmod.arnicalib.ArnicaLib;
import org.auioc.mcmod.hulsealib.game.command.argument.CreativeModeTabArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;

public final class HLCommandArguments {

    private static <T extends ArgumentType<?>> void register(String id, Class<T> clazz, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(ArnicaLib.id(id).toString(), clazz, serializer);
    }

    public static void init() {
        register("creative_mode_tab", CreativeModeTabArgument.class, new EmptyArgumentSerializer<>(CreativeModeTabArgument::creativeModeTab));
    }

}
