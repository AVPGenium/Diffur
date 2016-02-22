/*About.java*/

import javax.swing.*;

/**
 * Created by Андрей on 11.04.2015.
 */
public class About extends JFrame {
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    /**
     * frame with information about the project
     * @param title is frame's title
     */
    public About(String title) {
        super(title);
        jToolBar1 = new javax.swing.JToolBar();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel("<html><center>The solution of problem of cooling metals - is a user-friendly automatic<br> solver of the physical problem of cooling metals by liquids and builds graphs and tables corresponding to this solution <br>\n" +
                "<br><b><i>DiffUr distributed under the GPL.</i></b><br><br>\n" + "Source code: http://github.com/diffur<br><br>\n" +
                "<b>Development team: \n</b><br>Poliakov Andrei (developer)<br>\n <br>2015<br>Poliakov Andrei" +
                "</center></html>");
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel("<html><br>\n" +
                "Uses JFreeChart library under LGPL\n" +
                "<br>(files in the folder \"libs\":" +
                "<br><center>hamcrest-core-1.3.jar" +
                "<br>jcommon-1.0.23.jar" +
                "<br>jfreechart-1.0.19.jar" +
                "<br>jfreechart-1.0.19-experimental.jar" +
                "<br>jfreechart-1.0.19-swt.jar" +
                "<br>jfreesvg-2.0.jar" +
                "<br>junit-4.11.jar<br>orsoncharts-1.4-eval-nofx.jar" +
                "<br>orsonpdf-1.6-eval.jar" +
                "<br>servlet.jar" +
                "<br>swtgraphics2d.jar</center>)<br>" +
                "http://www.jfree.org/jfreechart/<br><br><br>\n" +
                "Uses JExcelApi library under GPL\n" +
                "<br>(files in the folder \"libs\":<br><center>jxl.jar</center>)<br>" +
                "http://jexcelapi.sourceforge.net/<br><br><br>\n" +
                "Uses JavaFX library under GPL\n" +
                "<br>(files in the folder \"libs\":<br><center>jfxrt.jar</center>)<br>" +
                "http://www.oracle.com/technetwork/java/javase/<br><br><br>\n" +
                "Uses iTextPdf library under GPL\n" +
                "<br>(files in the folder \"libs\":" +
                "<br><center>itextpdf-5.5.5.jar" +
                "<br>itext-pdfa-5.5.5.jar" +
                "<br>itext-xtra-5.5.5.jar</center><br>)<br>" +
                "http://itextpdf.com/<br><br><br>\n" +
                "</html>");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jTabbedPane1.addTab("About App", jPanel1);
        jScrollPane1.setViewportView(jLabel1);

        jTabbedPane1.addTab("Used Libraries", jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTabbedPane1)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane1)
                                .addContainerGap())
        );
        setSize(350, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);
    }
}
