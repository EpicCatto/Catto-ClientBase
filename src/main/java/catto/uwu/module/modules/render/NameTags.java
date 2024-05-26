package catto.uwu.module.modules.render;

import catto.uwu.event.EventTarget;
import catto.uwu.event.impl.Event3D;
import catto.uwu.module.api.Category;
import catto.uwu.module.api.Module;
import catto.uwu.module.api.ModuleData;
import catto.uwu.module.modules.combat.AntiBot;
import catto.uwu.module.modules.player.Teams;
import catto.uwu.module.settings.impl.BooleanSetting;
import catto.uwu.utils.font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDisable;

@ModuleData(name="NameTags", description = "See ppl throu walls", category = Category.RENDER)
public class NameTags extends Module {

    @EventTarget
    private void onRender(Event3D render) {
        ArrayList<EntityPlayer> validEnt = new ArrayList<EntityPlayer>();
        for (EntityLivingBase player2 : mc.theWorld.playerEntities) {
            if (player2.isInvisible()) {
                if (!validEnt.contains(player2))
                    continue;
                validEnt.remove(player2);
                continue;
            }
            if (validEnt.size() > 100)
                break;
            if (validEnt.contains(player2))
                continue;
            validEnt.add((EntityPlayer) player2);
            continue;
        }

        validEnt.forEach(player -> {
            float x = (float) (player.lastTickPosX
                    + (player.posX - player.lastTickPosX) * (double) render.getPartialTicks()
                    - RenderManager.renderPosX);
            float y = (float) (player.lastTickPosY
                    + (player.posY - player.lastTickPosY) * (double) render.getPartialTicks()
                    - RenderManager.renderPosY);
            float z = (float) (player.lastTickPosZ
                    + (player.posZ - player.lastTickPosZ) * (double) render.getPartialTicks()
                    - RenderManager.renderPosZ);
            if(player != mc.thePlayer)
                this.renderNametag(player, x, y, z);
        });
    }



    private String getHealth(EntityPlayer player) {
        DecimalFormat numberFormat = new DecimalFormat("0.#");
        return numberFormat.format(player.getHealth() / 2.0f + player.getAbsorptionAmount() / 2.0f);
    }

    private void drawNames(EntityPlayer player) {
        float xP = 2.2f;
        float width = (float) this.getWidth(this.getPlayerName(player)) / 2.0f + xP;
        float w = width = (float) ((double) width + + 2.5);
        float nw = -width - xP;
        float offset = this.getWidth(this.getPlayerName(player));
        GlStateManager.disableDepth();
        Gui.drawRect((double) -FontLoaders.Sfui19.getStringWidth(getPlayerName(player)) / 2 - 1, -1, FontLoaders.Sfui19.getStringWidth(getPlayerName(player)) / 2 + 2, 9, 0x50000000);
        FontLoaders.Sfui19.drawStringWithShadow(getPlayerName(player), -FontLoaders.Sfui19.getStringWidth(getPlayerName(player)) / 2, 0, new Color(0, 255, 100).getRGB());
        Gui.drawRect((double) -FontLoaders.Sfui19.getStringWidth(getPlayerName(player)) / 2 - 1, 9.5F, FontLoaders.Sfui19.getStringWidth(getPlayerName(player)) / 2 + 1, 9, new Color(0, 255, 0, 0).getRGB());
        if (player.getHealth() == 10.0f) {
            int n = 16776960;
        }
        GlStateManager.enableDepth();
    }

//    private void drawString(String text, float x, float y, int color) {
//        mc.fontRendererObj.drawCenteredString(text, x - 30 + mc.fontRendererObj.getStringWidth(text), y, color);
//    }

    private int getWidth(String text) {
        return mc.fontRendererObj.getStringWidth(text);
    }

    private void startDrawing(float x, float y, float z, EntityPlayer player) {
        float var10001 = mc.gameSettings.thirdPersonView ? -1.0f : 1.0f;
        double size = Config.zoomMode ? (double) (this.getSize(player) / 10.0f) * 1.6
                : (double) (this.getSize(player) / 10.0f) * 4.8;
        GL11.glPushMatrix();
        RenderUtil.startDrawing();
        GL11.glTranslatef(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(mc.getRenderManager().playerViewX, var10001, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size,
                0.01666666753590107 * size);
    }

    private void stopDrawing() {
        RenderUtil.stopDrawing();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

    private void renderNametag(EntityPlayer player, float x, float y, float z) {
        y = (float) ((double) y + (1.55 + (player.isSneaking() ? 0.5 : 0.7)));
        this.startDrawing(x, y, z, player);
        this.drawNames(player);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        this.stopDrawing();
    }

    private String getPlayerName(EntityPlayer player) {
        String name = StringUtils.stripControlCodes(player.getName()) + "\247c " + getHealth(player);
        if (Teams.isOnSameTeam(player)) {
            name = "\247a[Teams] " + StringUtils.stripControlCodes(player.getName()) + "\247c " + getHealth(player);
        }
        if (AntiBot.isBot(player)) {
            name = "\247c[Bot] " + StringUtils.stripControlCodes(player.getName()) + "\247c " + getHealth(player);
        }
        return name;
    }

    private float getSize(EntityPlayer player) {
        return Math.max(mc.thePlayer.getDistanceToEntity(player) / 4.0f, 2.0f);
    }


}
class RenderUtil {
    public static void startDrawing() {
        glEnable(3042);
        glEnable(3042);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        glDisable(3553);
        glDisable(2929);
        Minecraft.getMinecraft().entityRenderer
                .setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
    }

    public static void stopDrawing() {
        glDisable(3042);
        glEnable(3553);
        glDisable(2848);
        glDisable(3042);
        glEnable(2929);
    }

    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        Gui.drawRect(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        glEnable(3042);
        glDisable(3553);
        GL11.glBlendFunc(770, 771);
        glEnable(2848);
        glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        glEnable(3553);
        glDisable(3042);
        glDisable(2848);
    }


}
