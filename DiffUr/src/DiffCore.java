/*DiffCore.java*/
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 21.03.2015.
 "<html><br>\n" +
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
 "</html>"
 */
public class DiffCore {
    // начальные условия
    double x0;
    double y0;
    int n; // число шагов
    public final static long MAX_COUNT_OF_STEPS = 10000000; //максимальное количество шагов
    public static double EPSILON = 0.01;
    double h; // шаг интегрированя

    public double getY0() {
        return y0;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    double k; // постоянный коэффициент
    static DiffCore diffCore;

    public List<Pair<Double, Double>> getPointsEuler1() {
        return pointsEuler1;
    }

    public List<Pair<Double, Double>> getPointsEuler2() {
        return pointsEuler2;
    }

    public List<Pair<Double, Double>> getPointsEuler3() {
        return pointsEuler3;
    }

    public List<Pair<Double, Double>> getPointsERungeKutt1() {
        return pointsERungeKutt1;
    }

    public List<Pair<Double, Double>> getPointsERungeKutt2() {
        return pointsERungeKutt2;
    }

    public List<Pair<Double, Double>> getPointsERungeKutt3() {
        return pointsERungeKutt3;
    }

    List<Pair<Double, Double>> pointsEuler1;
    List<Pair<Double, Double>> pointsEuler2;
    List<Pair<Double, Double>> pointsEuler3;
    List<Pair<Double, Double>> pointsERungeKutt1;
    List<Pair<Double, Double>> pointsERungeKutt2;
    List<Pair<Double, Double>> pointsERungeKutt3;

    private DiffCore(){
        pointsEuler1 = new ArrayList<Pair<Double, Double>>();
        pointsEuler2 = new ArrayList<Pair<Double, Double>>();
        pointsEuler3 = new ArrayList<Pair<Double, Double>>();
        pointsERungeKutt1 = new ArrayList<Pair<Double, Double>>();
        pointsERungeKutt2 = new ArrayList<Pair<Double, Double>>();
        pointsERungeKutt3 = new ArrayList<Pair<Double, Double>>();
    }

    /**
     * Euler method for solving the problem
     * @param step is the required step for method
     */
    void Euler(double step){
        List<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
        double x=x0, y=y0;
        h = step;
        n = (int) (10 / step);
        for(int i = 0; i < DiffCore.MAX_COUNT_OF_STEPS && y > EPSILON; i++){
//        for (int i = 0; i < n; i++) {
            y += h * f(x, y);
            x += h;
            points.add(new Pair<Double, Double>(x, y));
        }
        if (step == 0.1){
            pointsEuler1 = points;
        } else if (step == 0.01){
            pointsEuler2 = points;
        } else{
            pointsEuler3 = points;
        }
    }

    /**
     * Runge-Kutta method for solving the problem
     * @param step is the required step for method
     */
    void RungeKutt(double step){
        List<Pair<Double, Double>> points = new ArrayList<Pair<Double, Double>>();
        double x=x0, y=y0, k1, k2, k3, k4;
        h = step;
        n = (int) (10 / step);
        for(int i = 0; i < DiffCore.MAX_COUNT_OF_STEPS && y > EPSILON; i++){
//        for (int i=0; i<n; i++){
            k1=f(x, y);
            k2=f(x + h / 2, y + (h * k1) / 2);
            k3=f(x + h / 2, y + (h * k2) / 2);
            k4=f(x + h, y + h * k3);
            y+=h*(k1+2*k2+2*k3+k4)/6;
            x+=h;
            points.add(new Pair<Double, Double>(x, y));
        }
        if (step == 0.1){
            pointsERungeKutt1 = points;
        } else if (step == 0.01){
            pointsERungeKutt2 = points;
        } else{
            pointsERungeKutt3 = points;
        }
    }

    /**
     * Corresponds to the function f(x,y), where
     * @param x is time
     * @param y is temperature difference
     * For this task, we have the equation y'=ky
     */
    double f(double x, double y){
        return k*y;
    }

    static DiffCore init(){
        if (diffCore == null){
            diffCore = new DiffCore();
        }
        return diffCore;
    }

    /**
     * @param tDiff is the required temperature difference
     */
    public double[][] getSolution(double tDiff){
        double[][] solution = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
        for (int i = 1; i < pointsEuler1.size(); i++) {
            if (pointsEuler1.get(i).getValue() < tDiff && pointsEuler1.get(i - 1).getValue() > tDiff ||
                    pointsEuler1.get(i).getValue() > tDiff && pointsEuler1.get(i - 1).getValue() < tDiff) {
                solution[0][0] = pointsEuler1.get(i - 1).getKey();
                solution[0][1] = pointsEuler1.get(i - 1).getValue();
                solution[1][0] = pointsEuler1.get(i).getKey();
                solution[1][1] = pointsEuler1.get(i).getValue();
                break;
            }
        }
        for (int i = 1; i < pointsEuler2.size(); i++) {
            if (pointsEuler2.get(i).getValue() < tDiff && pointsEuler2.get(i - 1).getValue() > tDiff ||
                    pointsEuler2.get(i).getValue() > tDiff && pointsEuler2.get(i - 1).getValue() < tDiff) {
                solution[2][0] = pointsEuler2.get(i - 1).getKey();
                solution[2][1] = pointsEuler2.get(i - 1).getValue();
                solution[3][0] = pointsEuler2.get(i).getKey();
                solution[3][1] = pointsEuler2.get(i).getValue();
                break;
            }
        }
        for (int i = 1; i < pointsEuler3.size(); i++) {
            if (pointsEuler3.get(i).getValue() < tDiff && pointsEuler3.get(i-1).getValue() > tDiff ||
                    pointsEuler3.get(i).getValue() > tDiff && pointsEuler3.get(i-1).getValue() < tDiff ){
                solution[4][0]=  pointsEuler3.get(i-1).getKey();
                solution[4][1]=  pointsEuler3.get(i-1).getValue();
                solution[5][0]=  pointsEuler3.get(i).getKey();
                solution[5][1]=  pointsEuler3.get(i).getValue();
                break;
            }
        }
        for (int i = 1; i < pointsERungeKutt1.size(); i++) {
            if (pointsERungeKutt1.get(i).getValue() < tDiff && pointsERungeKutt1.get(i - 1).getValue() > tDiff ||
                    pointsERungeKutt1.get(i).getValue() > tDiff && pointsERungeKutt1.get(i - 1).getValue() < tDiff) {
                solution[6][0] = pointsERungeKutt1.get(i - 1).getKey();
                solution[6][1] = pointsERungeKutt1.get(i - 1).getValue();
                solution[7][0] = pointsERungeKutt1.get(i).getKey();
                solution[7][1] = pointsERungeKutt1.get(i).getValue();
                break;
            }
        }
        for (int i = 1; i < pointsERungeKutt2.size(); i++) {
            if (pointsERungeKutt2.get(i).getValue() < tDiff && pointsERungeKutt2.get(i - 1).getValue() > tDiff ||
                    pointsERungeKutt2.get(i).getValue() > tDiff && pointsERungeKutt2.get(i - 1).getValue() < tDiff) {
                solution[8][0] = pointsERungeKutt2.get(i - 1).getKey();
                solution[8][1] = pointsERungeKutt2.get(i - 1).getValue();
                solution[9][0] = pointsERungeKutt2.get(i).getKey();
                solution[9][1] = pointsERungeKutt2.get(i).getValue();
                break;
            }
        }
        for (int i = 1; i < pointsERungeKutt3.size(); i++) {
            if (pointsERungeKutt3.get(i).getValue() < tDiff && pointsERungeKutt3.get(i - 1).getValue() > tDiff ||
                    pointsERungeKutt3.get(i).getValue() > tDiff && pointsERungeKutt3.get(i - 1).getValue() < tDiff) {
                solution[10][0] = pointsERungeKutt3.get(i - 1).getKey();
                solution[10][1] = pointsERungeKutt3.get(i - 1).getValue();
                solution[11][0] = pointsERungeKutt3.get(i).getKey();
                solution[11][1] = pointsERungeKutt3.get(i).getValue();
                break;
            }
        }
        return solution;
    }

}
