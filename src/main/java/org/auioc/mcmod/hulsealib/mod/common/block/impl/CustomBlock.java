package org.auioc.mcmod.hulsealib.mod.common.block.impl;

import java.util.Optional;
import org.auioc.mcmod.hulsealib.mod.client.gui.screen.CustomBlockScreen;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.HLBlockEntities;
import org.auioc.mcmod.hulsealib.mod.common.blockentity.impl.CustomBlockBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;

public class CustomBlock extends BaseEntityBlock {

    public CustomBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().dynamicShape().noDrops());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return CustomBlock.getBlockEntity(level, pos).map(CustomBlockBlockEntity::getShape).orElse(Shapes.block());
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return CustomBlock.getBlockEntity(level, pos).map(CustomBlockBlockEntity::getLight).orElse(0);
    }

    // ====================================================================== //

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide && player.hasPermissions(2) && player.isShiftKeyDown()) {
            var tile = CustomBlock.getBlockEntity(level, pos);
            if (tile.isPresent()) {
                DistExecutor.safeRunWhenOn(
                    Dist.CLIENT, () -> new SafeRunnable() {
                        public void run() { Minecraft.getInstance().setScreen(new CustomBlockScreen(tile.get())); }
                    }
                );
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.PASS;
    }

    // ====================================================================== //

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBlockBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.INVISIBLE; }

    // ============================================================================================================== //

    public static Optional<CustomBlockBlockEntity> getBlockEntity(BlockGetter level, BlockPos pos) {
        return level.getBlockEntity(pos, HLBlockEntities.CUSTOM_BLOCK_BLOCK_ENTITY.get());
    }

}
