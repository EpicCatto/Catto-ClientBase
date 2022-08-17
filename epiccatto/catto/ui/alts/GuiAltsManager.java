package epiccatto.catto.ui.alts;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiAltsManager extends GuiScreen {

    private final GuiScreen parent;

    public GuiAltsManager(GuiScreen parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Alts Manager (Soon lol)", this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 50, "Microsoft"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(parent);
        }else if (button.id == 1) {

        }
    }
}
