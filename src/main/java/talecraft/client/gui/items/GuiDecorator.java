package talecraft.client.gui.items;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.TaleCraft;
import talecraft.client.gui.qad.*;
import talecraft.client.gui.qad.QADDropdownBox.ListModel;
import talecraft.client.gui.qad.QADDropdownBox.ListModelItem;
import talecraft.client.gui.qad.QADNumberTextField.NumberType;
import talecraft.client.gui.vcui.VCUIRenderer;
import talecraft.network.packets.DecoratorPacket;

import java.util.ArrayList;
import java.util.List;

public class GuiDecorator extends QADGuiScreen {

    private QADDropdownBox DECORATION_SELECTOR;
    private QADNumberTextField X_OFFSET;
    private QADNumberTextField Y_OFFSET;
    private QADNumberTextField Z_OFFSET;
    private QADNumberTextField AMOUNT;
    private QADNumberTextField RADIUS;
    private String selected_decoration;
    private final List<String> decorations;
    private final NBTTagCompound tag;

    public GuiDecorator(List<String> sd, NBTTagCompound tag) {
        selected_decoration = sd.get(0);
        decorations = sd;
        this.tag = tag;
    }

    @Override
    public void buildGui() {
        addComponent(new QADLabel("Decorator", 2, 2));
        addComponent(new QADLabel("Decoration:", 20, 30));
        DECORATION_SELECTOR = new QADDropdownBox(new DecorationListModel(), new DecorationModelItem(tag.getString("decor")));
        DECORATION_SELECTOR.setBounds(85, 25, 100, 20);
        DECORATION_SELECTOR.setColor(-6250336);
        addComponent(new QADLabel("Offset:", 42, 60));
        X_OFFSET = new QADNumberTextField(85, 53, 40, 20, 0, NumberType.INTEGER);
        X_OFFSET.setRange(0, 200);
        X_OFFSET.setText("" + tag.getInteger("xoff"));
        addComponent(X_OFFSET);
        Y_OFFSET = new QADNumberTextField(85 + 40 + 10, 53, 40, 20, 0, NumberType.INTEGER);
        Y_OFFSET.setRange(0, 200);
        Y_OFFSET.setText("" + tag.getInteger("yoff"));
        addComponent(Y_OFFSET);
        Z_OFFSET = new QADNumberTextField(85 + 80 + 20, 53, 40, 20, 0, NumberType.INTEGER);
        Z_OFFSET.setRange(0, 200);
        Z_OFFSET.setText("" + tag.getInteger("zoff"));
        addComponent(Z_OFFSET);
        addComponent(new QADLabel("Amount:", 42, 90));
        AMOUNT = new QADNumberTextField(85, 53 + 28, 40, 20, 1, NumberType.INTEGER);
        AMOUNT.setRange(1, 50);
        if (tag.hasKey("amount")) AMOUNT.setText(tag.getString("amount"));
        addComponent(AMOUNT);
        addComponent(new QADLabel("Radius:", 42, 90 + 30));
        RADIUS = new QADNumberTextField(85, 53 + 28 + 30, 40, 20, 1, NumberType.INTEGER);
        RADIUS.setRange(1, 100);
        if (tag.hasKey("radius")) RADIUS.setText(tag.getString("radius"));
        addComponent(RADIUS);
        addComponent(DECORATION_SELECTOR);
        QADButton button = new QADButton("Apply");
        button.setBounds(2, 135, 75, 20);
        button.setAction(() -> TaleCraft.network.sendToServer(
                new DecoratorPacket(mc.player.getUniqueID(),
                        X_OFFSET.getValue().intValue(), Y_OFFSET.getValue().intValue(), Z_OFFSET.getValue().intValue(),
                        selected_decoration, AMOUNT.getValue().intValue(), RADIUS.getValue().intValue())));
        addComponent(button);
    }

    private class DecorationListModel implements ListModel {

        private final List<ListModelItem> items = new ArrayList<>();
        private final List<ListModelItem> filtered = new ArrayList<>();

        public DecorationListModel() {
            for (String decor : decorations) {
                items.add(new DecorationModelItem(decor));
            }
            filtered.addAll(filtered);
        }

        @Override
        public void onSelection(ListModelItem selected) {
            selected_decoration = ((DecorationModelItem) selected).decor;
        }

        @Override
        public boolean hasItems() {
            return true;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public List<ListModelItem> getItems() {
            return items;
        }

        @Override
        public void applyFilter(String filter) {
            filtered.clear();
            for (ListModelItem item : items) {
                DecorationModelItem decor = (DecorationModelItem) item;
                if (decor.decor.toLowerCase().contains(filter.toLowerCase())) {
                    filtered.add(item);
                }
            }
        }

        @Override
        public List<ListModelItem> getFilteredItems() {
            return filtered;
        }

        @Override
        public boolean hasIcons() {
            return false;
        }

        @Override
        public void drawIcon(VCUIRenderer renderer, float partialTicks, boolean light, ListModelItem item) {
        }

    }

    private class DecorationModelItem implements ListModelItem {

        private final String decor;

        public DecorationModelItem(String decor) {
            this.decor = decor;
        }

        @Override
        public String getText() {
            return decor;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof DecorationModelItem)) return false;
            DecorationModelItem other = (DecorationModelItem) o;
            return other.decor.equals(decor);
        }

        @Override
        public int hashCode() {
            return decor.hashCode();
        }
    }

}
