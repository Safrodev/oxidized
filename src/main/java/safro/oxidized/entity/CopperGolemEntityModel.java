package safro.oxidized.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CopperGolemEntityModel extends EntityModel<CopperGolemEntity> {
	private final ModelPart right_leg;
	private final ModelPart left_leg;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;

	public CopperGolemEntityModel(ModelPart root) {
		this.right_leg = root.getChild("right_leg");
		this.left_leg = root.getChild("left_leg");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.head = root.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(16,0).cuboid(-2.2F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(8,15).cuboid(0.2F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0,8).cuboid(-3.0F, -6.0F, -1.5F, 6.0F, 4.0F, 3.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(16,15).cuboid(3.0F, -6.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(18,8).cuboid(-4.0F, -6.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-3.0F, -10.0F, -2.0F, 6.0F, 4.0F, 4.0F).uv(0,0).cuboid(-0.5F, -7.6F, -3.0F, 1.0F, 2.0F, 1.0F).uv(6,15).cuboid(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F).uv(0,15).cuboid(-1.0F, -13.0F, -1.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		return TexturedModelData.of(modelData,32,32);
	}

	@Override
	public void setAngles(CopperGolemEntity entity, float f, float g, float h, float i, float j){
		this.right_leg.pitch = -1.5F * MathHelper.wrap(f, 13.0F) * g;
		this.left_leg.pitch = 1.5F * MathHelper.wrap(f, 13.0F) * g;
		this.right_leg.yaw = 0.0F;
		this.left_leg.yaw = 0.0F;
	 	if (entity.isPressingButtons()) {
			this.right_arm.pivotZ = -0.5F;
			this.left_arm.pivotZ = -0.5F;
			this.right_arm.pitch = 6.1F;
			this.left_arm.pitch = 6.1F;
		} else {
	 		this.right_arm.pivotZ = 0F;
	 		this.left_arm.pivotZ = 0F;
			this.right_arm.pitch = 0F;
			this.left_arm.pitch = 0F;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}