package ez.galaxymonster424.autol.gui.modern;

import com.google.common.collect.Lists;

import ez.galaxymonster424.autol.utils.ChatColor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.List;

public abstract class ModernGui extends GuiScreen {

    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final FontRenderer fontRendererObj = this.mc.fontRendererObj;

    protected List<ModernTextBox> textList = Lists.newArrayList();

    private GuiButton selectedButton;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }

        for (GuiLabel label : this.labelList) {
            label.drawLabel(this.mc, mouseX, mouseY);
        }

        for (ModernTextBox text : this.textList) {
            text.drawTextBox();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);

            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        } else {
            for (ModernTextBox text : this.textList) {
                text.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) break;
                    guibutton = event.button;
                    this.selectedButton = guibutton;
                    guibutton.playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                    if (this.equals(this.mc.currentScreen))
                        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                }
            }
        }

        if (mouseButton == 1) {
            for (GuiButton button : this.buttonList) {
                if (button instanceof ModernButton) {
                    ModernButton modernButton = (ModernButton) button;

                    if (modernButton.mousePressed(this.mc, mouseX, mouseY)) {
                        this.rightClicked(modernButton);
                    }
                }
            }
        }

        for (ModernTextBox text : this.textList) {
            text.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    @Override
    public final void drawDefaultBackground() {
        Gui.drawRect(0, 0, this.width, this.height, new Color(2, 2, 2, 120).getRGB());
    }

    @Override
    public final void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, false);
    }

    public final void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color, boolean shadow) {
        fontRendererIn.drawString(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color, shadow);
    }

    public final void drawCenteredString(String text, int x, int y, int color) {
        drawCenteredString(this.fontRendererObj, text, x, y, color, false);
    }

    public final void drawCenteredString(String text, int x, int y, int color, boolean shadow) {
        drawCenteredString(this.fontRendererObj, text, x, y, color, shadow);
    }

    @Override
    public final void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float) x, (float) y, color, false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof ModernButton) {
            buttonPressed((ModernButton) button);
        }
    }

    @Override
    public final void sendChatMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(ChatColor.GOLD + "AutoGG" + ChatColor.AQUA + " > " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes(msg)));
    }

    public void buttonPressed(ModernButton button) {
    }

    public void rightClicked(ModernButton button) {

    }

    public void writeInformation(int startingX, int startingY, int separation, String... lines) {
        for (String s : lines) {
            drawCenteredString(this.fontRendererObj, ChatColor.translateAlternateColorCodes(s), startingX, startingY, Color.WHITE.getRGB());
            startingY += separation;
        }
    }

    public final void display() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public final void onTick(TickEvent.ClientTickEvent event) {
        MinecraftForge.EVENT_BUS.unregister(this);
        Minecraft.getMinecraft().displayGuiScreen(this);
    }

    public static String getStatus(boolean in) {
        return in ? ChatColor.GREEN + "Enabled" : ChatColor.GRAY + "Disabled";
    }
}
