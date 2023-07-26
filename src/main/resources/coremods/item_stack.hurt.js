function initializeCoreMod() {
    ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    Opcodes = Java.type('org.objectweb.asm.Opcodes');

    InsnList = Java.type('org.objectweb.asm.tree.InsnList');

    VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

    return {
        'ItemStack#hurt': {
            target: {
                type: 'METHOD',
                class: 'net.minecraft.world.item.ItemStack',
                methodName: ASMAPI.mapMethod('m_220157_'),
                methodDesc:
                    '(ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)Z',
            },
            transformer: function (methodNode) {
                var toInject = new InsnList();
                {
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    toInject.add(new VarInsnNode(Opcodes.ILOAD, 1));
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 2));
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 3));
                    toInject.add(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'org/auioc/mcmod/hulsealib/mod/server/event/HLServerEventFactory',
                            'onItemHurt',
                            '(Lnet/minecraft/world/item/ItemStack;ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)I',
                            false
                        )
                    );
                    toInject.add(new VarInsnNode(Opcodes.ISTORE, 1));
                }

                var at = ASMAPI.findFirstInstructionAfter(
                    methodNode,
                    Opcodes.ILOAD,
                    0
                );
                methodNode.instructions.insertBefore(at, toInject);

                methodNode.visitMaxs(6, 7);

                // print(ASMAPI.methodNodeToString(methodNode));
                return methodNode;
            },
        },
    };
}

//! SRG <-> MCP
/*
    m_220157_    hurt    net/minecraft/world/item/ItemStack;hurt(ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)Z
*/

//! LocalVariableTable
/*
    Slot    Name        Signature
    6       k           I
    4       i           I
    5       j           I
    4       l           I
    0       this        Lnet/minecraft/world/item/ItemStack;
    1       p_220158_   I
    2       p_220159_   Lnet/minecraft/util/RandomSource;
    3       p_220160_   Lnet/minecraft/server/level/ServerPlayer;
*/

//! Code
/*
    public boolean hurt(int p_220158_, RandomSource p_220159_, @Nullable ServerPlayer p_220160_) {
        if (!this.isDamageableItem()) {
            return false;
        } else {
+           p_220158_ = org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory.onItemHurt(this, p_220158_, p_220159_, p_220160_);
            if (p_220158_ > 0) {
                //_ ...
            }
            //_ ...
        }
    }
*   ========== ByteCode ==========   *
    L0
        LINENUMBER 331 L0
        ALOAD 0
        INVOKEVIRTUAL net/minecraft/world/item/ItemStack.isDamageableItem ()Z
        IFNE L1
    L2
        LINENUMBER 332 L2
        ICONST_0
        IRETURN
    L1
        LINENUMBER 334 L1
    FRAME SAME
+       ALOAD 0
+       ILOAD 1
+       ALOAD 2
+       ALOAD 3
+       INVOKESTATIC org/auioc/mcmod/hulsealib/mod/server/event/HLServerEventFactory.onItemHurt (Lnet/minecraft/world/item/ItemStack;ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)I
+       ISTORE 1
        ILOAD 1
        IFLE L3
    L4
        LINENUMBER 335 L4
    //_ ...

-   MAXSTACK = 5
+   MAXSTACK = 6
*/
