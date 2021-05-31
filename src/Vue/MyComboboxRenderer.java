package Vue;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * MyComboboxRenderer permet de modifier le style d'un objet de type {@link JComboBox}.
 */
public class MyComboboxRenderer extends DefaultListCellRenderer{
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setFont(new Font("Tempus sans ITC", Font.BOLD, 16)); // changement de la police d'ecriture des elements de la liste
        return this;
    }
}
