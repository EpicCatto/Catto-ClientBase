package epiccatto.catto.ui.alts;

import epiccatto.catto.Client;
import epiccatto.catto.utils.TimerUtil;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import net.minecraft.client.gui.*;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;

import java.awt.*;
import java.io.IOException;

public class GuiAltsManager extends GuiScreen {

    private final GuiScreen parent;

    private GuiTextField username;
    private GuiTextField combined;
    private GuiTextField password;

    private String status = "";
    public TimerUtil timer = new TimerUtil();

    public GuiAltsManager(GuiScreen parent) {
        super();
        this.parent = parent;
    }

    @Override
    public void initGui() {
        status = EnumChatFormatting.YELLOW + "Waiting...";

        ScaledResolution sr = new ScaledResolution(mc);
        this.username = new GuiTextField(1, mc.fontRendererObj, 10, 30, 100, 20);
        this.password = new GuiTextField(1, mc.fontRendererObj, username.xPosition,  username.yPosition + username.getHeight() + 10, username.getWidth(), username.getHeight());

        username.setEnableBackgroundDrawing(false);
        password.setEnableBackgroundDrawing(false);

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        //current user
        mc.fontRendererObj.drawString(EnumChatFormatting.YELLOW + "Current user: " + EnumChatFormatting.GREEN + mc.getSession().getUsername(), 10, 10, 0xFFFFFF);

        this.drawCenteredString(this.fontRendererObj, "Alts Manager (Soon lol)", this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, status, this.width / 2, this.height / 2 + 10, 0xFFFFFF);
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + 50, "Microsoft"));

        this.username.drawTextBox();
        this.password.drawTextBox();

        if (!username.isFocused() && username.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Username", username.xPosition + 5, username.yPosition + 5, Color.GRAY.getRGB());
        }

        if (!password.isFocused() && password.getText().isEmpty()) {
            mc.fontRendererObj.drawStringWithShadow("Password", password.xPosition + 5, password.yPosition + 5, Color.GRAY.getRGB());
        }

        //Draw rect under textbox
        Gui.drawRect(username.xPosition - 1, username.yPosition + username.getHeight()  - 1, username.xPosition + username.getWidth() + 1, username.yPosition + username.getHeight() + 1, Color.GRAY.getRGB());
        Gui.drawRect(password.xPosition - 1, password.yPosition + username.getHeight()  - 1, password.xPosition + password.getWidth() + 1, password.yPosition + password.getHeight() + 1, Color.GRAY.getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(parent);
        }
        if (button.id == 1 && timer.hasReached(1000)) {
            timer.reset();
            System.out.println("Microsoft");
            status = EnumChatFormatting.YELLOW + "Logging in...";
            new Thread(() -> {
                MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                try {
                    MicrosoftAuthResult result = authenticator.loginWithCredentials(username.getText(), password.getText());
                    MinecraftProfile profile = result.getProfile();
                    mc.session = new Session(profile.getName(), profile.getId(), result.getAccessToken(), "microsoft");
                    status = EnumChatFormatting.GREEN + "Logged in! (" + profile.getName() + ")";
                } catch (MicrosoftAuthenticationException e) {
                    Client.clientData.logError("Error while logging into alts! (Microsoft)", e);
                    status = EnumChatFormatting.RED + "Error: " + e.getMessage();
                }
            }).start();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.username.textboxKeyTyped(typedChar, keyCode);
        //this.combined.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);

        if (typedChar == '\t' && (this.username.isFocused() || this.combined.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
            this.combined.setFocused(!this.combined.isFocused());
        }
        if (typedChar == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        //this.combined.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
