package talecraft.client.gui.qad.model;

import com.google.common.base.Predicate;
import talecraft.client.gui.qad.QADTextField.TextFieldModel;

public final class DefaultTextFieldIntegerNumberModel implements TextFieldModel {
    private java.util.function.Predicate<Integer> validatorPredicate = (Predicate<Integer>) input -> true;

    private String text;
    private boolean valid;
    private int value;

    public DefaultTextFieldIntegerNumberModel(int value) {
        this.value = value;
        this.text = Integer.toString(value);
        this.valid = validatorPredicate.test(value);
    }

    public DefaultTextFieldIntegerNumberModel() {
        this.value = 0;
        this.text = "0";
        this.valid = validatorPredicate.test(value);
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;

        int oldValue = value;
        try {
            value = Integer.parseInt(text);
            valid = true;

            if (!validatorPredicate.test(value)) {
                throw new NumberFormatException("Value did not pass validator predicate.");
            }
        } catch (NumberFormatException e) {
            value = oldValue;
            valid = false; // :(
        }
    }

    @Override
    public int getTextLength() {
        return this.text.length();
    }

    @Override
    public char getCharAt(int i) {
        return this.text.charAt(i);
    }

    @Override
    public int getTextColor() {
        return valid ? 0xFFFFFFFF : 0xFFFF7070;
    }

    @Override
    public void setTextColor(int color) {
        // nope
    }

    public int getValue() {
        return value;
    }

    public void setValidatorPredicate(java.util.function.Predicate<Integer> validator) {
        this.validatorPredicate = validator::test;
    }

}