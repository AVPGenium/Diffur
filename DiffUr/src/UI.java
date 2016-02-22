/*UI.java*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import javafx.util.Pair;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created by Андрей on 21.03.2015.
 */
public class UI implements ExceptionListener {
    About about;
    SolutionFrame solutionFrame;
    JFrame mainFrame;
    JPanel eulerPanel;
    JPanel rungeKuttPanel;
    JPanel buttonsArea;
    JPanel eulerGraphicArea;
    JPanel rungeKuttGraphicArea;
    JPanel diffGraphicArea;
    JButton newSolution;
    JButton saveToExcel;
    JButton changeK;
    JButton clear;
    JButton aboutButton;
    JButton showSolution;
    JLabel jLabel1, jLabel2, jLabel3;
    JTextField jTextField1, jTextField2;
    JFreeChart chartEuler;
    JFreeChart chartRungeKutt;
    JFreeChart chartDiff;
    JPanel eulerTables;
    JPanel rungeKuttTables;
    JTable euler1;
    JTable euler2;
    JTable euler3;
    JTable rungeKutt1;
    JTable rungeKutt2;
    JTable rungeKutt3;

    public JComboBox getSaveOptions() {
        return saveOptions;
    }

    public About getAbout() {
        return about;
    }

    public JFreeChart getChartRungeKutt() {
        return chartRungeKutt;
    }

    public JFreeChart getChartDiff() {
        return chartDiff;
    }

    public JFreeChart getChartEuler() {
        return chartEuler;
    }

    JComboBox saveOptions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;

    String[] columnNames = {"Время", "Разница температур"};
    Object[][] data1, data2, data3, data4, data5, data6;

    public void setPath(String path) {
        this.path = path;
    }

    String path="";

    public String getPath() {
        return path;
    }

    @Override
    public void handleExceptionAndShowDialog(Throwable throwable) {
        JOptionPane.showMessageDialog(mainFrame, throwable.getMessage(), "It's an error, breathe deeply", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void handleExceptionAndDisplayItInCodeArea(Exception exception) {

    }

    public void reDraw() {
        mainFrame.remove(eulerPanel);
        mainFrame.remove(rungeKuttPanel);
        mainFrame.remove(diffGraphicArea);
        mainFrame.pack();
        createArraysWithData();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        euler1 = new JTable();
        euler2 = new JTable();
        euler3 = new JTable();
        rungeKutt1 = new JTable();
        rungeKutt2 = new JTable();
        rungeKutt3 = new JTable();
        euler1.setModel(new javax.swing.table.DefaultTableModel(
                data1, new String [] {
                "Время", "Разница температур"
        }
        ));
        euler1.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(euler1);
        euler1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler1.getColumnModel().getColumnCount() > 0) {
            euler1.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        euler2.setModel(new javax.swing.table.DefaultTableModel(
                data2, new String [] {
                "Время", "Разница температур"
        }
        ));
        euler2.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(euler2);
        euler2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler2.getColumnModel().getColumnCount() > 0) {
            euler2.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        euler3.setModel(new javax.swing.table.DefaultTableModel(
                data3, new String [] {
                "Время", "Разница температур"
        }
        ));
        euler3.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(euler3);
        euler3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler3.getColumnModel().getColumnCount() > 0) {
            euler3.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        eulerTables = new JPanel(new BorderLayout());
        eulerTables.setMaximumSize(new Dimension(400, 300));
        eulerTables.add(jScrollPane1, BorderLayout.WEST);
        eulerTables.add(jScrollPane2, BorderLayout.CENTER);
        eulerTables.add(jScrollPane3, BorderLayout.EAST);
        chartEuler = createEulerChart();
        eulerGraphicArea = new ChartPanel(chartEuler);
        eulerGraphicArea.setPreferredSize(new java.awt.Dimension(400, 200));
        eulerGraphicArea.setMinimumSize(new java.awt.Dimension(400, 150));
        eulerGraphicArea.setLayout(new BoxLayout(eulerGraphicArea, BoxLayout.X_AXIS));
        eulerPanel = new JPanel(new BorderLayout());
        eulerPanel.add(eulerGraphicArea, BorderLayout.CENTER);
        eulerPanel.add(eulerTables, BorderLayout.SOUTH);
        eulerPanel.setPreferredSize(new Dimension(450, 700));
        //eulerPanel.setMinimumSize(new Dimension(400, 700));
        ///
        rungeKutt1.setModel(new javax.swing.table.DefaultTableModel(
                data4, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt1.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(rungeKutt1);
        rungeKutt1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt1.getColumnModel().getColumnCount() > 0) {
            rungeKutt1.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKutt2.setModel(new javax.swing.table.DefaultTableModel(
                data5, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt2.setColumnSelectionAllowed(true);
        jScrollPane5.setViewportView(rungeKutt2);
        rungeKutt2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt2.getColumnModel().getColumnCount() > 0) {
            rungeKutt2.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKutt3.setModel(new javax.swing.table.DefaultTableModel(
                data6, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt3.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(rungeKutt3);
        rungeKutt3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt3.getColumnModel().getColumnCount() > 0) {
            rungeKutt3.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKuttTables = new JPanel(new BorderLayout());
        rungeKuttTables.add(jScrollPane4, BorderLayout.WEST);
        rungeKuttTables.add(jScrollPane5, BorderLayout.CENTER);
        rungeKuttTables.add(jScrollPane6, BorderLayout.EAST);
        chartRungeKutt = createRungeKuttChart();
        rungeKuttGraphicArea = new ChartPanel(chartRungeKutt);
        rungeKuttGraphicArea.setPreferredSize(new java.awt.Dimension(400, 200));
        rungeKuttGraphicArea.setMinimumSize(new Dimension(400, 50));
        rungeKuttGraphicArea.setLayout(new BoxLayout(rungeKuttGraphicArea, BoxLayout.X_AXIS));
        rungeKuttPanel = new JPanel(new BorderLayout());
        rungeKuttPanel.add(rungeKuttGraphicArea, BorderLayout.CENTER);
        rungeKuttPanel.add(rungeKuttTables, BorderLayout.SOUTH);
        rungeKuttPanel.setPreferredSize(new Dimension(450, 800));
        chartDiff = createDiffChart();
        diffGraphicArea = new ChartPanel(chartDiff);
        diffGraphicArea.setPreferredSize(new java.awt.Dimension(450, 800));
        mainFrame.add(eulerPanel, BorderLayout.WEST);
        mainFrame.add(rungeKuttPanel, BorderLayout.CENTER);
        mainFrame.add(diffGraphicArea, BorderLayout.EAST);
        mainFrame.add(buttonsArea, BorderLayout.NORTH);
        mainFrame.pack();
    }

    private static class UIHolder {
        static final UI UI_INSTANCE = new UI();
    }

    private UI() {
    }

    public static UI getInstance(){
        return UIHolder.UI_INSTANCE;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public JFrame initUI(){
        createArraysWithData();
        mainFrame = new JFrame();
        mainFrame.setSize(1400, 800);
        mainFrame.setTitle("Решение задачи охлаждения металлов");
        mainFrame.setLayout(new BorderLayout());
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        euler1 = new JTable();
        euler2 = new JTable();
        euler3 = new JTable();
        rungeKutt1 = new JTable();
        rungeKutt2 = new JTable();
        rungeKutt3 = new JTable();
        euler1.setModel(new javax.swing.table.DefaultTableModel(
                data1, new String [] {
                "Время", "Разница температур"
                }
        ));
        euler1.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(euler1);
        euler1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler1.getColumnModel().getColumnCount() > 0) {
            euler1.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        euler2.setModel(new javax.swing.table.DefaultTableModel(
                data2, new String [] {
                "Время", "Разница температур"
        }
        ));
        euler2.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(euler2);
        euler2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler2.getColumnModel().getColumnCount() > 0) {
            euler2.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        euler3.setModel(new javax.swing.table.DefaultTableModel(
                data3, new String [] {
                "Время", "Разница температур"
        }
        ));
        euler3.setColumnSelectionAllowed(true);
        jScrollPane3.setViewportView(euler3);
        euler3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (euler3.getColumnModel().getColumnCount() > 0) {
            euler3.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        eulerTables = new JPanel(new BorderLayout());
        eulerTables.setMaximumSize(new Dimension(400, 300));
        eulerTables.add(jScrollPane1, BorderLayout.WEST);
        eulerTables.add(jScrollPane2, BorderLayout.CENTER);
        eulerTables.add(jScrollPane3, BorderLayout.EAST);
        chartEuler = createEulerChart();
        eulerGraphicArea = new ChartPanel(chartEuler);
        eulerGraphicArea.setPreferredSize(new java.awt.Dimension(400, 200));
        eulerGraphicArea.setMinimumSize(new java.awt.Dimension(400, 150));
        eulerGraphicArea.setLayout(new BoxLayout(eulerGraphicArea, BoxLayout.X_AXIS));
        eulerPanel = new JPanel(new BorderLayout());
        eulerPanel.add(eulerGraphicArea, BorderLayout.CENTER);
        eulerPanel.add(eulerTables, BorderLayout.SOUTH);
        eulerPanel.setPreferredSize(new Dimension(450, 700));

        rungeKutt1.setModel(new javax.swing.table.DefaultTableModel(
                data4, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt1.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(rungeKutt1);
        rungeKutt1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt1.getColumnModel().getColumnCount() > 0) {
            rungeKutt1.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKutt2.setModel(new javax.swing.table.DefaultTableModel(
                data5, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt2.setColumnSelectionAllowed(true);
        jScrollPane5.setViewportView(rungeKutt2);
        rungeKutt2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt2.getColumnModel().getColumnCount() > 0) {
            rungeKutt2.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKutt3.setModel(new javax.swing.table.DefaultTableModel(
                data6, new String [] {
                "Время", "Разница температур"
        }
        ));
        rungeKutt3.setColumnSelectionAllowed(true);
        jScrollPane6.setViewportView(rungeKutt3);
        rungeKutt3.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (rungeKutt3.getColumnModel().getColumnCount() > 0) {
            rungeKutt3.setPreferredScrollableViewportSize(new Dimension(150, 200));
        }
        rungeKuttTables = new JPanel(new BorderLayout());
        rungeKuttTables.add(jScrollPane4, BorderLayout.WEST);
        rungeKuttTables.add(jScrollPane5, BorderLayout.CENTER);
        rungeKuttTables.add(jScrollPane6, BorderLayout.EAST);
        chartRungeKutt = createRungeKuttChart();
        rungeKuttGraphicArea = new ChartPanel(chartRungeKutt);
        rungeKuttGraphicArea.setPreferredSize(new java.awt.Dimension(400, 200));
        rungeKuttGraphicArea.setMinimumSize(new Dimension(400, 50));
        rungeKuttGraphicArea.setLayout(new BoxLayout(rungeKuttGraphicArea, BoxLayout.X_AXIS));
        rungeKuttPanel = new JPanel(new BorderLayout());
        rungeKuttPanel.add(rungeKuttGraphicArea, BorderLayout.CENTER);
        rungeKuttPanel.add(rungeKuttTables, BorderLayout.SOUTH);
        rungeKuttPanel.setPreferredSize(new Dimension(450, 800));
        chartDiff = createDiffChart();
        diffGraphicArea = new ChartPanel(chartDiff);
        diffGraphicArea.setPreferredSize(new java.awt.Dimension(450, 800));
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        changeK = new JButton();
        clear = new JButton();
        newSolution = new JButton();
        saveToExcel = new JButton();
        saveOptions = new JComboBox();
        aboutButton = new JButton();
        showSolution = new JButton("Показать решение");
        changeK.setText("Изменить");
        clear.setText("Очистить");
        aboutButton.setText("О приложении");
        newSolution.setText("Новое решение");

        changeK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                double tempK = 0, tempT0 = 0;
                if(jTextField1.getText().equals("Введите значение k") || jTextField1.getText().equals("")){
                    tempK = UIEntry.getDiffCore().getK();
                }
                if(jTextField2.getText().equals("Введите начальные условия") || jTextField2.getText().equals("")){
                    tempT0 = UIEntry.getDiffCore().getY0();
                }
                try {
                    if(tempK == 0) {
                        if (Double.parseDouble(jTextField1.getText()) >= 0 || Double.parseDouble(jTextField1.getText()) < -10)
                            throw new Exception("Value is out of range. (-10 <= k < 0)");
                        else tempK = Double.parseDouble(jTextField1.getText());
                    }
                    if(tempT0 == 0) {
                        if (Double.parseDouble(jTextField2.getText()) < DiffCore.EPSILON || Double.parseDouble(jTextField2.getText()) > 1000000000)
                            throw new Exception("Value is out of range. (0 <= delta T0 <= 1000000000)");
                        else tempT0 = Double.parseDouble(jTextField2.getText());
                    }
                    UIEntry.reCalc(tempK, UIEntry.getDiffCore().x0, tempT0);
                } catch (Exception e) {
                    UIEntry.exceptionListener.handleExceptionAndShowDialog(e);
                    e.printStackTrace();
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIEntry.reCalc(0, 0, 0);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (about == null) {
                    about = new About("DiffUr About");
                    about.setVisible(true);
                } else {
                    about.setVisible(true);
                    about.repaint();
                }
            }
        });

        showSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (solutionFrame == null) {
                    solutionFrame = new SolutionFrame("Расчет времени");
                    solutionFrame.setVisible(true);
                } else {
                    solutionFrame.setVisible(true);
                    solutionFrame.repaint();
                }
            }
        });

        saveOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[]{".csv", ".xls", ".pdf"}));
        saveOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //jComboBox2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Сохранить как:");

        saveToExcel.setText("Сохранить в");

        jTextField1.setText("Введите значение k");

        jTextField2.setText("Введите начальные условия");

        jLabel1.setText("k = ");

        jLabel3.setText("T(0) = ");

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if(jTextField1.getText().equals("Введите значение k")){
                    jTextField1.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(jTextField1.getText().equals("")){
                    jTextField1.setText("Введите значение k");
                }
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(jTextField1.getText().equals("Введите значение k")){
                    jTextField1.setText("");
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(jTextField1.getText().equals("")){
                    jTextField1.setText("Введите значение k");
                }
            }
        });

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if(jTextField2.getText().equals("Введите начальные условия")){
                    jTextField2.setText("");
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(jTextField2.getText().equals("")){
                    jTextField2.setText("Введите начальные условия");
                }
            }
        });
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(jTextField2.getText().equals("Введите начальные условия")){
                    jTextField2.setText("");
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(jTextField2.getText().equals("")){
                    jTextField2.setText("Введите начальные условия");
                }
            }
        });

        newSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIEntry.master.setVisible(true);
                UIEntry.master.repaint();
            }
        });

        buttonsArea = new JPanel();
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(buttonsArea);
        buttonsArea.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(newSolution)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(changeK)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clear)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(saveOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveToExcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(showSolution)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(aboutButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(newSolution)
                                        .addComponent(changeK)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clear)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveToExcel)
                                        .addComponent(showSolution)
                                        .addComponent(aboutButton))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        saveToExcel.addActionListener(new FileSaveAction());
        mainFrame.add(eulerPanel, BorderLayout.WEST);
        mainFrame.add(rungeKuttPanel, BorderLayout.CENTER);
        mainFrame.add(diffGraphicArea, BorderLayout.EAST);
        mainFrame.add(buttonsArea, BorderLayout.SOUTH);
        //mainFrame.pack();
        return mainFrame;
    }

    public void createArraysWithData(){
        data1 = new Object[UIEntry.getDiffCore().getPointsEuler1().size()][2];
        data2 = new Object[UIEntry.getDiffCore().getPointsEuler2().size()][2];
        data3 = new Object[UIEntry.getDiffCore().getPointsEuler3().size()][2];
        data4 = new Object[UIEntry.getDiffCore().getPointsERungeKutt1().size()][2];
        data5 = new Object[UIEntry.getDiffCore().getPointsERungeKutt2().size()][2];
        data6 = new Object[UIEntry.getDiffCore().getPointsERungeKutt3().size()][2];
        ArrayList<Pair<Double, Double>> a1 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler1();
        ArrayList<Pair<Double, Double>> a2 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler2();
        ArrayList<Pair<Double, Double>> a3 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler3();
        ArrayList<Pair<Double, Double>> a4 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt1();
        ArrayList<Pair<Double, Double>> a5 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt2();
        ArrayList<Pair<Double, Double>> a6 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt3();
        for (int i = 0; i < a1.size(); i++){
            data1[i][0] = a1.get(i).getKey();
            data1[i][1] = a1.get(i).getValue();
        }
        for (int i = 0; i < a2.size(); i++){
            data2[i][0] = a2.get(i).getKey();
            data2[i][1] = a2.get(i).getValue();
        }
        for (int i = 0; i < a3.size(); i++){
            data3[i][0] = a3.get(i).getKey();
            data3[i][1] = a3.get(i).getValue();
        }
        for (int i = 0; i < a4.size(); i++){
            data4[i][0] = a4.get(i).getKey();
            data4[i][1] = a4.get(i).getValue();
        }
        for (int i = 0; i < a5.size(); i++){
            data5[i][0] = a5.get(i).getKey();
            data5[i][1] = a5.get(i).getValue();
        }
        for (int i = 0; i < a6.size(); i++){
            data6[i][0] = a6.get(i).getKey();
            data6[i][1] = a6.get(i).getValue();
        }
    }

    public JPanel createDemoPanel() {
        chartEuler = createEulerChart();
        return new ChartPanel(chartEuler);
    }
    private JFreeChart createDiffChart() {
        // create subplot 1...
        final XYDataset data1 = createDataset3();
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis("The temperature difference of the liquid and metal");
        final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Time (min.)"));
        plot.setGap(50);

        // add the subplots...
        plot.add(subplot1, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("Comparison of Euler and Runge-Kutta methods",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    private JFreeChart createEulerChart() {
        // create subplot 1...
        final XYDataset data1 = createDataset1();
        final XYItemRenderer renderer1 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis1 = new NumberAxis("The temperature difference of the liquid and metal");
        final XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);
        subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Time (min.)"));
        plot.setGap(50);

        // add the subplots...
        plot.add(subplot1, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("The solution of problem of cooling metals by Euler method",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    private JFreeChart createRungeKuttChart() {
        final XYDataset data2 = createDataset2();
        final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        final NumberAxis rangeAxis2 = new NumberAxis("The temperature difference of the liquid and metal");
        rangeAxis2.setAutoRangeIncludesZero(false);
        final XYPlot subplot2 = new XYPlot(data2, null, rangeAxis2, renderer2);
        subplot2.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

        // parent plot...
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Time (min.)"));
        plot.setGap(50);

        // add the subplots...
        plot.add(subplot2, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("The solution of problem of cooling metals by Runge-Kutta method",
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    private XYDataset createDataset1() {

        // create dataset 1...
        final XYSeries series1a = new XYSeries("Euler with step 0.1");
        ArrayList<Pair<Double, Double>> a1 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler1();
        ArrayList<Pair<Double, Double>> a2 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler2();
        ArrayList<Pair<Double, Double>> a3 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler3();
        for (int i = 0; i < a1.size(); i++){
            series1a.add(a1.get(i).getKey(), a1.get(i).getValue());
        }
        final XYSeries series1b = new XYSeries("Euler with step 0.01");
        for (int i = 0; i < a2.size(); i++){
            series1b.add(a2.get(i).getKey(), a2.get(i).getValue());
        }
        final XYSeries series1c = new XYSeries("Euler with step 0.001");
        for (int i = 0; i < a3.size(); i++){
            series1c.add(a3.get(i).getKey(), a3.get(i).getValue());
        }
        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1a);
        collection.addSeries(series1b);
        collection.addSeries(series1c);
        return collection;

    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    private XYDataset createDataset2() {
        ArrayList<Pair<Double, Double>> a4 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt1();
        ArrayList<Pair<Double, Double>> a5 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt2();
        ArrayList<Pair<Double, Double>> a6 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt3();
        // create dataset 2...
        final XYSeries series2a = new XYSeries("RungeKutt with step 0.1");
        for (int i = 0; i < a4.size(); i++){
            series2a.add(a4.get(i).getKey(), a4.get(i).getValue());
        }
        final XYSeries series2b = new XYSeries("RungeKutt with step 0.01");
        for (int i = 0; i < a5.size(); i++){
            series2b.add(a5.get(i).getKey(), a5.get(i).getValue());
        }
        final XYSeries series2c = new XYSeries("RungeKutt with step 0.001");
        for (int i = 0; i < a6.size(); i++){
            series2a.add(a6.get(i).getKey(), a6.get(i).getValue());
        }
        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series2a);
        collection.addSeries(series2b);
        collection.addSeries(series2c);
        return collection;

    }

    private XYDataset createDataset3() {
        ArrayList<Pair<Double, Double>> a1 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsEuler3();
        ArrayList<Pair<Double, Double>> a4 = (ArrayList<Pair<Double, Double>>) UIEntry.getDiffCore().getPointsERungeKutt3();
        // create dataset 1...
        final XYSeries series1 = new XYSeries("Difference (ABS(Euler - RungeKutta))");
        for (int i = 0; i < a1.size(); i++){
            series1.add(a1.get(i).getKey(), (Number)(Math.abs(a1.get(i).getValue()-a4.get(i).getValue())));
        }
        /*final XYSeries series1b = new XYSeries("RungeKutt");
        for (int i = 0; i < a4.size(); i++){
            series1b.add(a4.get(i).getKey(), a4.get(i).getValue());
        }*/
        final XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
//        collection.addSeries(series1b);
        return collection;

    }
}
