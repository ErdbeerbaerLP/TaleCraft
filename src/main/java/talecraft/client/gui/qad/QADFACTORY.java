package talecraft.client.gui.qad;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import talecraft.client.gui.qad.QADTextField.TextChangeListener;

public class QADFACTORY {

    public static QADLabel createLabel(Object object, int x, int y) {
        return new QADLabel("" + object, x, y);
    }

    public static QADLabel createLabel(String text, int x, int y) {
        return new QADLabel(text, x, y);
    }

    public static QADButton createButton(ResourceLocation icon, int x, int y, int width, Runnable action) {
        QADButton button = new QADButton(x, y, width, "");
        button.setAction(action);
        button.setIcon(icon);
        return button;
    }

    public static QADButton createButton(String text, int x, int y, int width, Runnable action) {
        QADButton button = new QADButton(x, y, width, text);
        button.setAction(action);
        return button;
    }

    public static QADButton createButton(String text, int x, int y, int width) {
        return new QADButton(x, y, width, text);
    }

    public static QADTextField createTextField(String text, int x, int y, int width) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(text);
        return textField;
    }

    public static QADTextField createTextField(int number, int x, int y, int width) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(Integer.toString(number));
        return textField;
    }

    public static QADTextField createTextField(float number, int x, int y, int width) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(Float.toString(number));
        return textField;
    }

    public static QADTextField createNumberTextField(int number, int x, int y, int width) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(Integer.toString(number));
        textField.textChangedListener = (qadTextField, text) -> {
            if (text.isEmpty()) {
                qadTextField.setText("0");
                return;
            }

            try {
                Integer.parseInt(text);
                qadTextField.setTextColor(0xFFFFFFFF);
            } catch (NumberFormatException e) {
                qadTextField.setTextColor(0xFFFF5555);
            }
        };
        return textField;
    }

    public static QADTextField createNumberTextField(int number, int x, int y, int width, final int MAX) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(Integer.toString(number));
        textField.textChangedListener = new TextChangeListener() {
            final int max = MAX;

            @Override
            public void call(QADTextField qadTextField, String text) {
                if (text.isEmpty()) {
                    qadTextField.setText("0");
                    return;
                }

                try {
                    int i = Integer.parseInt(text);

                    if (i > max)
                        qadTextField.setTextColor(0xFFFF5555);
                    else
                        qadTextField.setTextColor(0xFFFFFFFF);
                } catch (NumberFormatException e) {
                    qadTextField.setTextColor(0xFFFF5555);
                }
            }
        };
        return textField;
    }

    public static QADTextField createNumberTextField(int number, int x, int y, int width, final int MAX, final int MIN) {
        QADTextField textField = new QADTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, 16);
        textField.setText(Integer.toString(number));
        textField.textChangedListener = new TextChangeListener() {
            final int max = MAX;
            final int min = MIN;

            @Override
            public void call(QADTextField qadTextField, String text) {
                if (text.isEmpty()) {
                    qadTextField.setText("0");
                    return;
                }

                try {
                    int i = Integer.parseInt(text);

                    if (i > max)
                        qadTextField.setTextColor(0xFFFF5555);
                    else if (i < min)
                        qadTextField.setTextColor(0xFFFF5555);
                    else
                        qadTextField.setTextColor(0xFFFFFFFF);
                } catch (NumberFormatException e) {
                    qadTextField.setTextColor(0xFFFF5555);
                }
            }
        };
        return textField;
    }

}
