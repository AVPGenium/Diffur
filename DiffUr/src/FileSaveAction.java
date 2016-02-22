import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Андрей on 12.04.2015.
 */
public class FileSaveAction implements ActionListener{
    private static JFileChooser fileChooser;


    @Override
    public void actionPerformed(ActionEvent anActionEvent) {
        if (fileChooser == null) {
            initFileChooser();
        }
        if (new File(UIEntry.getUi().getPath()).exists() && !UIEntry.getUi().getPath().equals("")) {
            fileChooser.setCurrentDirectory(new File(UIEntry.getUi().getPath()));
        }
        int resultOfChoice = fileChooser.showOpenDialog(UIEntry.getUi().getMainFrame());
        if (resultOfChoice == JFileChooser.APPROVE_OPTION) {
            File chosenDirectory = new File(fileChooser.getSelectedFile().getPath());
            UIEntry.getUi().setPath(chosenDirectory.toString());
            System.out.println(chosenDirectory.toString());
        }
        if ((UIEntry.getUi().getSaveOptions().getSelectedItem()).equals(".csv")){
            UIEntry.saveCSV(UIEntry.getUi().getPath());
        } else if ((UIEntry.getUi().getSaveOptions().getSelectedItem()).equals(".pdf")){
            UIEntry.savePDF();
        } else if ((UIEntry.getUi().getSaveOptions().getSelectedItem()).equals(".xls")){
            UIEntry.saveXLS(UIEntry.getUi().getPath());
        }
    }

    private void initFileChooser(){
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Comma-Separated Values (.csv)", "csv"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Portable Document Format (.pdf)", "pdf"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("Рабочая книга Excel с макросами (.xls)", "xls"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Project directory", "."));
        fileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
    }
}
