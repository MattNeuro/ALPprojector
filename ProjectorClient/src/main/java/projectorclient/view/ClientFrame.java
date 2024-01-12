/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package projectorclient.view;

import java.awt.Rectangle;
import javax.swing.JOptionPane;
import projectorclient.controller.Controller;
import projectorclient.model.Feed;

/**
 *
 * @author Matthijs
 */
public class ClientFrame extends javax.swing.JFrame {

    /**
     * Creates new form ClientFrame
     */
    public ClientFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayLabel = new javax.swing.JLabel();
        captureX = new javax.swing.JTextField();
        captureY = new javax.swing.JTextField();
        uploadMask = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        captureWidth = new javax.swing.JTextField();
        captureHeight = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textFocusField = new javax.swing.JTextField();
        maskSizeSlider = new javax.swing.JSlider();
        jLabel6 = new javax.swing.JLabel();
        toggleAll = new javax.swing.JToggleButton();
        clearAllButton = new javax.swing.JButton();
        gridLinesToggleButton = new javax.swing.JToggleButton();
        deinterlaceToggle = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 820));
        setResizable(false);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        displayLabel.setText("jLabel1");
        displayLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        displayLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                displayLabelMouseMoved(evt);
            }
        });
        displayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayLabelMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                displayLabelMouseReleased(evt);
            }
        });

        captureX.setText("100");
        captureX.setToolTipText("Horizontal offset to camera window");
        captureX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captureXActionPerformed(evt);
            }
        });
        captureX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                captureXKeyReleased(evt);
            }
        });

        captureY.setText("100");
        captureY.setToolTipText("Vertical offset to camera window");
        captureY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                captureYKeyReleased(evt);
            }
        });

        uploadMask.setText("UPLOAD MASK");
        uploadMask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadMaskActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Offset X");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Capture Area");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Offset Y");

        captureWidth.setText("1024");
        captureWidth.setToolTipText("Horizontal offset to camera window");
        captureWidth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                captureWidthKeyReleased(evt);
            }
        });

        captureHeight.setText("768");
        captureHeight.setToolTipText("Vertical offset to camera window");
        captureHeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                captureHeightKeyReleased(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Height");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Width");

        textFocusField.setFocusCycleRoot(true);

        maskSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maskSizeSliderStateChanged(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mask Size (G/H)");

        toggleAll.setText("Toggle All");
        toggleAll.setPreferredSize(new java.awt.Dimension(83, 15));
        toggleAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAllActionPerformed(evt);
            }
        });

        clearAllButton.setText("Clear All");
        clearAllButton.setPreferredSize(new java.awt.Dimension(74, 15));
        clearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllButtonActionPerformed(evt);
            }
        });

        gridLinesToggleButton.setText("Grid Lines");
        gridLinesToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridLinesToggleButtonActionPerformed(evt);
            }
        });

        deinterlaceToggle.setText("Deinterlace");
        deinterlaceToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deinterlaceToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(displayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(captureWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(captureHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFocusField)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(maskSizeSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(uploadMask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clearAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(toggleAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(captureX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(captureY, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(deinterlaceToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gridLinesToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textFocusField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(captureX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(captureY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(captureWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(captureHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(66, 66, 66)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maskSizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(toggleAll, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(deinterlaceToggle)
                        .addGap(18, 18, 18)
                        .addComponent(gridLinesToggleButton)
                        .addGap(308, 308, 308)
                        .addComponent(uploadMask, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(displayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void captureXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captureXActionPerformed
        
    }//GEN-LAST:event_captureXActionPerformed

    private void captureYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_captureYKeyReleased
        captureAreaChanged();
    }//GEN-LAST:event_captureYKeyReleased

    private void captureXKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_captureXKeyReleased
        captureAreaChanged();
    }//GEN-LAST:event_captureXKeyReleased

    private void displayLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayLabelMouseClicked
        int x = evt.getX();
        int y = evt.getY();
        Feed.getInstance().addMaskSpot(x,y);
        System.out.println("Adding mask spot.");
    }//GEN-LAST:event_displayLabelMouseClicked

    private void displayLabelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayLabelMouseReleased
        // TODO add your handling code here:
        displayLabelMouseClicked(evt);
    }//GEN-LAST:event_displayLabelMouseReleased

    private void uploadMaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadMaskActionPerformed
        // TODO add your handling code here:
        try {
            Controller.getInstance().network.uploadMask();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Mask upload failed: \r\n" + e.getMessage());
        }
        
    }//GEN-LAST:event_uploadMaskActionPerformed

    private void captureWidthKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_captureWidthKeyReleased
        captureAreaChanged();
    }//GEN-LAST:event_captureWidthKeyReleased

    private void captureHeightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_captureHeightKeyReleased
        captureAreaChanged();
    }//GEN-LAST:event_captureHeightKeyReleased

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // TODO add your handling code here:
        formWindowGainedFocus(null);
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        System.out.println("Form window Gained focus.");
        textFocusField.requestFocusInWindow();
        textFocusField.setVisible(false);
    }//GEN-LAST:event_formWindowGainedFocus

    private void clearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllButtonActionPerformed
        System.out.println("GUI requests clearing all masks");
        Feed.getInstance().clearMask();
        
        if (toggleAll.isSelected())         // If we toggled all and then cleared the field, the toggle-all button should reflect this state change
            toggleAll.setSelected(false);
    }//GEN-LAST:event_clearAllButtonActionPerformed

    private void toggleAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAllActionPerformed
        if (toggleAll.isSelected())
            Feed.getInstance().fillMask();
        else
            Feed.getInstance().clearMask();
        
        // After the toggle all button is pressed, automatically upload the mask.
        uploadMaskActionPerformed(evt);
    }//GEN-LAST:event_toggleAllActionPerformed

    private void maskSizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maskSizeSliderStateChanged
        Feed.getInstance().setMaskSpotSize(maskSizeSlider.getValue());
    }//GEN-LAST:event_maskSizeSliderStateChanged

    private void displayLabelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayLabelMouseMoved
        // Mouse moved, update spot indicator
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_displayLabelMouseMoved

    private void gridLinesToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridLinesToggleButtonActionPerformed
        Feed.getInstance().setGridLinesActive(gridLinesToggleButton.isSelected());
        uploadMaskActionPerformed(evt); // After the toggle all button is pressed, automatically upload the mask.
    }//GEN-LAST:event_gridLinesToggleButtonActionPerformed

    private void deinterlaceToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deinterlaceToggleActionPerformed
        // Toggle deinterlacing.
        Feed.getInstance().setDeinterlacing(deinterlaceToggle.isSelected());
        uploadMaskActionPerformed(evt);        // After the toggle all button is pressed, automatically upload the mask.
    }//GEN-LAST:event_deinterlaceToggleActionPerformed

    private void captureAreaChanged () {
        System.out.println("GUI requests a change to the capture area.");
        Feed.getInstance().updateCaptureArea();
    }


    // When loading in, the capture preferences are restored from previous run.
    public void updateAreaPreferences (Rectangle area) {
        captureX.setText(Integer.toString(area.x));
        captureY.setText(Integer.toString(area.y));
        captureWidth.setText(Integer.toString(area.width));
        captureHeight.setText(Integer.toString(area.height));
    }
       
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }
    
    // Store mouse location
    public int mouseX, mouseY;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField captureHeight;
    public javax.swing.JTextField captureWidth;
    public javax.swing.JTextField captureX;
    public javax.swing.JTextField captureY;
    private javax.swing.JButton clearAllButton;
    private javax.swing.JToggleButton deinterlaceToggle;
    public javax.swing.JLabel displayLabel;
    private javax.swing.JToggleButton gridLinesToggleButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    public static javax.swing.JSlider maskSizeSlider;
    private javax.swing.JTextField textFocusField;
    private javax.swing.JToggleButton toggleAll;
    private javax.swing.JButton uploadMask;
    // End of variables declaration//GEN-END:variables
}
