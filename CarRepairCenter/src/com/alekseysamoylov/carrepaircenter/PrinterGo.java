package com.alekseysamoylov.carrepaircenter;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by AlekseiSamoilov on 06.12.15.
 */
public class PrinterGo {
    public void printPage(){


        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.pageDialog(pj.defaultPage());
        pj.setPrintable(new FilePagePainter("text.txt"), pf);

        if(pj.printDialog()){
            try{
                pj.print();
            }catch(PrinterException e){}
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //pj.cancel();
            //System.exit(0);
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }

    }

    static class FilePagePainter implements Printable{

        private BufferedReader br;
        private String file;
        private int page = -1;
        private boolean eof;
        private String[] line;
        private int numLines;
        public FilePagePainter(String file){
            this.file = file;
            try{
                br = new BufferedReader(new FileReader(file));
            }catch(IOException e){eof = true;}
        }

        @Override
        public int print(Graphics g, PageFormat pf, int ind)throws PrinterException {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times new roman", Font.PLAIN, 14));
            int h = (int)pf.getImageableHeight();
            int x = (int)pf.getImageableX();
            int y = (int)pf.getImageableY();
            try{
                if(ind != page){
                    if(eof) return Printable.NO_SUCH_PAGE;
                    page = ind;
                    line = new String[h/12];
                    numLines = 0;

                    while(y+48<pf.getImageableY()+h){
                        line[numLines] = br.readLine();
                        if(line[numLines] == null){
                            eof = true; break;
                        }
                        numLines++;
                        y+=12;
                    }
                }
                y = (int)pf.getImageableY()+12;
                g.drawString("Чек " + file + ", Страница " + (ind + 1), x, y);

                for (int i = 0; i < numLines; i++){
                    g.drawString(line[i], x, y);
                    y += 12;
                }
                return Printable.PAGE_EXISTS;

            }catch(IOException e){
                return Printable.NO_SUCH_PAGE;
            }
        }

    }

}
