package ez.galaxymonster424.autol.gui.modern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ModernButton extends GuiButton {

    private int id;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private boolean isNew;
    private boolean enabled;
    private boolean visible;
    private boolean hovered;
    private String buttonIdName;
    private String displayString;

    private Color enabledOverrideColor = null;
    private Color disabledOverrideColor = null;

    public ModernButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, "", x, y, 200, 20, buttonText);
    }

    public ModernButton(int buttonId, String idName, int x, int y, String buttonText) {
        this(buttonId, idName, x, y, 200, 20, buttonText);
    }

    public ModernButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this(buttonId, "", x, y, widthIn, heightIn, buttonText);
    }

    public ModernButton(int buttonId, String idName, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.buttonIdName = idName;
        this.displayString = buttonText;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }
        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);

            if (this.enabled) {
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + height, getEnabledColor().getRGB());
            } else {
                drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + height, getDisabledColor().getRGB());
            }

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (!this.enabled) {
                j = Color.WHITE.getRGB();
            } else if (this.hovered) {
                j = 16777120;
            }
            fontrenderer.drawString(this.displayString, (this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2), this.yPosition + (this.height - 8) / 2, j, false);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    @Override
    public boolean isMouseOver() {
        return this.hovered;
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 2.0F));
    }

    @Override
    public int getButtonWidth()
    {
        return this.width;
    }

    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    public Color getEnabledColor() {
        return this.enabledOverrideColor == null ? new Color(255, 255, 255, 75) : this.enabledOverrideColor;
    }

    public ModernButton setEnabledColor(Color colorIn) {
        this.enabledOverrideColor = colorIn;

        return this;
    }

    public Color getDisabledColor() {
        return this.disabledOverrideColor == null ? new Color(100, 100, 100, 75) : this.disabledOverrideColor;
    }

    public ModernButton setDisabledColor(Color colorIn) {
        this.disabledOverrideColor = colorIn;

        return this;
    }

    public String getButtonId() {
        return this.buttonIdName;
    }

    public String getText() {
        return this.displayString;
    }

    public void setText(String text) {
        this.displayString = text != null ? text : "";
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public ModernButton setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;

        return this;
    }

    public int getId() {
        return this.id;
    }
}