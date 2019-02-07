package main.application;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This
 * @author ROSY
 * @version 07/02/2019
 * 
 * This class gives a format the log file.
 *
 */
public class CustomFormatter extends Formatter {
		 
	@Override
	public String format(LogRecord record) {
		StringBuffer sb = new StringBuffer();
        //sb.append("Prefixn");
        sb.append(record.getMessage() + "\n");
        //sb.append("Suffixn");
        return sb.toString();
	}
}
