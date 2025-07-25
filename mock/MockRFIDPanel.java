package mock;

import java.awt.*;
import javax.swing.*;

public class MockRFIDPanel extends JPanel {
    private JTextField cardField;
    private JButton scanButton;

    public interface ScanListener {
        void onScan(String cardNumber);
    }

    public MockRFIDPanel(ScanListener listener) {
        setLayout(new FlowLayout());
        cardField = new JTextField(15);
        scanButton = new JButton("Scan");

        add(new JLabel("Card Number:"));
        add(cardField);
        add(scanButton);

        scanButton.addActionListener(e -> {
            String card = cardField.getText().trim();
            if (!card.isEmpty()) {
                listener.onScan(card);
            }
        });
    }

    public void clear() {
        cardField.setText("");
    }
}
