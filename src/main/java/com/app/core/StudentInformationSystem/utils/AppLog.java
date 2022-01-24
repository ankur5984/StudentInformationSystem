package com.app.core.StudentInformationSystem.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class AppLog  {

    public AppLog(){

    }


    public static void writeLog(String _message, String _level,String _exception){
//        _logger.
        File file = generateFile();
        //write data to log files.
        writeMessageToLogFileOnLogLevel(_message,_exception,file,_level);
    }

    public static void writeMessageToLogFileOnLogLevel(String _message,String _ex, File _file, String _level){
        String[] str = {AppConstant.DEBUG,AppConstant.ERROR,AppConstant.INFO};
        for(String levelString:str){
            String logLevel = levelString.equals(_level)?_level:"";
            if(!logLevel.equals("")){
                String textToBeWritten = String.format(" %s : %s : %s%n",_message,_level.toUpperCase(), _ex);
                writeAndAppendToFile(_file,textToBeWritten);
            }
        }

    }

    public static void writeAndAppendToFile(File _file,String _text){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(_file,true));
            writer.write(_text);
            writer.close();
        }catch (Exception _exception){
            _exception.getStackTrace();
        }
    }

    public static File generateFile(){

        File newFile = new File(AppConstant.logFolderPath+AppConstant.fileName);
        try {
            boolean f = newFile.createNewFile();
            System.out.println(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

}
