function initializeCoreMod() {
    ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    Opcodes = Java.type('org.objectweb.asm.Opcodes');

    InsnList = Java.type('org.objectweb.asm.tree.InsnList');

    VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

    return {
        'CrossbowItem#shootProjectile': {
            target: {
                type: 'METHOD',
                class: 'net.minecraft.world.item.CrossbowItem',
                methodName: ASMAPI.mapMethod('m_40894_'),
                methodDesc:
                    '(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;FZFFF)V',
            },
            transformer: function (methodNode) {
                var toInject = new InsnList();
                {
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 1));
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 3));
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 11));
                    toInject.add(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'org/auioc/mcmod/hulsealib/mod/server/event/HLServerEventFactory',
                            'preCrossbowRelease',
                            '(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/Projectile;)V',
                            false
                        )
                    );
                }

                var at = methodNode.instructions.get(
                    methodNode.instructions.indexOf(
                        ASMAPI.findFirstMethodCall(
                            methodNode,
                            ASMAPI.MethodType.VIRTUAL,
                            'net/minecraft/world/level/Level',
                            ASMAPI.mapMethod('m_7967_'),
                            '(Lnet/minecraft/world/entity/Entity;)Z'
                        )
                    ) - 2
                );
                methodNode.instructions.insertBefore(at, toInject);

                // print(ASMAPI.methodNodeToString(methodNode));
                return methodNode;
            },
        },
    };
}

//! SRG <-> MCP
/*
    m_40894_    shootProjectile    net/minecraft/world/item/CrossbowItem;shootProjectile(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;FZFFF)V
    m_7967_     addFreshEntity     net/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z
*/

//! LocalVariableTable
/*
    Slot    Name                 Signature
    11      projectile           Lnet/minecraft/world/entity/projectile/Projectile;
    12      crossbowattackmob    Lnet/minecraft/world/entity/monster/CrossbowAttackMob;
    12      vec31                Lnet/minecraft/world/phys/Vec3;
    13      quaternionf          Lorg/joml/Quaternionf;
    14      vec3                 Lnet/minecraft/world/phys/Vec3;
    15      vector3f             Lorg/joml/Vector3f;
    10      flag                 Z
~   11      projectile           Lnet/minecraft/world/entity/projectile/Projectile;
~   0       p_40895_             Lnet/minecraft/world/level/Level;
~   1       p_40896_             Lnet/minecraft/world/entity/LivingEntity;
    2       p_40897_             Lnet/minecraft/world/InteractionHand;
~   3       p_40898_             Lnet/minecraft/world/item/ItemStack;
    4       p_40899_             Lnet/minecraft/world/item/ItemStack;
    5       p_40900_             F
    6       p_40901_             Z
    7       p_40902_             F
    8       p_40903_             F
    9       p_40904_             F
*/

//! Code
/*
    public ItemStack run(Level p_40895_, LivingEntity p_40896_, InteractionHand p_40897_, ItemStack p_40898_, ItemStack p_40899_, float p_40900_, boolean p_40901_, float p_40902_, float p_40903_, float p_40904_) {
        if (!p_40895_.isClientSide) {
        //_...
+       org.auioc.mcmod.hulsealib.mod.server.event.HLServerEventFactory.preCrossbowRelease(p_40896_, p_40898_, projectile);
        p_40895_.addFreshEntity(projectile);
        //_...
        }
    }
*   ========== ByteCode ==========   *
    //_ ...
    L21
        LINENUMBER 221 L21
+       ALOAD 1
+       ALOAD 3
+       ALOAD 11
+       INVOKESTATIC org/auioc/mcmod/hulsealib/mod/server/event/HLServerEventFactory.preCrossbowRelease (Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/projectile/Projectile;)V
        ALOAD 0
        ALOAD 11
        INVOKEVIRTUAL net/minecraft/world/level/Level.addFreshEntity (Lnet/minecraft/world/entity/Entity;)Z
        POP
    L22
        LINENUMBER 222 L22
    //_ ...
*/
