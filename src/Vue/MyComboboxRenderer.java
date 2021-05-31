package Vue;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * La classe MyComboboxRenderer herite de DefaultListCellRenderer
 */
public class MyComboboxRenderer extends DefaultListCellRenderer{
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setFont(new Font("Tempus sans ITC", Font.BOLD, 16));
        return this;
    }
}
