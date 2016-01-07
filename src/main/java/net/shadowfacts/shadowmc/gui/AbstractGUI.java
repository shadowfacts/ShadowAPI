package net.shadowfacts.shadowmc.gui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.shadowfacts.shadowmc.util.Color;
import net.shadowfacts.shadowmc.util.MouseButton;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shadowfacts
 */
public abstract class AbstractGUI {

	protected Minecraft mc;

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	@Setter
	protected float zLevel = 0;

	@Setter
	protected AbstractGUI parent;
	protected List<AbstractGUI> children = new ArrayList<>();

	@Getter @Setter
	protected boolean visible = true;

	@Getter
	protected List<String> tooltip = new ArrayList<>();

	public AbstractGUI(int x, int y, int width, int height) {
		this.mc = Minecraft.getMinecraft();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean hasChildren() {
		return true;
	}

	public <T extends AbstractGUI> T addChild(T child) {
		child.parent = this;
		children.add(child);
		return child;
	}

	public AbstractGUI getRoot() {
		if (parent != null) {
			return parent.getRoot();
		}
		return this;
	}

	public boolean isWithinBounds(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width &&
				mouseY >= y && mouseY <= y + height;
	}

	public boolean isWithinMovableBounds(int mouseX, int mouseY) {
		return false;
	}

	protected void drawHoveringText(List<String> text, int x, int y) {
		getRoot().drawHoveringText(text, x, y);

//		TODO: Fix it. It's not working and I have no idea why
//		if (!text.isEmpty())
//		{
//			GlStateManager.disableRescaleNormal();
//			RenderHelper.disableStandardItemLighting();
//			GlStateManager.disableLighting();
//			GlStateManager.disableDepth();
//			int i = 0;
//
//			for (String s : text)
//			{
//				int j = mc.fontRendererObj.getStringWidth(s);
//
//				if (j > i)
//				{
//					i = j;
//				}
//			}
//
//			int l1 = x + 12;
//			int i2 = y - 12;
//			int k = 8;
//
//			if (text.size() > 1)
//			{
//				k += 2 + (text.size() - 1) * 10;
//			}
//
//			if (l1 + i > this.width)
//			{
//				l1 -= 28 + i;
//			}
//
//			if (i2 + k + 6 > this.height)
//			{
//				i2 = this.height - k - 6;
//			}
//
//			this.zLevel = 300.0F;
//			Color c1 = new Color(-267386864);
//			this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, c1, c1);
//			this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, c1, c1);
//			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, c1, c1);
//			this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, c1, c1);
//			this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, c1, c1);
//			Color c2 = new Color(1347420415);
//			Color c3 = new Color(1344798847);
//			this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, c2, c3);
//			this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, c2, c3);
//			this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, c2, c2);
//			this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, c3, c3);
//
//			for (int k1 = 0; k1 < text.size(); ++k1)
//			{
//				String s1 = text.get(k1);
//				mc.fontRendererObj.drawStringWithShadow(s1, (float)l1, (float)i2, -1);
//
//				if (k1 == 0)
//				{
//					i2 += 2;
//				}
//
//				i2 += 10;
//			}
//
//			this.zLevel = 0.0F;
//			GlStateManager.enableLighting();
//			GlStateManager.enableDepth();
//			RenderHelper.enableStandardItemLighting();
//			GlStateManager.enableRescaleNormal();
//		}
	}

	protected void drawText(String text, int x, int y, Color color) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, zLevel + .5f);
		mc.fontRendererObj.drawString(text, x, y, color.toARGB());
		Color.WHITE.apply();
		GL11.glPopMatrix(); // TODO: fixme
	}

	protected void drawText(String text, int x, int y) {
		drawText(text, x, y, Color.WHITE);
	}

	protected void drawCenteredText(String text, int x, int maxX, int y, int maxY, Color color) {
		int centerX = x + ((maxX - x) / 2) - (mc.fontRendererObj.getStringWidth(text) / 2);
		int centerY = y + ((maxY - y) / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2);
		drawText(text, centerX, centerY, color);
	}

	protected void drawCenteredText(String text, int x, int maxX, int y, int maxY) {
		drawCenteredText(text, x, maxX, y, maxY, Color.WHITE);
	}

	protected void bindTexture(ResourceLocation texture) {
		mc.getTextureManager().bindTexture(texture);
	}

	protected void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos((double)x, (double)(y + height), (double)this.zLevel).tex((double)((float)u * f), (double)((float)(v + height) * f1)).endVertex();
		worldrenderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(u + width) * f), (double)((float)(v + height) * f1)).endVertex();
		worldrenderer.pos((double)(x + width), (double)y, (double)this.zLevel).tex((double)((float)(u + width) * f), (double)((float)v * f1)).endVertex();
		worldrenderer.pos((double)x, (double)y, (double)this.zLevel).tex((double)((float)u * f), (double)((float)v * f1)).endVertex();
		tessellator.draw();
	}

	protected void drawRect(int x, int y, int width, int height, Color color) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		color.apply();
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		worldRenderer.pos((double)x, (double)y + height, zLevel).endVertex();
		worldRenderer.pos((double)x + width, (double)y + height, zLevel).endVertex();
		worldRenderer.pos((double)x + width, (double)y, zLevel).endVertex();
		worldRenderer.pos((double)x, (double)y, zLevel).endVertex();
		tessellator.draw();
		Color.WHITE.apply();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	protected void drawGradientRect(int x, int y, int width, int height, Color topColor, Color bottomColor) {
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) x + width, (double) y, (double)this.zLevel).color(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), topColor.getAlpha()).endVertex();
		worldrenderer.pos((double) x, (double) y, (double)this.zLevel).color(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), topColor.getAlpha()).endVertex();
		worldrenderer.pos((double) x, (double) y + height, (double)this.zLevel).color(bottomColor.getRed(), bottomColor.getRed(), bottomColor.getBlue(), bottomColor.getAlpha()).endVertex();
		worldrenderer.pos((double) x + width, (double) y + height, (double)this.zLevel).color(bottomColor.getRed(), bottomColor.getRed(), bottomColor.getBlue(), bottomColor.getAlpha()).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public void update() {

	}

	public void draw(int mouseX, int mouseY) {

	}

	public void drawTooltip(int x, int y) {
		drawHoveringText(getTooltip(), x, y);
	}

	public void handleMouseClicked(int mouseX, int mouseY, MouseButton button) {

	}

	public void handleMouseMove(int mouseX, int mouseY, MouseButton mouseButton) {

	}

	public void handleMouseReleased(int mouseX, int mouseY, MouseButton button) {

	}

	public void handleKeyPress(int keyCode, char charTyped) {

	}

	public void updatePosition(int newX, int newY) {
		x = newX;
		y = newY;
	}

}
