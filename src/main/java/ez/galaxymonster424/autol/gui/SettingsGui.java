package ez.galaxymonster424.autol.gui;

import ez.galaxymonster424.autol.AutoL;

import ez.galaxymonster424.autol.gui.modern.ModernButton;
import ez.galaxymonster424.autol.gui.modern.ModernGui;
import ez.galaxymonster424.autol.gui.modern.ModernTextBox;
import ez.galaxymonster424.autol.utils.ChatColor;

import org.lwjgl.input.Keyboard;

import java.awt.*;

public class SettingsGui extends ModernGui {

    private static final AutoL modInstance = AutoL.getInstance();

    private boolean changed = false;

    private ModernButton set;
    private ModernButton reset;

    private ModernTextBox text;
    private String input = "";

    public SettingsGui() {
        this("");
    }

    public SettingsGui(String input) {
        this.input = input;
        this.changed = false;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.textList.add(this.text = new ModernTextBox(this.width / 2 - 100, this.height / 2 - 27, 200, 20, true).returnWithSetText(this.input).returnWithOnlyAllowNumbersSet(true));

        this.buttonList.add(new ModernButton(1, this.width / 2 - 100, this.height / 2 + 2, 200, 20, "AutoL: " + getStatus(modInstance.isOn())));
        this.buttonList.add(this.set = new ModernButton(2, this.width / 2 - 100, this.height / 2 + 26, 200, 20, "Set Delay").setEnabled(modInstance.isOn()));
        this.buttonList.add(this.reset = new ModernButton(3, this.width / 2 - 100, this.height / 2 + 50, 200, 20, "Reset Delay").setEnabled(modInstance.isOn()));
    }

    @Override
    public void drawScreen(int x, int y, float ticks) {
        drawDefaultBackground();

        drawTitle("AutoL v" + AutoL.VERSION);
        drawCenteredString(this.mc.fontRendererObj, String.format("Delay is currently %s ticks", ChatColor.GOLD.toString() + modInstance.getTickDelay() + ChatColor.WHITE), this.width / 2, this.height / 2 - 58, Color.WHITE.getRGB());
        drawCenteredString(this.mc.fontRendererObj, String.format("Which is about %s second%s" , (ChatColor.GOLD.toString() + (double) modInstance.getTickDelay() / 20 + ChatColor.WHITE), modInstance.getTickDelay() == 20D ? "" : "s"), this.width / 2, this.height /2 - 46, Color.WHITE.getRGB());

        super.drawScreen(x, y, ticks);
    }

    @Override
    public void buttonPressed(ModernButton button) {
        switch (button.getId()) {
            case 1:
                modInstance.setOn(!modInstance.isOn());
                this.changed = true;
                button.setText("AutoL: " + getStatus(modInstance.isOn()));

                this.set.setEnabled(modInstance.isOn());
                this.reset.setEnabled(modInstance.isOn());

                if (!modInstance.isOn()) {
                    this.text.setEnabled(false);
                    this.text.setFocused(false);
                } else {
                    this.text.setEnabled(true);
                }

                break;
            case 2:
                if (!this.text.getText().isEmpty()) {
                    try {
                        int ticks = Integer.valueOf(this.text.getText());
                        if (ticks >= 0 && ticks <= 100) {
                            modInstance.setTickDelay(ticks);
                            this.changed = true;
                            sendChatMessage(String.format("Delay has been set to %s ticks!", ChatColor.RED + this.text.getText() + ChatColor.GRAY));
                        } else {
                            sendChatMessage("Tick delay must be over 0 and under 100.");
                        }
                    } catch (Exception ex) {
                        sendChatMessage("Please only use numbers!");
                    }
                } else {
                    sendChatMessage("No text provided!");
                }
                mc.displayGuiScreen(null);
                break;
            case 3:
                modInstance.setTickDelay(20);
                this.changed = true;
                sendChatMessage("Tick delay has been reset!");
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.changed) {
            modInstance.getFileUtils().saveConfig();
        }
    }

    private void drawTitle(String text) {
        drawCenteredString(this.mc.fontRendererObj, text, this.width / 2, this.height / 2 - 80, Color.WHITE.getRGB());
        drawHorizontalLine(this.width / 2 - this.mc.fontRendererObj.getStringWidth(text) / 2 - 5, this.width / 2 + this.mc.fontRendererObj.getStringWidth(text) / 2 + 5, this.height / 2 - 70, Color.WHITE.getRGB());
    }
}
