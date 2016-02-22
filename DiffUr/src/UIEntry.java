import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import java.util.Locale;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jfree.chart.ChartUtilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Created by Андрей on 21.03.2015.
 */
public class UIEntry implements Runnable{
    static ExceptionListener exceptionListener;

    public static UI getUi() {
        return ui;
    }

    static UI ui;

    public static DiffCore getDiffCore() {
        return diffCore;
    }

    static DiffCore diffCore;
    static NewOkCancelDialog master;

    private static WritableWorkbook workbook; // переменная рабочей книги
    public static WritableSheet sheet1, sheet2, sheet3;
    public static WritableCellFormat arial12BoldFormat;
    public static Label label;


    public static void main(String[] args) {
        final UIEntry uiEntry = new UIEntry();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    uiEntry.init();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
            exceptionListener.handleExceptionAndShowDialog(e);
        }
    }

    private void init() {
        diffCore = DiffCore.init();
        diffCore.setX0(0);
        diffCore.setY0(0);
        diffCore.setK(0);
        ui = UI.getInstance();
        exceptionListener = ui;
        /* Create and display the dialog */
        master = new NewOkCancelDialog(new javax.swing.JFrame(), true);
        master.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
        });
        master.setVisible(true);
        if (System.getProperty("os.name").contains("Windows")) {
            //ui.getMainFrame().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("about_logo.png")));
        }
    }

    public static void reCalc(double k, double x0, double y0){
        diffCore.setK(k);
        diffCore.setX0(x0);
        diffCore.setY0(y0);
        diffCore.Euler(0.1);
        diffCore.Euler(0.01);
        diffCore.Euler(0.001);
        diffCore.RungeKutt(0.1);
        diffCore.RungeKutt(0.01);
        diffCore.RungeKutt(0.001);
        if (ui.getMainFrame() == null){
            ui.initUI();
        }
        ui.reDraw();
    }

    @Override
    public void run() {

    }

    public static void saveCSV(String path){

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path + "/chartsAndTables.csv");
            fileWriter.write("Euler method;;;;;;;;;RungeKutta method;;;;;;;;\n" +
                        "step 0.1;;;step 0.01;;;step 0.001;;;step 0.1;;;step 0.01;;;step 0.001;;\n" +
                        "x;y;;x;y;;x;y;;x;y;;x;y;;x;y;\n");
            int m1 = max(UIEntry.getDiffCore().getPointsEuler1().size(), UIEntry.getDiffCore().getPointsEuler2().size(),
                    UIEntry.getDiffCore().getPointsEuler3().size());
            int m2 = max(UIEntry.getDiffCore().getPointsERungeKutt1().size(), UIEntry.getDiffCore().getPointsERungeKutt2().size(),
                            UIEntry.getDiffCore().getPointsERungeKutt3().size());
            int max = m1 > m2 ? m1 : m2;
            for (int i = 0; i < max; i++){
                if (i<UIEntry.getDiffCore().getPointsEuler1().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsEuler1().get(i).getKey())+";"+
                            String.valueOf(UIEntry.getDiffCore().getPointsEuler1().get(i).getValue()) + ";;");
                } else{
                    fileWriter.write(";;;");
                }
                if (i<UIEntry.getDiffCore().getPointsEuler2().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsEuler2().get(i).getKey()) + ";" +
                            String.valueOf(UIEntry.getDiffCore().getPointsEuler2().get(i).getValue()) + ";;");
                } else{
                    fileWriter.write(";;;");
                }
                if (i<UIEntry.getDiffCore().getPointsEuler3().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsEuler3().get(i).getKey()) + ";" +
                            String.valueOf(UIEntry.getDiffCore().getPointsEuler3().get(i).getValue()) + ";;");
                } else{
                    fileWriter.write(";;;");
                }
                if (i<UIEntry.getDiffCore().getPointsERungeKutt1().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt1().get(i).getKey())+";"+
                            String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt1().get(i).getValue()) + ";;");
                } else{
                    fileWriter.write(";;;");
                }
                if (i<UIEntry.getDiffCore().getPointsERungeKutt2().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt2().get(i).getKey()) + ";" +
                            String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt2().get(i).getValue()) + ";;");
                } else{
                    fileWriter.write(";;;");
                }
                if (i<UIEntry.getDiffCore().getPointsERungeKutt3().size()){
                    fileWriter.write(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt3().get(i).getKey()) + ";" +
                            String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt3().get(i).getValue()) + ";\n");
                } else{
                    fileWriter.write(";;\n");
                }
            }
            fileWriter.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    /*
     * метод создает книгу с 3-мя раб страницами
     */
    public static void saveXLS(String path){
        WritableSheet sheet1, sheet2, sheet3;
        WorkbookSettings ws = new WorkbookSettings();
        ws.setLocale(new Locale("ru", "RU"));
        //имя и путь файла
        try {
            workbook = Workbook.createWorkbook(new File(path + "/chartsAndTables.xls"), ws);
            //название листа
            sheet1 = workbook.createSheet("Решение методом Эйлера", 0);
			//методы, которые будут выполняться для заполнения листа
            fillEulerSheet(sheet1);
            sheet2 = workbook.createSheet("Решение методом Рунге-Кутта", 1);
            fillRungeKuttSheet(sheet2);
            sheet3 = workbook.createSheet("Сравнение методов Эйлера и Рунге-Кутта", 2);
            fillComparisonEulerRungeKuttSheet(sheet3);
        } catch (IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        } catch (WriteException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        }
        try {
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        } catch (WriteException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        }
    }

    public static void fillEulerSheet(WritableSheet sheet) throws WriteException
    {
        WritableCellFormat arial12BoldFormat, arial8NormalFormat;
        //установка шрифта
        WritableFont arial12ptBold =
                new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial8Normal =
                new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD);
        arial12BoldFormat = new WritableCellFormat(arial12ptBold);
        arial8NormalFormat = new WritableCellFormat(arial8Normal);
        //выравнивание по центру
        arial12BoldFormat.setAlignment(Alignment.CENTRE);
        //перенос по словам если не помещается
        arial12BoldFormat.setWrap(true);
        //установить цвет
        arial12BoldFormat.setBackground(Colour.GRAY_50);
        arial8NormalFormat.setBackground(Colour.GRAY_25);
        //рисуем рамку
        arial12BoldFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        arial8NormalFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        //поворот текста
        //arial12BoldFormat.setOrientation(Orientation.PLUS_90);

        //пример добавления в ячейки
        Label label = new Label(0, 0, "step 0.1", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(1, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(3, 0, "step 0.01", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(4, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(6, 0, "step 0.001", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(7, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        //arial12BoldFormat.setBackground(Colour.GRAY_25);

        for(int i=1;i<getDiffCore().getPointsEuler1().size()+1;i++)
        {
            label = new Label(0, i, String.valueOf(getDiffCore().getPointsEuler1().get(i-1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(1, i, String.valueOf(getDiffCore().getPointsEuler1().get(i-1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        for(int i=1;i<getDiffCore().getPointsEuler2().size()+1;i++)
        {
            label = new Label(3, i, String.valueOf(getDiffCore().getPointsEuler2().get(i-1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(4, i, String.valueOf(getDiffCore().getPointsEuler2().get(i-1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        for(int i=1;i<getDiffCore().getPointsEuler3().size()+1;i++)
        {
            label = new Label(6, i, String.valueOf(getDiffCore().getPointsEuler3().get(i-1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(7, i, String.valueOf(getDiffCore().getPointsEuler3().get(i-1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        // выбрасывем в поток
        try {
            // например в файл
            OutputStream stream = new FileOutputStream("chartEuler.png");
            ChartUtilities.writeChartAsPNG(stream, getUi().getChartEuler(), 500, 500);
        } catch(IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            System.err.println("Failed to render chart as png: "+ e.getMessage());
            e.printStackTrace();
        }
        WritableImage wi = new WritableImage(10, 1, 10, 17, new File("chartEuler.png"));
        sheet.addImage(wi);
    }

    public static void fillRungeKuttSheet(WritableSheet sheet) throws WriteException
    {
        WritableCellFormat arial12BoldFormat, arial8NormalFormat;
        //установка шрифта
        WritableFont arial12ptBold =
                new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial8Normal =
                new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD);
        arial12BoldFormat = new WritableCellFormat(arial12ptBold);
        arial8NormalFormat = new WritableCellFormat(arial8Normal);
        //выравнивание по центру
        arial12BoldFormat.setAlignment(Alignment.CENTRE);
        //перенос по словам если не помещается
        arial12BoldFormat.setWrap(true);
        //установить цвет
        arial12BoldFormat.setBackground(Colour.GRAY_50);
        arial8NormalFormat.setBackground(Colour.GRAY_25);
        //рисуем рамку
        arial12BoldFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        arial8NormalFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
        //поворот текста
        //arial12BoldFormat.setOrientation(Orientation.PLUS_90);

        //пример добавления в ячейки
        Label label = new Label(0, 0, "step 0.1", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(1, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(3, 0, "step 0.01", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(4, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(6, 0, "step 0.001", arial12BoldFormat);
        sheet.addCell(label);
        label = new Label(7, 0, "", arial12BoldFormat);
        sheet.addCell(label);
        //arial12BoldFormat.setBackground(Colour.GRAY_25);

        for(int i=1;i<getDiffCore().getPointsEuler1().size()+1;i++)
        {
            label = new Label(0, i, String.valueOf(getDiffCore().getPointsERungeKutt1().get(i - 1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(1, i, String.valueOf(getDiffCore().getPointsERungeKutt1().get(i - 1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        for(int i=1;i<getDiffCore().getPointsEuler2().size()+1;i++)
        {
            label = new Label(3, i, String.valueOf(getDiffCore().getPointsERungeKutt2().get(i-1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(4, i, String.valueOf(getDiffCore().getPointsERungeKutt2().get(i-1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        for(int i=1;i<getDiffCore().getPointsEuler3().size()+1;i++)
        {
            label = new Label(6, i, String.valueOf(getDiffCore().getPointsERungeKutt3().get(i-1).getKey()), arial8NormalFormat);
            sheet.addCell(label);
            label = new Label(7, i, String.valueOf(getDiffCore().getPointsERungeKutt3().get(i-1).getValue()), arial8NormalFormat);
            sheet.addCell(label);
        }
        // выбрасывем в поток
        try {
            // например в файл
            OutputStream stream = new FileOutputStream("chartRungeKutt.png");
            ChartUtilities.writeChartAsPNG(stream, getUi().getChartRungeKutt(), 500, 500);
        } catch(IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            System.err.println("Failed to render chart as png: "+ e.getMessage());
            e.printStackTrace();
        }
        WritableImage wi = new WritableImage(4, 1, 10, 17, new File("chartRungeKutt.png"));
        sheet.addImage(wi);
    }
    public static void fillComparisonEulerRungeKuttSheet(WritableSheet sheet) throws WriteException {
        // выбрасывем в поток
        try {
            // например в файл
            OutputStream stream = new FileOutputStream("chartComparisonEulerRungeKutt.png");
            ChartUtilities.writeChartAsPNG(stream, getUi().getChartDiff(), 500, 500);
        } catch(IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            System.err.println("Failed to render chart as png: "+ e.getMessage());
            e.printStackTrace();
        }
        WritableImage wi = new WritableImage(4, 1, 10, 17, new File("chartComparisonEulerRungeKutt.png"));
        sheet.addImage(wi);
    }
    /**
     * Saves a chart to a PDF file.
     *
     * file the file.
     * chart the chart.
     * width the chart width.
     * height the chart height.
     */
    public static void savePDF() {
        File fileName = new File(getUi().getPath() + "/chartsAndTables.pdf");
        try {
            float width = PageSize.A4.getWidth();
            float height = PageSize.A4.getHeight()/2;
//            Rectangle pagesize = new Rectangle(width, height);
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new BufferedOutputStream(new FileOutputStream(fileName)));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Anchor anchorTarget = new Anchor("Uses iTextPdf library under GPL");
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();
            paragraph1.setSpacingBefore(50);
            paragraph1.add(anchorTarget);
            document.add(paragraph1);
            document.add(new Paragraph("The results of solving the problem of cooling metals \n(tables and graphics) \n" +
                    "with a coefficient k = " + getDiffCore().getK() + "\nand the initial conditions T(0) = " + getDiffCore().getY0() +
                    ";",
                    FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));
            Paragraph eulerChart = new Paragraph("Euler method charts",
                    FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(0, 255, 255,17)));
            Chapter chapter1a = new Chapter(eulerChart, 1);
            chapter1a.setNumberDepth(0);
            document.add(chapter1a);
            PdfTemplate tp1 = cb.createTemplate(width, height);
            Graphics2D g2d1 = new PdfGraphics2D(tp1, width, height);
            Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, width, height);
            UIEntry.getUi().getChartEuler().draw(g2d1, r2d1);
            g2d1.dispose();
            cb.addTemplate(tp1, 0, height - 120);
            Paragraph eulerChapter = new Paragraph("Euler method tables",
                    FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(0, 255, 255,17)));
            Chapter chapter1b = new Chapter(eulerChapter, 2);
            chapter1b.setNumberDepth(0);
            PdfPTable t1 = new PdfPTable(8);
            t1.setSpacingBefore(25);
            t1.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("step " + 0.1));
            c1 = new PdfPCell(new Phrase("step " + 0.1));
            t1.addCell(c1);
            t1.addCell("");
            t1.addCell("");
            PdfPCell c2 = new PdfPCell(new Phrase("step " + 0.01));
            t1.addCell(c2);
            t1.addCell("");
            t1.addCell("");
            PdfPCell c3 = new PdfPCell(new Phrase("step " + 0.001));
            t1.addCell(c3);
            t1.addCell("");
            for (int i = 0; i < max(UIEntry.getDiffCore().getPointsEuler1().size(), UIEntry.getDiffCore().getPointsEuler2().size(),
                    UIEntry.getDiffCore().getPointsEuler3().size()); i++) {
                if (i<UIEntry.getDiffCore().getPointsEuler1().size()){
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler1().get(i).getKey()));
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler1().get(i).getValue()));
                } else{
                    t1.addCell("");
                    t1.addCell("");
                }
                t1.addCell("");
                if (i<UIEntry.getDiffCore().getPointsEuler2().size()){
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler2().get(i).getKey()));
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler2().get(i).getValue()));
                } else{
                    t1.addCell("");
                }
                t1.addCell("");
                if (i<UIEntry.getDiffCore().getPointsEuler3().size()){
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler3().get(i).getKey()));
                    t1.addCell(String.valueOf(UIEntry.getDiffCore().getPointsEuler3().get(i).getValue()));
                } else{
                    t1.addCell("");
                }
            }
            chapter1b.add(t1);
            document.add(chapter1b);
            //section1.add(t);
            document.addAuthor("Andrei Poliakov");
            document.addSubject("Графики и таблицы решения задачи");
            Paragraph rungeKuttChart = new Paragraph("Runge-Kutta method charts",
                    FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(0, 255, 255,17)));
            Chapter chapter2a = new Chapter(rungeKuttChart, 3);
            chapter2a.setNumberDepth(0);
            document.add(chapter2a);
            PdfTemplate tp2 = cb.createTemplate(width, height);
            Graphics2D g2d2 = new PdfGraphics2D(tp2, width, height);
            Rectangle2D r2d2 = new Rectangle2D.Double(0, 0, width, height);
            UIEntry.getUi().getChartRungeKutt().draw(g2d2, r2d2);
            g2d2.dispose();
            cb.addTemplate(tp2, 0, height - 120);
            Paragraph rungeKuttChapter = new Paragraph("Runge-Kutta method tables",
                    FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(0, 255, 255,17)));
            Chapter chapter2b = new Chapter(rungeKuttChapter, 3);
            chapter2b.setNumberDepth(0);
            PdfPTable t2 = new PdfPTable(8);
            t2.setSpacingBefore(25);
            t2.setSpacingAfter(25);
            t2.addCell("step " + 0.1);
            t2.addCell("");
            t2.addCell("");
            t2.addCell("step " + 0.01);
            t2.addCell("");
            t2.addCell("");
            t2.addCell("step " + 0.001);
            t2.addCell("");
            for (int i = 0; i < max(UIEntry.getDiffCore().getPointsERungeKutt1().size(), UIEntry.getDiffCore().getPointsERungeKutt2().size(),
                    UIEntry.getDiffCore().getPointsERungeKutt3().size()); i++) {
                if (i<UIEntry.getDiffCore().getPointsEuler1().size()){
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt1().get(i).getKey()));
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt1().get(i).getValue()));
                } else{
                    t2.addCell("");
                    t2.addCell("");
                }
                t2.addCell("");
                if (i<UIEntry.getDiffCore().getPointsEuler2().size()){
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt2().get(i).getKey()));
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt2().get(i).getValue()));
                } else{
                    t2.addCell("");
                    t2.addCell("");
                }
                t2.addCell("");
                if (i<UIEntry.getDiffCore().getPointsEuler3().size()){
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt3().get(i).getKey()));
                    t2.addCell(String.valueOf(UIEntry.getDiffCore().getPointsERungeKutt3().get(i).getValue()));
                } else{
                    t2.addCell("");
                    t2.addCell("");
                }
            }
            chapter2b.add(t2);
            document.add(chapter2b);
            Paragraph ComparisonOfEulerAndRungeKutt = new Paragraph("Comparison of Euler and Runge-Kutta methods",
                    FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new CMYKColor(0, 255, 255,17)));
            Chapter chapter3 = new Chapter(ComparisonOfEulerAndRungeKutt, 3);
            chapter3.setNumberDepth(0);
            document.add(chapter3);
            PdfTemplate tp3 = cb.createTemplate(width, height);
            Graphics2D g2d3 = new PdfGraphics2D(tp3, width, height);
            Rectangle2D r2d3 = new Rectangle2D.Double(0, 0, width, height);
            UIEntry.getUi().getChartDiff().draw(g2d3, r2d3);
            g2d3.dispose();
            cb.addTemplate(tp3, 0, height - 120);
            document.close();
        } catch (IOException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        } catch (DocumentException e) {
            exceptionListener.handleExceptionAndShowDialog(e);
            e.printStackTrace();
        }
    }

    private static int max(int a, int b, int c){
        if (a>b){
            return a>c?a:c;
        } else{
            return b>c?b:c;
        }
    }

    public class NewOkCancelDialog extends javax.swing.JDialog {

        /**
         * A return status code - returned if Cancel button has been pressed
         */
        public static final int RET_CANCEL = 0;
        /**
         * A return status code - returned if OK button has been pressed
         */
        public static final int RET_OK = 1;

        /**
         * Creates new form NewOkCancelDialog
         */
        public NewOkCancelDialog(java.awt.Frame parent, boolean modal) {
            super(parent, modal);
            initComponents();

            // Close the dialog when Esc is pressed
            String cancelName = "cancel";
            InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
            ActionMap actionMap = getRootPane().getActionMap();
            actionMap.put(cancelName, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    doClose(RET_CANCEL);
                }
            });
        }

        /**
         * @return the return status of this dialog - one of RET_OK or RET_CANCEL
         */
        public int getReturnStatus() {
            return returnStatus;
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

            okButton = new javax.swing.JButton();
            jTabbedPane1 = new javax.swing.JTabbedPane();
            jPanel1 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jTextField1 = new javax.swing.JTextField();
            jLabel3 = new javax.swing.JLabel();
            jTextField2 = new javax.swing.JTextField();
            jLabel4 = new javax.swing.JLabel();
            jLabel5 = new javax.swing.JLabel();
            jLabel6 = new javax.swing.JLabel();
            jLabel1 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setTitle("Мастер начальных настроек");
            setAlwaysOnTop(true);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            setForeground(java.awt.Color.lightGray);
            setIconImage(null);
            setResizable(false);
            setType(java.awt.Window.Type.POPUP);
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    closeDialog(evt);
                }
            });

            okButton.setText("OK");
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okButtonActionPerformed(evt);
                }
            });

            jLabel2.setText("Начальная разница температур");

            jLabel3.setText("Коэффициент k");

            jLabel4.setText("(в момент времени t=0)");

            jLabel5.setText("(от 0 до 1 млрд.)");

            jLabel6.setText("(от -12 до 0)");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel3))
                                                    .addGap(68, 68, 68)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                                            .addComponent(jTextField2))
                                                    .addGap(26, 26, 26)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel5)
                                                            .addComponent(jLabel6)))
                                            .addComponent(jLabel4))
                                    .addContainerGap(52, Short.MAX_VALUE))
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5))
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                    .addContainerGap(76, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Начальные условия", jPanel1);

            jLabel1.setText("Добро пожаловать!");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(37, 37, 37)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(jLabel1)
                                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                            .addComponent(jTabbedPane1)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTabbedPane1)
                                    .addGap(18, 18, 18)
                                    .addComponent(okButton)
                                    .addContainerGap())
            );

            getRootPane().setDefaultButton(okButton);

            pack();
        }// </editor-fold>

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
            try {
                if (Double.parseDouble(jTextField2.getText()) >= 0 || Double.parseDouble(jTextField1.getText()) < -12)
                    throw new Exception("Value is out of range. (-12 <= k < 0)");
                if(Double.parseDouble(jTextField1.getText()) < 0 || Double.parseDouble(jTextField2.getText()) > 1000000000)
                    throw new Exception("Value is out of range. (0 <= delta T0 <= 1 млрд.)");
                UIEntry.reCalc(Double.parseDouble(jTextField2.getText()), UIEntry.getDiffCore().x0, Double.parseDouble(jTextField1.getText()));
                if (!UIEntry.getUi().getMainFrame().isVisible())
                    ui.getMainFrame().setVisible(true);
                doClose(RET_OK);
            } catch (Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage(), "It's an error, breathe deeply", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        /**
         * Closes the dialog
         */
        private void closeDialog(java.awt.event.WindowEvent evt) {
            doClose(RET_CANCEL);
        }

        private void doClose(int retStatus) {
            returnStatus = retStatus;
            setVisible(false);
            dispose();
        }

        /**
         * @param args the command line arguments
         */
        public void main(String args[]) {

        /* Create and display the dialog */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    NewOkCancelDialog dialog = new NewOkCancelDialog(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JTabbedPane jTabbedPane1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JButton okButton;
        // End of variables declaration

        private int returnStatus = RET_CANCEL;
    }
}
