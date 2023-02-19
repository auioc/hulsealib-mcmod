function initializeCoreMod() {
    ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    Opcodes = Java.type('org.objectweb.asm.Opcodes');

    InsnList = Java.type('org.objectweb.asm.tree.InsnList');

    VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

    function findFirstVarInsnNode(instructions, opcodes, _var) {
        var result = null;
        for (var i = 0; i < instructions.size(); i++) {
            var node = instructions.get(i);
            if (
                node instanceof VarInsnNode &&
                node.getOpcode() === opcodes &&
                node.var === _var
            ) {
                result = node;
                break;
            }
        }
        return result;
    }

    return {
        'PotionUtils#getColor': {
            target: {
                type: 'METHOD',
                class: 'net.minecraft.world.item.alchemy.PotionUtils',
                methodName: ASMAPI.mapMethod('m_43564_'),
                methodDesc: '(Ljava/util/Collection;)I',
            },
            transformer: function (methodNode) {
                var instructions = methodNode.instructions;

                var istore8Node = findFirstVarInsnNode(
                    instructions,
                    Opcodes.ISTORE,
                    8
                );
                if (istore8Node === null) {
                    ASMAPI.log(
                        'ERROR',
                        '[HulseaLib/CoreMod] potion_utils.get_color: Error locating istore8Node'
                    );
                    return methodNode;
                }

                var istore8Index = instructions.indexOf(istore8Node);

                instructions.set(
                    instructions.get(istore8Index - 2),
                    new VarInsnNode(Opcodes.ILOAD, 5)
                );

                instructions.set(
                    instructions.get(istore8Index - 1),
                    new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        'org/auioc/mcmod/hulsealib/game/effect/ICustomColorMobEffect',
                        'coremod_getColor',
                        '(Lnet/minecraft/world/effect/MobEffectInstance;I)I',
                        true
                    )
                );

                // print(ASMAPI.methodNodeToString(methodNode));
                return methodNode;
            },
        },
    };
}

//! SRG <-> MCP
/*
    m_43564_    render    net/minecraft/world/item/alchemy/PotionUtils/getColor (Ljava/util/Collection;)I
*/

//! LocalVariableTable
/*
    Slot    Name                 Signature
?   8       k                    I
    9       l                    I
~   7       mobeffectinstance    Lnet/minecraft/world/effect/MobEffectInstance;
    2       f                    F
    3       f1                   F
    4       f2                   F
~   5       j                    I
    0       p_43565_             Ljava/util/Collection;
    1       i                    I
*/

//! Code
/*
    public static int getColor(Collection<MobEffectInstance> p_43565_) {
        //_ ...
        if (p_43565_.isEmpty()) {
            //_ ...
        } else {
            //_ ...
            int j = 0;
            for(MobEffectInstance mobeffectinstance : p_43565_) {
                if (mobeffectinstance.isVisible()) {
-                   int k = mobeffectinstance.getEffect().getColor();
+                   int k = org.auioc.mcmod.hulsealib.game.effect.ICustomColorMobEffect.coremod_getColor(mobeffectinstance, j);
                    //_ ...
                }
            }
            //_ ...
        }
    }
*   ========== ByteCode ==========   *
    //_ ...
    L12
        LINENUMBER 101 L12
        ALOAD 7
-       INVOKEVIRTUAL net/minecraft/world/effect/MobEffectInstance.getEffect ()Lnet/minecraft/world/effect/MobEffect;
-       INVOKEVIRTUAL net/minecraft/world/effect/MobEffect.getColor ()I
+       ILOAD 5
+       INVOKESTATIC org/auioc/mcmod/hulsealib/game/effect/ICustomColorMobEffect.coremod_getColor (Lnet/minecraft/world/effect/MobEffectInstance;I)I (itf)
        ISTORE 8
    L13
        LINENUMBER 102 L13
    //_ ...
*/
